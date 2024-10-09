"use client";

import React, { useState, useEffect } from 'react';
import ProjectListModal from '@/components/projectModal'; // Modal 컴포넌트 가져오기
import CodeEditor from '@/components/codeEditor';
import { Tree } from '@/components/folderTree';
import Chat from '@/components/chatWindow';
// import { GetProjectsRootResponse, Folder } from '../../../../lib/projectTypes';
import { FolderAddTransparent, FileAddTransparent } from '@/components/icons/icons';
import { useProjects } from '@/components/useProjects';


export default function IdePage() {
    const [isModalOpen, setIsModalOpen] = useState(true);
    const [token, setToken]  = useState("");
    const [selectedProjectId, setSelectedProjectId] = useState<number | null>(null);

    const handleProjectSelect = (projectId: number) => {
      setSelectedProjectId(projectId);
      setIsModalOpen(false); // 프로젝트 선택 후 모달 닫기
    };

    useEffect(()=>{
      const storeAccessToken = localStorage.getItem('accessToken') || "";
      setToken(storeAccessToken);
    }, [])
    useProjects(token);
  // const { data } = useQuery({
  //   queryKey: [],
  //   queryFn: getProjectsRoot
  // })
  // const data = {} as GetProjectsRootResponse

    return (
    <div className='flex h-screen'>
      <aside className="w-1/6 h-full bg-gray-800">
        <div className='flex-col space-s-4'>
          <div className='flex justify-between items-center p-3'>
            <ul className='flex space-x-4 ml-auto'>
              <li>
                <button> <FolderAddTransparent className='w-5 h-5'/> </button>
              </li>
              <li>
                <button> <FileAddTransparent className='w-5 h-5'/> </button>
              </li>
            </ul>
          </div>
          <div>
          {selectedProjectId ? (
            <Tree projectId={selectedProjectId} />
          ):(<p>Please select a project to begin working on it.</p>)
          }
          </div>
        </div>
        </aside>
      <div className='flex-col w-3/6 justify-between border-white'>
          <div className='flex justify-between p-2 ml-auto'>
            <h1 className="text-2xl">IDE 메인 페이지</h1>
            <button onClick={() => setIsModalOpen(true)} className="open-modal-btn">
              Select Project
            </button>
          </div>
          
          {/* My Projects Modal */}
          <ProjectListModal isOpen={isModalOpen}
              onClose={() => setIsModalOpen(false)}
              token={token}
              onProjectSelect={handleProjectSelect}
          >
          </ProjectListModal>
          <div>
          {selectedProjectId ? (
            <CodeEditor />
          ) : ( 
          // <p>Please select a project to begin working on it.</p>
            <CodeEditor />
          )}
              
          </div>
      </div>
      <div className='w-2/6 h-100 bg-black text-white'>
        <Chat />    
      </div>
    </div>
    );
}

