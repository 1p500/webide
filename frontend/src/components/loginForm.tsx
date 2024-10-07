import { useState } from 'react';
import { useLogin } from './auth/useLogin';

export default function LoginButton() {
  const [loginid, setLoginid] = useState('');  // 로그인 ID 상태 관리
  const [password, setPassword] = useState(''); // 비밀번호 상태 관리
  const { mutate: login, isPending, isError, data } = useLogin();

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault(); // 폼 제출 기본 동작 방지
    login({ loginid, password }); // mutate 함수를 호출하여 로그인 요청 실행
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        type="text"
        value={loginid}
        onChange={(e) => setLoginid(e.target.value)}
        placeholder="Login ID"
        required
      />
      <input
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        placeholder="Password"
        required
      />
      <button type="submit" disabled={isPending}>
        {isPending ? 'Logging in...' : 'Login'}
      </button>

      {isError && <p>로그인에 실패했습니다. 다시 시도해주세요.</p>}
    </form>
  );
}
