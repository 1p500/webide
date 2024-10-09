"use client"

import React from 'react';
import '../globals.css'; // 동일한 글로벌 스타일 적용

export default function GuestIdeLayout({ children }: { children: React.ReactNode }) {
    return (
        <div className="bg-gray-900 text-white h-screen">
            <main className="w-full"> {/* 메인 IDE 영역 */ }
                {children}
            </main>
            
        </div>
    );
}
