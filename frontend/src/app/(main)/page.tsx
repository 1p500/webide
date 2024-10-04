"use client";

import Image from 'next/image';
import { SocialLoginGoogle, SocialLoginGuest, SocialLoginKakao } from '@/component/SocialLoginButtons';
import { useRouter } from 'next/navigation';

export default function Home() {
  const router = useRouter();

  const handleSignupClick = () => {
    router.push('/signup'); // /signup 페이지로 이동
  };

  const handleLoginClick = () =>{
    router.push('/ide')
  }

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
            <button className="w-full bg-black text-white py-3 rounded-md font-semibold"
                    onClick={handleLoginClick}
            >
              Login
            </button>
          </div>
            <button className="w-full bg-gray-900 text-white py-3 rounded-md font-semibold"
                    onClick={handleSignupClick}
            >
              회원가입
            </button>
          

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
