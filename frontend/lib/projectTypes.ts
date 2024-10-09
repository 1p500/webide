
// 프로젝트 생성
export interface PostProjectCreateRequest {
  name: string;
  description: string;
}

export interface PostProjectCreateResponse {
  projectId: number;
  name: string;
  description: string;
}

//프로젝트 수정
export interface PutProjectProjectIdRequestResponse {
  projectId: number;
  name: string;
  description: string;
}

export interface GetProjectListResponse{
  projectId: number;
  name: string;
  description: string;
}

// 프로젝트 내 폴더 생성
export interface PostProjectsFoldersRequest{
  forlderName:string;
}


export interface PostProjectsFoldersResponse{
  id:number;
  name: string;
  path: string;
  files: null;
}

// 폴더 내 폴더 생성
export interface PostProjectsSubFoldersRequest{
  forlderName:string;
  parentFolderId:number;
}

export interface PostProjectsSubFoldersResponse{
  id:number;
  name: string;
  path: string;
  files: null;
}

// 프로젝트 내 폴더 파일 조회
export interface GetProjectsProjectIdResponse{
  folders: PatchProjectsFoldersResponse[];
  files: PatchProjectsFoldersFilesResponse[];
}

// 폴더 수정
export interface PatchProjectsFoldersRequest{
  folderName:string;
  parentFolderId: number;

}

export interface PatchProjectsFoldersResponse{
  id: number;
  name: string;
  path: string;
}

// 파일 수정
export interface PatchProjectsFoldersFilesRequest{
  fileName: string;
  content: string;
}
export interface PatchProjectsFoldersFilesResponse{
  id: number;
  name: string;
  path: string;
  size: number;
  content: string;
}

// 프로젝트 내 파일 생성 & 폴더 내 파일 생성
export interface PostProjectsFilesRequest{
  fileName:string;
  content:string;

}

export interface PostProjectsFilesResponse{
  id: number;
  name: string;
  path: string;
  size: number;
  content: string;
}



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

// 폴더 생성
export interface CreateProjectFolderRequest extends BaseFolder {}

// 폴더 내 폴더 생성
export interface CreateSubfolderRequest extends BaseFolder {
  parentFolderId: number; 
}

// 폴더 수정
export interface UpdateFolderRequest extends BaseFolder {
  parentFolderId: number;  
}

export interface CreateFileRequest extends BaseFile {}

export interface UpdateFileRequest extends BaseFile {}

export interface CreateProjectRequest extends ProjectBase {}



// 응답 인터페이스

export interface CreateFolder{
  id:number;
  name:string;
  path:string;
  files: null;

}


export interface ReadFolder{
  folders:BaseFolder[];
  files:BaseFile[];
  subFolders:BaseFolder[];
}


export interface UpdateFolder{
  id:number;
  name:string;
  path:string;
}

export interface CreateUpdateFile{
  id: number,
  name: string,
  path: string,
  size: number,
  content: string
}

export interface CreateReadUpdateProject{
  projectId: number
  name: string
  description: string
}

//'api/projects/{projectId}' GET
//'api/projects/{projectId}/folders/{folderId}' GET
export interface GetProjectsPathParm {
  projetId: string
}

// 재귀적으로 API를 호출해야 트리를 그릴 수 있는 형태.

type Folder = {
  id:number;
  name:string;
  path:string;
  files: null;
}

type File = {
  id: number,
  name: string,
  path: string,
  size: number,
  content: string
}

export interface GetProjectsRootResponse {
  folders: Folder[]
  files: File[]
}

export interface GetProjectsSubFoldersResponse {
  subFolders: Folder[]
  files: File[]
}

// const getProjectsRoot = () => {
//   return axiosInstance.get<GetProjectsRootResponse>('api/projects/{projectId}')
// }

// const getProjectsSubFolders = () => {
//   return axiosInstance.get<GetProjectsSubFoldersResponse>('api/projects/{projectId}/folders/{folderId}')
// }

// Http Method
// GET PATCH POST DELETE
// 
