"use client";

import { useQuery } from '@tanstack/react-query';
import { CreateReadUpdateProject } from '../../lib/projectTypes';
import { GetProjectListResponse } from '../../lib/projectTypes';
import axiosInstance from './axiosInstance';

export const fetchProjects = async (accessToken: string): Promise<CreateReadUpdateProject[]> => {
  try {
    const API_URL = '/projects/list'
    const response = await axiosInstance.get<GetProjectListResponse[]>(API_URL,{
      headers: {
        Authorization: `Bearer ${accessToken}`, // accessToken을 Authorization 헤더에 추가
      },
    });
    return response.data;
  } catch (error) {
    console.error('Error fetching projects:', error);
    return [];
  }
};


export const useProjects = (accessToken: string) => {
  return useQuery<CreateReadUpdateProject[]>({
    queryKey: ['projects', accessToken],    // 쿼리 키를 명시적인 객체로 전달
    queryFn: ()=> fetchProjects(accessToken),    // 데이터를 가져오는 비동기 함수
    enabled: !!accessToken,
  });
};