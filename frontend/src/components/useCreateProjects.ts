"use client";

import { useMutation, useQueryClient } from '@tanstack/react-query';
import axiosInstance from './axiosInstance';
import { PostProjectCreateRequest, PostProjectCreateResponse } from '../../lib/projectTypes';


export const createProject = async (token: string, name: string, description: string) => {
  try {
    const response = await axiosInstance.post('/api/projects', {
      name,
      description,
    }, {
      headers: {
        Authorization: `Bearer ${token}`, // 인증을 위한 토큰
      },
    });
    return response.data; // 생성된 프로젝트 데이터 반환
  } catch (error) {
    console.error('프로젝트 생성 중 오류 발생:', error);
    throw new Error('프로젝트 생성 실패');
  }
};

// useCreateProject 훅 (프로젝트 생성)
export const useCreateProject = () => {
  const queryClient = useQueryClient();
  const API_URL = '/project/create'
  return useMutation<PostProjectCreateResponse, Error, PostProjectCreateRequest>({
    mutationFn: async (newProjectData: PostProjectCreateRequest) => {
      const response = await axiosInstance.post<PostProjectCreateResponse>(API_URL, {
        name: newProjectData.name,
        description: newProjectData.description,
      });
      return response.data;
    },
      onError: (error) => {
        console.error('프로젝트 생성 실패:', error);
      },
    }
  );
};

