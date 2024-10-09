"use client"

import React from 'react';
import '@/app/globals.css'; // 동일한 글로벌 스타일 적용
import { FolderAddTransparent, FileAddTransparent } from '@/components/icons/icons';

export default function IdeLayout({ children }: { children: React.ReactNode }) {
    return (
        <div className="bg-gray-900 text-white h-screen">
            <div className="flex h-full">
            <aside className="w-1/5 bg-gray-800">
            <div className='flex-col space-s-4'>
                    <div className='flex justify-between items-center p-3'>
                        <ul className='flex space-x-4 ml-auto'>
                            <li>
                                <button>
                                    <FolderAddTransparent className='w-5 h-5'/>
                                </button>
                            </li>
                            <li>
                                <button>
                                    <FileAddTransparent className='w-5 h-5'/>
                                </button>
                                </li>
                            </ul>
                    </div>
                    <div>

                    </div>
                </div>
                </aside>
                <main className="flex-1 p-4"> {/* 메인 IDE 영역 */ }
                    {children}
                </main>
            </div>
        </div>
    );
}
