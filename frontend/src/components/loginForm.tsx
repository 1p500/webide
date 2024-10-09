"use client";

import { useState } from 'react';
import { useLogin } from './auth/useLogin';
import { LockIcon, UserIcon } from './icons/icons';

export default function LoginButton() {
  const [loginid, setLoginid] = useState('');  // 로그인 ID 상태 관리
  const [password, setPassword] = useState(''); // 비밀번호 상태 관리
  const { mutate: login, isPending, isError } = useLogin();

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault(); // 폼 제출 기본 동작 방지
    login({ loginid, password }); // mutate 함수를 호출하여 로그인 요청 실행
  };

  return (
          
    <form className="space-y-4 w-full text-center bg-white py-3 rounded-md font-semibold"
          onSubmit={handleSubmit}>
      <div className='relative'>
        <input
          className='w-full pl-10 border text-black placeholder-gray-200 font-mono border-black-800 rounded-md'
          type="text"
          value={loginid}
          onChange={(e) => setLoginid(e.target.value)}
          placeholder="Login ID"
          required
        />
        <div className="absolute left-3 top-1/2 transform -translate-y-1/2">
          <UserIcon />
        </div>  
      </div>
      <div className='relative'>
        <input
          className='w-full pl-10 border text-black placeholder-gray-200 font-mono border-black-800 rounded-md'
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="PASSWORD"
          required
        />
        <div className="absolute left-3 top-1/2 transform -translate-y-1/2">
          <LockIcon />
        </div>  
      </div>
      
      <button className='w-full py-3 rounded-md font-semibold bg-black text-white'
              type="submit" disabled={isPending}>
        {isPending ? 'Logging in...' : 'Login'}
      </button>

      {isError && <p>로그인에 실패했습니다. 다시 시도해주세요.</p>}
    </form>
  );
}
