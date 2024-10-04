"use client"

import React from 'react';
import '../globals.css'; // 동일한 글로벌 스타일 적용

export default function IdeLayout({ children }: { children: React.ReactNode }) {
    return (
        <html lang="en">
            <body className="bg-gray-900 text-white">
                <div className="flex h-screen">
                    <aside className="w-1/4 bg-gray-800"> {/* 사이드바 */}</aside>
                    <main className="flex-1 p-4"> {/* 메인 IDE 영역 */ }
                        {children}
                    </main>
                </div>
            </body>
        </html>
    );
}
