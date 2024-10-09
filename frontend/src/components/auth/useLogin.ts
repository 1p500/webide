"use client";

import { useMutation } from '@tanstack/react-query';
import axiosInstance from '../axiosInstance';
import { LoginRequest, LoginResponse } from '../../../lib/authTypes';
import { useRouter } from 'next/navigation';
import { useProjects } from '../useProjects';


export const useLogin = () => {
  const router = useRouter();
  // 실제 사용 URL
  const API_URL = '/api/auth/login'; 
  // 테스트용 URL
  // const API_URL = '/login'
  // useMutation을 정의할 때 제네릭 타입 설정
  const mutation = useMutation<LoginResponse, Error, LoginRequest>({
    mutationFn: async ({ loginid, password }: LoginRequest): Promise<LoginResponse> => {
      
      const response = await axiosInstance.post<LoginResponse>(API_URL, { loginid, password });
      return response.data;
    },
    onSuccess: (data: LoginResponse) => {
      console.log('로그인 성공:', data);
      // 로그인 성공 시, 토큰을 저장
      localStorage.setItem('userId', data.user.loginid);
      localStorage.setItem('accessToken', data.accessToken);
      localStorage.setItem('refreshToken', data.refreshToken);
      
      useProjects(data.accessToken);
      router.push('/ide/[projectPage]');
    },
    onError: (error) => {
      console.error('로그인 실패:', error.message);
    },
  });

  return mutation;
};
