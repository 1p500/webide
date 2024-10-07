"use client";

import React, { useState } from 'react';
import Modal from '@/components/projectModal'; // Modal 컴포넌트 가져오기
import { useSearchParams } from 'next/navigation';

export default function IdePage() {
    const searchParams = useSearchParams();
    const isGuest = searchParams.get('guest') == 'true';
    const [isModalOpen, setIsModalOpen] = useState(!isGuest);
  // 예시 프로젝트 데이터 (API를 통해 불러올 수 있음)
    const projects = [
    { id: 1, name: 'Project 1', description: 'First project description' },
    { id: 2, name: 'Project 2', description: 'Second project description' },
    { id: 3, name: 'Project 3', description: 'Third project description' },
    ];

    return (
    <div>
        <h1 className="text-2xl">IDE 메인 페이지</h1>

      {/* My Projects Modal */}
        <Modal isOpen={isModalOpen} onClose={() => setIsModalOpen(false)}>
        <ul>
            {projects.map((project) => (
            <li key={project.id} className="mb-4 text-black">
                <h3 className="text-lg font-bold">{project.name}</h3>
                <p>{project.description}</p>
            </li>
            ))}
        </ul>
        </Modal>
    </div>
    );
}
