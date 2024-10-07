"use client";

import { useMutation } from '@tanstack/react-query';
import axiosInstance from '../axiosInstance';
import { SignupRequest, SignupResponse } from '../../../lib/authTypes';
import { useRouter } from 'next/navigation';

export const useSignup = () => {
  const router = useRouter();

  return useMutation<SignupResponse, Error, SignupRequest>({
    mutationFn: async (signupData: SignupRequest) => {
      const response = await axiosInstance.post<SignupResponse>('/signup', signupData);
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
