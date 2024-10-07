"use client";

import Image from 'next/image';
import { SocialLoginGithub, SocialLoginGoogle, SocialLoginGuest } from '@/components/auth/SocialLoginButtons';
import { useRouter } from 'next/navigation';
import { useState } from 'react';
import { useLogin } from '@/components/auth/useLogin';
import Link from 'next/link';
import LoginButton from '@/components/loginForm';

export default function Home() {
  const router = useRouter();
  const { mutate: login, isError } = useLogin(); // useLogin 훅 사용

  // 로그인 ID와 비밀번호 상태 관리
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  // 로그인 버튼 클릭 시 호출될 함수
  const handleLogin = () => {
    if (username && password) {
      // useLogin 훅의 mutate 함수로 로그인 요청 보내기
      login(
        { loginid: username, password }, // loginid와 password를 전달
        {
          onSuccess: () => {
            // 성공 시 /ide로 이동
            router.push('/ide');
          },
          onError: (error) => {
            console.error('로그인 실패:', error);
          },
        }
      );
    } else {
      alert('아이디와 비밀번호를 입력해주세요.');
    }
  };

  return (
    <div className="w-full min-h-screen flex items-center justify-center bg-black">
      <div className="bg-white rounded-3xl flex max-w-3xl mx-auto">
        {/* 왼쪽: 로그인 폼 */}
        <div className="w-1/2 p-5 space-y-4">
          <h2 className="text-5xl font-bold mb-5">Welcome</h2>
          <div className="space-y-4">
            <LoginButton />
            
            <div className="w-full text-center bg-black text-white py-3 rounded-md font-semibold">
              <Link href="/signup">회원가입</Link>
            </div>
          </div>

          {isError && <p className="text-red-500">로그인에 실패했습니다. 다시 시도해주세요.</p>} {/* 에러 메시지 표시 */}

          <p className="text-center mt-5 font-medium">Login with Others</p>
          <div className="flex flex-col mt-4 space-y-2">
            <SocialLoginGoogle />
            <SocialLoginGithub />
            <SocialLoginGuest />
          </div>
        </div>

        {/* 오른쪽: 이미지 */}
        <div className="w-1/2 p-5 flex items-center justify-center">
          <Image
            src="/temp_main_image.png"
            alt="Coding Illustration"
            width="100"
            height="100"
            className="rounded-3xl"
          />
        </div>
      </div>
    </div>
  );
}
