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

  const handleGuestLogin = () =>{
    router.push('/ide?guest=true')
  }

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
