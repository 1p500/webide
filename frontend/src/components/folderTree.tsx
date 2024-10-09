"use client";

import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { GetProjectsProjectIdResponse } from '../../lib/projectTypes';
import axios from 'axios';

type FolderProps = {
  type: "folder"
  name: string
  id: number
}

type FileProps = {
  type: "file"
  id: number
  name: string
  contents: string
}

const fetchProjectsFolder = async (projectId:number):Promise<GetProjectsProjectIdResponse> => {
  const API_URL = `/api/projects/${projectId}`
  try{
    const response = await axios.get(API_URL);
    return response.data;

  } catch(error){
    console.error('Error fetching projects folder', error);
    throw new Error('Failed to fetch')
  }
}


export const useProjectsFolder = (projectId:number) => {
  return useQuery<GetProjectsProjectIdResponse>({
    queryKey: ['projects', projectId],    // 쿼리 키를 명시적인 객체로 전달
    queryFn: ()=> fetchProjectsFolder(projectId),    // 데이터를 가져오는 비동기 함수
    enabled: !!projectId,
  });
};
// const folders = data.folders.map(folder => ({
//   type: "folder",
//   name: folder.name,
  
// }));
// const files = data.files.map(files => ({
//   type: "files",
//   name: files.name,
//   contents: files.content,
// }))


const fetchProjectsSubFoldersAndFiles = async(projectId: number, folderId: number): Promise<GetProjectsProjectIdResponse> => {
  const API_URL = `/api/projects/${projectId}/folders/${folderId}`;
  try{
    const response = await axios.get(API_URL);
    return response.data;

  } catch(error){
    console.error('Error fetching projects folder', error);
    throw new Error('Failed to fetch')
  }
}

export const Tree = ({ projectId }: { projectId: number }) => {
  const { data, isLoading, isError } = useProjectsFolder(projectId);

  if(isLoading) return <p>Loading</p>
  if(isError || !data) return <p>Error</p>

  return (
    <div>
      {data.folders.map((folder) => (
        <Folder type='folder' id={folder.id} name={folder.name} />
      ))}
      {data.files.map((file) => (
        <File type='file' id={file.id} name={file.name} contents={file.content} />
      ))}
    </div>
  );
}

const Folder = ({ type, id, name }: FolderProps) => {
  const [isOpen, setIsOpen] = useState(false); // 폴더 열림/닫힘 상태 관리

  const toggleFolder = () => setIsOpen(!isOpen); // 폴더 열기/닫기 토글

  return (
    <div className='ml-2'>
      <div onClick={toggleFolder} style={{ cursor: 'pointer', fontWeight: 'bold' }}>
        {isOpen ? '📂' : '📁'} {name}
      </div>
      {isOpen && (
        <div className='ml-2'>
          {/* 하위 폴더와 파일을 트리 구조로 표시 (재귀적) */}
          <Tree projectId={id} />
        </div>
      )}
    </div>
  );
};

const File = ({type, id, name, contents}: FileProps) => {
  return (
    <div className='ml-2'>
      📄 {name}
    </div>
  );
}


// const Folder = (folder: Folder) => { 
//   const [isOpen, setIsOpen] = useState(false)

//   const { data } = useQuery({
//     queryKey: [],
//     queryFn: getProjectsSubFolders,
//     enabled: isOpen
//   })

//   return (
//     <div>
//       <button onClick={() => setIsOpen((prev) => !prev)}>{folder.name}</button>
//       {isOpen && data && (
//         <Tree />
//       )}
//   </div>
//   )
// }

// // path 예시 File: "src/app/ide/page.tsx"
// // path 예시 Folder: "src/app/ide"

// const makeTree = ({ folder, files}: ReadFolder): 트리_그릴때_내가_원하는_데이터_모양 => { 
//   return (
//     if (folders.path === subfolder.path | folders.path === files.path){
      
//     }
//   )
// }


// const Folder = (props: Folder) => { 
//   return <button>
//     {props.name}
//     {props.contents.map((contentProps) => {
//         if (contentProps.type === "file") {
//           return <File {...contentProps} />
//         } else {
//           return <Folder {...contentProps} />
//         }
//       }
//     )}
//   </button>
// }

// const File = (props: File) => { 
//   return <button onClick={() => { }}>{props.name}</button>
// }

// type Props = {
//   tree: Leaf[]
// }
// const FolderTree = ({ tree }: Props) => { 
//   return tree.map((contentProps) => { 
//     if (contentProps.type === "file") {
//           return <File {...contentProps} />
//         } else {
//           return <Folder {...contentProps} />
//         }
//   })
// }