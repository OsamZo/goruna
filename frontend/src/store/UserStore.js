import { defineStore } from 'pinia';
import { getGoogleLoginUrl, handleGoogleAuthResult, addNickname } from '@/api/user/UserApi';

export const useUserStore = defineStore('user', {
  state: () => ({
    userInfo: null,
    isAuthenticated: false,
    token: null,
    userSeq: null // userSeq 상태 추가
  }),
  actions: {
    async loginWithGoogle() {
        try {
          const loginUrl = await getGoogleLoginUrl();
          console.log('구글 로그인 URL:', loginUrl); // 디버깅용 로그
          window.location.href = loginUrl; // 올바른 URL로 리다이렉트하는지 확인
        } catch (error) {
          console.error('구글 로그인 URL 요청 중 오류:', error);
        }
      },
      async processGoogleAuthResult(authCode) {
        try {
          const result = await handleGoogleAuthResult(authCode);
      
          if (result === 'signup') {
            this.isAuthenticated = true; // 로그인 처리 완료
            this.token = result.token;
            localStorage.setItem('jwtToken', this.token);
      
            // 토큰에서 userSeq 추출
            this.decodeTokenAndSetUserSeq(this.token);
      
            console.log('닉네임 추가가 필요합니다. 모달 창을 표시할 준비가 되었습니다.');
          } else if (result === 'login') {
            this.token = result.token;
            localStorage.setItem('jwtToken', this.token);
      
            // 토큰에서 userSeq 추출
            this.decodeTokenAndSetUserSeq(this.token);
      
            this.isAuthenticated = true;
            this.setUserInfo(result.user);
      
            console.log('로그인 성공, 닉네임 추가 모달 창을 표시합니다.');
          }
      
          // 3초 지연 후 페이지 리다이렉션을 테스트할 때만 사용
          if (this.isAuthenticated && !this.userInfo?.userNickname) {
            setTimeout(() => {
              console.log('닉네임 추가 모달 창을 표시할 준비가 되었습니다.');
              // 모달 표시 코드가 프론트엔드에서 실행됨
            }, 3000); // 3초 지연
          }
        } catch (error) {
          console.error('구글 로그인 처리 중 오류:', error);
        }
      },
      async addNickname(nickname) {
        console.log('addNickname 호출 시 userSeq:', this.userSeq); // 디버깅용 로그
        if (!this.userSeq) {
          console.error('userSeq가 없습니다. JWT 토큰을 확인하세요.');
          return;
        }
      
        try {
          const response = await addNickname(this.userSeq, nickname, this.token);
          console.log('닉네임 추가 성공:', response);
      
          // 닉네임 추가 후 로그인 상태 업데이트
          this.isAuthenticated = true;
          this.userInfo = { ...this.userInfo, userNickname: nickname };
      
          // 메인 페이지로 리다이렉션 (3초 지연)
          console.log('닉네임 추가 완료, 3초 후 메인 페이지로 리다이렉션 준비 중'); // 디버깅용 로그
          setTimeout(() => {
            window.location.href = '/';
          }, 3000); // 3초 지연
        } catch (error) {
          console.error('닉네임 추가 중 오류:', error);
        }
      },
      decodeTokenAndSetUserSeq(token) {
        if (!token) return;
      
        try {
          // 토큰의 payload 부분을 디코딩
          const base64Url = token.split('.')[1];
          const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
          const jsonPayload = decodeURIComponent(atob(base64).split('').map(c => {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
          }).join(''));
      
          const payload = JSON.parse(jsonPayload);
          this.userSeq = payload.userSeq; // payload에서 userSeq 추출
          console.log('추출된 userSeq:', this.userSeq); // 디버깅용 로그
        } catch (error) {
          console.error('토큰에서 userSeq 추출 중 오류:', error);
        }
      },
    setUserInfo(user) {
      this.userInfo = user;
      this.isAuthenticated = true;
    },
    logout() {
      this.userInfo = null;
      this.isAuthenticated = false;
      this.token = null;
      this.userSeq = null;
      localStorage.removeItem('jwtToken');
      window.location.href = '/login';
    }
  }
});
