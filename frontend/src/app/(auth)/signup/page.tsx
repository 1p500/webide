// src/app/(auth)/signup/page.tsx
"use client";

import { useSignup } from '@/components/auth/useSignup';
import { useState } from 'react';


export default function SignupPage() {

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [name, setName] = useState('');

  const signupMutation = useSignup();

  const handleSignup = (e: React.FormEvent) => {
    e.preventDefault();
    if(password !== confirmPassword){
      alert('비밀번호가 일치하지 않습니다.');
      return;
    }
    signupMutation.mutate({
      loginid: email,
      password,
      name,
    });
  }

  return (
    <div className="flex items-center justify-center h-screen bg-gray-50">
      <div className="w-full max-w-md p-8 space-y-8 bg-white rounded-lg shadow-md">
        <h2 className="text-3xl font-bold text-center">회원가입</h2>

        <form className="mt-8 space-y-6">
          {/* 이메일 입력 필드 */}
          <div>
            <label
              htmlFor="email"
              className="block text-sm font-medium text-gray-700"
            >
              아이디 (이메일)
            </label>
            <div className="flex mt-1">
              <input
                id="email"
                name="email"
                type="email"
                required
                className="flex-1 px-3 py-2 text-black border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                placeholder="이메일 주소"
                value={email}
                onChange={(e)=> setEmail(e.target.value)}
              />
              <button
                type="button"
                className="ml-2 px-4 py-2 text-sm font-medium text-white bg-gray-700 rounded-md hover:bg-gray-800 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-500"
              >
                중복 확인
              </button>
            </div>
          </div>

          {/* 비밀번호 입력 필드 */}
          <div>
            <label
              htmlFor="password"
              className="block text-sm font-medium text-gray-700"
            >
              비밀번호
            </label>
            <input
              id="password"
              name="password"
              type="password"
              required
              className="w-full px-3 py-2 mt-1 text-black border  border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              placeholder="비밀번호"
              value={password}
              onChange={(e)=>setPassword(e.target.value)}
            />
          </div>

          {/* 비밀번호 확인 입력 필드 */}
          <div>
            <label
              htmlFor="confirm-password"
              className="block text-sm font-medium text-gray-700"
            >
              비밀번호 확인
            </label>
            <input
              id="confirm-password"
              name="confirm-password"
              type="password"
              required
              className="w-full px-3 py-2 mt-1 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              placeholder="비밀번호 확인"
              value={confirmPassword}
              onChange={(e)=>setConfirmPassword(e.target.value)}
            />
          </div>
          {/* 이름 입력 */}
          <div>
            <label
              htmlFor="user-name"
              className="block text-sm font-medium text-gray-700"
            >
              이름
            </label>
            <input
              id="user-name"
              name="user-name"
              type="text"
              required
              className="w-full px-3 py-2 mt-1 text-black border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              placeholder="이름"
              value={name}
              onChange={(e)=>setName(e.target.value)}
            />
          </div>
          {/* 가입 버튼 */}
          <div>
            <button
              type="submit"
              className="w-full px-4 py-2 text-sm font-medium text-white bg-black rounded-md hover:bg-gray-800 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-gray-500"
              onClick={handleSignup}
            >
              가입하고 시작하기
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
