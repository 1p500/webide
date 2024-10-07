// 로그인 요청 데이터 타입
export interface LoginRequest {
  loginid: string;
  password: string;
}

// 로그인 응답 데이터 타입
export interface LoginResponse {
  accessToken: string;
  refreshToken: string;
  user: {
    id: number;
    loginid: string;
    name: string;
  };
}

// 회원가입 요청 데이터 타입
export interface SignupRequest {
  loginid: string;
  password: string;
  name: string;
}

// 회원가입 응답 데이터 타입
export interface SignupResponse {
  id: number;
  loginid: string;
  name: string;
  createdAt: string;
}

// 소셜 로그인 응답 덷이터 타입
export interface SocialLoginResponse {
  accessToken: string;
  user: {
    id: number;
    loginid: string;
    name: string;
  };
}
