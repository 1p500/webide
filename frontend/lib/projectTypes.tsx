// 공통 인터페이스 정의
interface BaseFolder {
  folderName: string;
  parentFolderId?: number; // 선택적으로 사용 가능하도록 설정
}

interface BaseFile {
  fileName: string;
  content: string;
}

interface ProjectBase {
  name: string;
  description: string;
}

// 요청 인터페이스
export interface CreateProjectFolderRequest extends BaseFolder {}

export interface CreateSubfolderRequest extends BaseFolder {
  parentFolderId: number; 
}

export interface UpdateFolderRequest extends BaseFolder {
  parentFolderId: number;  
}

export interface CreateFileRequest extends BaseFile {}

export interface UpdateFileRequest extends BaseFile {}

export interface CreateProjectRequest extends ProjectBase {}
