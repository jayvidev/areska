import type {
  FirebaseUserRequest,
  UpdateUserEmailRequest,
  UpdateUserPhotoRequest,
  UpdateUserProfileRequest,
  UserDetailResponse,
  UserListResponse,
} from '@/features/public/schemas/user-schema'

import { apiClient } from './client'

export const usersApi = {
  async getAll(): Promise<UserListResponse[]> {
    return apiClient.get<UserListResponse[]>('/users')
  },

  async getById(id: number): Promise<UserDetailResponse> {
    return apiClient.get<UserDetailResponse>(`/users/${id}`)
  },

  async getByFirebaseUid(firebaseUid: string): Promise<UserDetailResponse> {
    return apiClient.get<UserDetailResponse>(`/users/firebase/${firebaseUid}`)
  },

  async syncWithFirebase(payload: FirebaseUserRequest): Promise<UserDetailResponse> {
    return apiClient.post<UserDetailResponse>('/users/firebase/sync', payload)
  },

  async updateEmail(
    firebaseUid: string,
    payload: UpdateUserEmailRequest
  ): Promise<UserDetailResponse> {
    return apiClient.put<UserDetailResponse>(`/users/${firebaseUid}/email`, payload)
  },

  async updateProfile(
    firebaseUid: string,
    payload: UpdateUserProfileRequest
  ): Promise<UserDetailResponse> {
    return apiClient.put<UserDetailResponse>(`/users/${firebaseUid}/profile`, payload)
  },

  async updatePhoto(
    firebaseUid: string,
    payload: UpdateUserPhotoRequest
  ): Promise<UserDetailResponse> {
    return apiClient.put<UserDetailResponse>(`/users/${firebaseUid}/photo`, payload)
  },
}
