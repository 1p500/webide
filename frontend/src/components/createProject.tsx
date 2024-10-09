"use client";

import { useState } from 'react';
import { useCreateProject } from './useCreateProjects';

interface ProjectModalProps {
  isOpen: boolean;
  onClose: () => void;
}

export default function ProjectAddModal ({ isOpen, onClose }: ProjectModalProps) {
  const [projectName, setProjectName] = useState('');
  const [projectDescription, setProjectDescription] = useState('');

  const createProjectMutation = useCreateProject();

  const handleAddProject = (e: React.FormEvent) => {
    e.preventDefault();

    if (projectName.trim() === '') {
      alert('프로젝트 이름을 입력해주세요.');
      return;
    }

    if (projectDescription.trim() === '') {
      alert('프로젝트 설명을 입력해주세요.');
      return;
    }

    // 프로젝트 생성 요청 보내기
    createProjectMutation.mutate({
      name: projectName,
      description: projectDescription,
    });

    // 모달 닫기
    onClose();
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50">
      {/* 모달 배경 */}
      <div className="absolute inset-0 bg-black bg-opacity-50"></div>

      {/* 모달 내부 */}
      <div className="relative bg-white p-8 rounded-lg shadow-lg max-w-md w-full z-10">
        <h2 className="text-2xl font-bold mb-4">프로젝트 추가</h2>

        <form onSubmit={handleAddProject}>
          {/* 프로젝트 이름 입력 필드 */}
          <div className="mb-4">
            <label htmlFor="project-name" className="block text-sm font-medium text-gray-700">
              프로젝트 이름
            </label>
            <input
              id="project-name"
              type="text"
              required
              className="w-full px-3 py-2 border text-black border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
              placeholder="프로젝트 이름"
              value={projectName}
              onChange={(e) => setProjectName(e.target.value)}
            />
          </div>

          {/* 프로젝트 설명 입력 필드 */}
          <div className="mb-4">
            <label htmlFor="project-description" className="block text-sm font-medium text-gray-700">
              프로젝트 설명
            </label>
            <textarea
              id="project-description"
              required
              className="w-full px-3 py-2 border text-black border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"
              placeholder="프로젝트 설명"
              value={projectDescription}
              onChange={(e) => setProjectDescription(e.target.value)}
            />
          </div>

          {/* 버튼 */}
          <div className="flex justify-end">
            <button
              type="button"
              onClick={onClose}
              className="px-4 py-2 mr-2 text-sm text-gray-700 bg-gray-200 rounded hover:bg-gray-300"
            >
              취소
            </button>
            <button
              type="submit"
              className="px-4 py-2 text-sm text-white bg-blue-600 rounded hover:bg-blue-700"
            >
              추가
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};


