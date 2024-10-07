"use client";

import { useMutation } from '@tanstack/react-query';
import axiosInstance from '../axiosInstance';
import { LoginRequest, LoginResponse } from '../../../lib/authTypes';
import { useRouter } from 'next/navigation';

export const useLogin = () => {
  const router = useRouter();
  // useMutation을 정의할 때 제네릭 타입 설정
  const mutation = useMutation<LoginResponse, Error, LoginRequest>({
    mutationFn: async ({ loginid, password }: LoginRequest): Promise<LoginResponse> => {
      // API 요청 (Mock 데이터가 반환됨)
      const response = await axiosInstance.post<LoginResponse>('/login', { loginid, password });
      return response.data;
    },
    onSuccess: (data: LoginResponse) => {
      console.log('로그인 성공:', data);
      // 로그인 성공 시, 토큰을 저장
      localStorage.setItem('accessToken', data.accessToken);
      localStorage.setItem('refreshToken', data.refreshToken);
      router.push('/ide');
    },
    onError: (error) => {
      console.error('로그인 실패:', error.message);
    },
  });

  return mutation;
};
