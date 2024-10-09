"use client";

import { useMutation } from '@tanstack/react-query';
import axiosInstance from '../axiosInstance';
import { SignupRequest, SignupResponse } from '../../../lib/authTypes';
import { useRouter } from 'next/navigation';

export const useSignup = () => {
  const router = useRouter();
  // 실제사용
  const API_URL = '/api/auth/signup'

  //테스트용
  // const API_URL = '/signup';

  return useMutation<SignupResponse, Error, SignupRequest>({
    mutationFn: async (signupData: SignupRequest) => {
      const response = await axiosInstance.post<SignupResponse>(API_URL, signupData);
      return response.data;
    },
    onSuccess:(data)=>{
      console.log('회원가입 성공:', data);
      router.push('/');
    },
    onError: (error) => {
      console.error('회원가입 실패:', error);
    },
  });
};
