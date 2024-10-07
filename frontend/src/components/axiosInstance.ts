// src/axiosInstance.ts
import axios from 'axios';
import MockAdapter from 'axios-mock-adapter';

// Axios 인스턴스 생성
const axiosInstance = axios.create({
  baseURL: 'https://api.example.com', // 실제로는 사용되지 않음
  headers: {
    'Content-Type': 'application/json',
  },
});

// Mock Adapter 설정
const mock = new MockAdapter(axiosInstance);

// 로그인 요청에 대해 Mock 데이터 설정
mock.onPost('/login').reply(200, {
  accessToken: 'mockAccessToken',
  refreshToken: 'mockRefreshToken',
  user: {
    id: 1,
    loginid: 'mockLoginId',
    name: 'Mock User',
  },
});

export default axiosInstance;
