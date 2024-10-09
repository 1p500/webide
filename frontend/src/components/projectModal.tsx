"use client";

import React, { useState } from 'react';

import { useProjects } from './useProjects';
import { CreateReadUpdateProject } from '../../lib/projectTypes';
import ProjectAddModal from './createProject';
// import { useRouter } from 'next/navigation';

interface ModalProps {
  isOpen: boolean;
  onClose: () => void;
  token: string;
  onProjectSelect: (projectId:number)=> void; 
}

// ProjectListModal 입장에서는 로그인 여부를 알아야 하는가?
// 모달은 UI일 뿐. 로직은 외부에서 핸들링 해야 함.
export default function ProjectListModal({ isOpen, onClose, token, onProjectSelect }: ModalProps) {
  const {data:projects, isLoading, isError} = useProjects(token);
  const [isModalOpen, setIsModalOpen] = useState(false);

  const openModal = () => setIsModalOpen(true);
  if (!isOpen) return null;

  if (isLoading){
    return <p>Loading</p>
  }
  if (isError){
    return <p>Error</p>
  }
  // const router = useRouter();

  const handleProjectClick = (projectId: number) => {
    onProjectSelect(projectId); 
  };
  return (
    <div className='fixed inset-0 flex items-center justify-center z-50 bg-black bg-opacity-50'>
      {/* 모달 배경 */}
      <div className='absolute inset-0 bg-black bg-opacity-50 pointer-events-none'></div>

      {/* 모달 내부 */}
      <div className='relative bg-white p-6 rounded-md shadow-sm max-w-lg w-full pointer-events-auto'>
        <div className='flex justify-between items-center'>
          <h2 className='text-black'>My Projects</h2>
          <button onClick={onClose} className='text-black text-xl font-bold'>x</button>
        </div>
        <div className='mt-4'>
          {projects && projects.length === 0 ? (
            <div className='text-center'>
              <button className="mt-4 bg-blue-500 text-white p-2 rounded"
                onClick={openModal}
              >+ New Project</button>
              <ProjectAddModal
                isOpen={isModalOpen}
                onClose={() => setIsModalOpen(false)}
              >

              </ProjectAddModal>

            </div>
          ):(
            <ul>
              {projects && projects.map((project: CreateReadUpdateProject) => (
                <li key={project.projectId} className="mb-4">
                  <h3 className="text-lg font-bold text-black"
                    onClick={() => handleProjectClick(project.projectId)}
                    >{project.name}</h3>
                  <p>{project.description}</p>
                </li>
              ))}
            </ul>
          )}
        </div>
      </div>
    </div>
  );
}
