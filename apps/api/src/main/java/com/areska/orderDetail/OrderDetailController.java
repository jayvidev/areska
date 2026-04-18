package com.areska.orderDetail;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.areska.order.dto.response.OrderDetailResponse;
import com.areska.orderDetail.dto.request.OrderDetailRequest;
import com.areska.orderDetail.dto.request.OrderDetailUpdateRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/order-details")
@RequiredArgsConstructor
@Tag(name = "Order Details", description = "Operations related to order line items")
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @GetMapping("/by-order/{orderId}")
    @Operation(summary = "List order details by order ID")
    public ResponseEntity<?> byOrder(@Min(1) @PathVariable Integer orderId) {
        List<OrderDetailResponse> details = orderDetailService.getListByOrder(orderId);
        if (details.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(details);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an order detail by ID")
    public ResponseEntity<OrderDetailResponse> get(@Min(1) @PathVariable Integer id) {
        OrderDetailResponse detail = orderDetailService.getDetailById(id);
        return ResponseEntity.ok(detail);
    }

    @PostMapping
    @Operation(summary = "Create a new order detail")
    public ResponseEntity<?> create(@Valid @RequestBody OrderDetailRequest request) {
        OrderDetailResponse created = orderDetailService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an order detail by ID")
    public ResponseEntity<?> update(@Min(1) @PathVariable Integer id, @Valid @RequestBody OrderDetailUpdateRequest request) {
        OrderDetailResponse updated = orderDetailService.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order detail by ID")
    public ResponseEntity<Void> delete(@Min(1) @PathVariable Integer id) {
        orderDetailService.delete(id);
        return ResponseEntity.noContent().build();
    }
}