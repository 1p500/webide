"use client";

import Image from 'next/image';
import { SocialLoginGoogle, SocialLoginGuest, SocialLoginKakao } from '@/components/SocialLoginButtons';
import { useRouter } from 'next/navigation';
import Link from 'next/link';

export default function Home() {
  const router = useRouter();

  return (
    <div className="w-full min-h-screen flex items-center justify-center bg-black">
      <div className="bg-white rounded-3xl flex max-w-3xl mx-auto">
        {/* 왼쪽: 로그인 폼 */}
        <div className="w-1/2 p-5 space-y-4">
          <h2 className="text-5xl font-bold mb-5">Welcome</h2>
          <div className="space-y-4">
            <div>
              <input
                type="text"
                placeholder="Username"
                className="w-full p-3 rounded-md border border-gray-300"
              />
            </div>
            <div>
              <input
                type="password"
                placeholder="Password"
                className="w-full p-3 rounded-md border border-gray-300"
              />
            </div>
            <Link href="/ide">
              <div className="w-full text-center bg-black text-white py-3 rounded-md font-semibold"            >
                Login
              </div>
            </ Link>
          
            <Link href="/signup">
              <div className="w-full text-center bg-black text-white py-3 rounded-md font-semibold"            >
                회원가입
              </div>
            </ Link>
          </div>

          <p className="text-center mt-5 font-medium">Login with Others</p>
          <div className="flex flex-col mt-4 space-y-2">
            <SocialLoginGoogle />
            <SocialLoginKakao />
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
