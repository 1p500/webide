"use client";

import React, { useState, useEffect } from 'react';
import ProjectListModal from '@/components/projectModal'; // Modal 컴포넌트 가져오기
import CodeEditor from '@/components/codeEditor';
import { Tree } from '@/components/folderTree';
// import { GetProjectsRootResponse, Folder } from '../../../../lib/projectTypes';

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

  // const { data } = useQuery({
  //   queryKey: [],
  //   queryFn: getProjectsRoot
  // })
  // const data = {} as GetProjectsRootResponse

    return (
    <div>
        <h1 className="text-2xl">IDE 메인 페이지</h1>
        <button onClick={() => setIsModalOpen(true)} className="open-modal-btn">
          Select Project
        </button>
      {/* My Projects Modal */}
        <ProjectListModal isOpen={isModalOpen}
            onClose={() => setIsModalOpen(false)}
            token={token}
            onProjectSelect={handleProjectSelect}
        >
        </ProjectListModal>
        {selectedProjectId ? (
          <div>
          <Tree projectId={selectedProjectId} />
          <CodeEditor />
          </div>
        ) : (
        <p>Please select a project to begin working on it.</p>
        )}
        {/* <Chat /> */}
    </div>
    );
}

