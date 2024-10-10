import { NextResponse } from 'next/server';

// POST 요청을 처리하는 함수
export async function POST(request: Request) {
  try {
    const signupData = await request.json();

    // 여기서 실제로 회원가입 로직을 처리합니다 (예: 데이터베이스에 사용자 정보 저장)
    
    // 회원가입 성공 시 응답
    return NextResponse.json({ message: '회원가입 성공', signupData });
  } catch (error) {
    // 에러 처리
    return NextResponse.json({ message: '회원가입 실패', error }, { status: 500 });
  }
}
