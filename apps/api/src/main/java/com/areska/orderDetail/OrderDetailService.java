package com.areska.orderDetail;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.areska.order.Order;
import com.areska.order.OrderRepository;
import com.areska.order.OrderService;
import com.areska.order.dto.response.OrderDetailResponse;
import com.areska.orderDetail.dto.request.OrderDetailRequest;
import com.areska.orderDetail.dto.request.OrderDetailUpdateRequest;
import com.areska.product.model.Product;
import com.areska.product.repository.ProductRepository;
import com.areska.shared.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderDetailService {

    private final OrderDetailRepository repository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderService orderService;

    public List<OrderDetailResponse> getListByOrder(Integer orderId) {
        orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));
        return repository.findByOrder_Id(orderId).stream().map(this::map).toList();
    }

    public OrderDetailResponse getDetailById(Integer id) {
        return repository.findById(id).map(this::map)
                .orElseThrow(() -> new ResourceNotFoundException("Order detail not found with ID: " + id));
    }

    @Transactional
    public OrderDetailResponse create(OrderDetailRequest req) {
        if (req.getQuantity() == null || req.getQuantity() <= 0)
            throw new IllegalArgumentException("Quantity must be >= 1");

        Order order = orderRepository.findById(req.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + req.getOrderId()));
        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + req.getProductId()));

        if (product.getStock() != null && product.getStock() < req.getQuantity())
            throw new IllegalArgumentException("Not enough stock for product " + product.getId());
        if (product.getStock() != null) { product.setStock(product.getStock() - req.getQuantity()); productRepository.save(product); }

        BigDecimal unitPrice = product.getPrice();

        OrderDetail saved = repository.save(OrderDetail.builder()
                .order(order).product(product)
                .quantity(req.getQuantity()).unitPrice(unitPrice).build());

        orderService.recalcTotal(order.getId());
        return map(saved);
    }

    @Transactional
    public OrderDetailResponse update(Integer id, OrderDetailUpdateRequest req) {
        OrderDetail d = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order detail not found with ID: " + id));

        Integer newQty = req.getQuantity() != null ? req.getQuantity() : d.getQuantity();
        if (newQty <= 0) throw new IllegalArgumentException("Quantity must be >= 1");

        Product p = d.getProduct();

        if (req.getProductId() != null && !req.getProductId().equals(p.getId())) {
            // devolver stock anterior
            if (p.getStock() != null) { p.setStock(p.getStock() + d.getQuantity()); productRepository.save(p); }
            p = productRepository.findById(req.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + req.getProductId()));
            if (p.getStock() != null && p.getStock() < newQty)
                throw new IllegalArgumentException("Not enough stock for product " + p.getId());
            if (p.getStock() != null) { p.setStock(p.getStock() - newQty); productRepository.save(p); }
            d.setProduct(p);
        } else if (!newQty.equals(d.getQuantity())) {
            int diff = newQty - d.getQuantity();
            if (diff > 0) {
                if (p.getStock() != null && p.getStock() < diff)
                    throw new IllegalArgumentException("Not enough stock for product " + p.getId());
                if (p.getStock() != null) { p.setStock(p.getStock() - diff); productRepository.save(p); }
            } else if (diff < 0 && p.getStock() != null) {
                p.setStock(p.getStock() + (-diff)); productRepository.save(p);
            }
        }

        d.setQuantity(newQty);
        d.setUnitPrice(p.getPrice());

        OrderDetail updated = repository.save(d);
        orderService.recalcTotal(updated.getOrder().getId());
        return map(updated);
    }

    @Transactional
    public void delete(Integer id) {
        OrderDetail d = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order detail not found with ID: " + id));

        Product p = d.getProduct();
        if (p.getStock() != null) { p.setStock(p.getStock() + d.getQuantity()); productRepository.save(p); }

        Integer orderId = d.getOrder().getId();
        repository.delete(d);
        orderService.recalcTotal(orderId);
    }

    private OrderDetailResponse map(OrderDetail d) {
        return new OrderDetailResponse(
                d.getId(),
                d.getOrder().getId(),
                d.getProduct().getId(),
                d.getProduct().getName(),
                d.getQuantity(),
                d.getUnitPrice(),
                d.getUnitPrice().multiply(BigDecimal.valueOf(d.getQuantity()))
        );
    }
}
