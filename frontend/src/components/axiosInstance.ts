import axios from 'axios';

// //테스트용
// import MockAdapter from 'axios-mock-adapter';

// Axios 인스턴스 생성
const axiosInstance = axios.create({
  baseURL: 'https://api.example.com', // 실제로는 사용되지 않음
  headers: {
    'Content-Type': 'application/json', 
  },
});
export default axiosInstance;

// // Mock Adapter 설정
// const mock = new MockAdapter(axiosInstance);


// // 사용자 데이터 저장소 (메모리 사용)
// const users: Array<{ loginid: string; password: string; name: string; projects: Array<any> }> = [];

// // 회원가입 Mock API
// mock.onPost('/signup').reply((config) => {
//   const { loginid, password, name } = JSON.parse(config.data);
  
//   // 중복된 사용자가 있는지 확인
//   const existingUser = users.find((user) => user.loginid === loginid);
//   if (existingUser) {
//     return [400, { message: 'User already exists' }];
//   }

//   // 새 사용자 추가
//   users.push({ loginid, password, name, projects: [] });
//   return [200, { message: 'User registered successfully' }];
// });




// // 로그인 Mock API
// mock.onPost('/login').reply((config) => {
//   const { loginid, password } = JSON.parse(config.data);

//   // 등록된 사용자 조회
//   const user = users.find((user) => user.loginid === loginid && user.password === password);

//   if (user) {
//     return [200, {
//       accessToken: 'mockAccessToken',
//       refreshToken: 'mockRefreshToken',
//       user: {
//         id: 1,
//         loginid: user.loginid,
//         name: user.name,
//         projects: user.projects,
//       },
//     }];
//   } else {
//     return [401, { message: 'Invalid credentials' }];
//   }
// });

// mock.onGet('/projects').reply((config) => {
  
//   const userId = config.params.userId;
  
//   const user = users.find((user) => user.loginid === userId);

//   if (user) {
//     return [200, user.projects];  // 해당 사용자의 프로젝트 목록 반환
//   } else {
//     return [401, { message: 'Unauthorized' }];
//   }
// });



