import { useMutation, UseMutationResult } from '@tanstack/react-query';
import axiosInstance from '../axiosInstance';
import { LoginRequest, LoginResponse } from '../../../lib/authTypes';
import { useRouter } from 'next/navigation';

export const useLogin = (): UseMutationResult<LoginResponse, Error, LoginRequest, unknown> => {
  const router = useRouter();

  // useMutation을 정의할 때 제네릭 타입 설정
  return useMutation<LoginResponse, Error, LoginRequest>({
    mutationFn: async ({ loginid, password }: LoginRequest): Promise<LoginResponse> => {
      const response = await axiosInstance.post<LoginResponse>('/login', { loginid, password });
      return response.data;
    },
    onSuccess: (data: LoginResponse) => {
      console.log('로그인 성공:', data);
      // 로그인 성공 시, 토큰을 저장
      localStorage.setItem('accessToken', data.accessToken);
      localStorage.setItem('refreshToken', data.refreshToken);

      // 성공적으로 로그인하면 IDE 페이지로 이동
      router.push('/ide');
    },
    onError: (error) => {
      console.error('로그인 실패:', error.message);
    },
  });
};
