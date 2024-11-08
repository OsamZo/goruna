<script setup>
import '@/assets/css/reset.css';
import { useUserStore } from '@/store/userStore';
import { ref, onMounted, watch } from 'vue';
import ModalNickname from '@/components/ModalNickname.vue';

const userStore = useUserStore();
const showNicknameModal = ref(false);

const handleLoginClick = async () => {
  console.log('로그인 버튼 클릭됨'); // 디버깅용 로그
  await userStore.loginWithGoogle();
};

const processAuthCodeAndOpenModal = async () => {
  if (window.location.search.includes('code')) {
    const urlParams = new URLSearchParams(window.location.search);
    const authCode = urlParams.get('code');
    console.log('감지된 authCode:', authCode); // 디버깅용 로그

    if (authCode) {
      try {
        await userStore.processGoogleAuthResult(authCode);
        console.log('구글 인증 성공 후 상태:', userStore.isAuthenticated); // 상태 변화 확인
        console.log('로그인 후 userInfo 상태:', userStore.userInfo); // 디버깅용 로그

        // 닉네임 모달 창 표시 조건
        if (userStore.isAuthenticated && !userStore.userInfo?.userNickname) {
          showNicknameModal.value = true;
        }
      } catch (error) {
        console.error('구글 인증 처리 중 오류:', error);
      }
    }
  }
};

// 상태 변화를 감지하여 모달 창 표시
watch(
  () => userStore.isAuthenticated,
  (newVal) => {
    if (newVal && !userStore.userInfo?.userNickname) {
      showNicknameModal.value = true;
    }
  }
);

// 컴포넌트가 마운트될 때 인증 코드를 처리
onMounted(() => {
  processAuthCodeAndOpenModal();
});
</script>

<template>
  <div class="content-box">
    <div class="login-title">SNS 계정으로 간편 로그인</div>
    <div class="gray-btn">
      <img src="https://goruna.s3.us-west-1.amazonaws.com/1a90a548-a272-4c5d-aa3f-076c6b74997d_kakaotalk.png">
      <div class="login-type">카카오 계정으로 로그인</div>
    </div>
    <div class="gray-btn" @click="handleLoginClick">
      <img src="https://goruna.s3.us-west-1.amazonaws.com/755c83be-a394-4344-801c-6b3f8f118b0f_google.png">
      <div class="login-type">구글 계정으로 로그인</div>
    </div>
    <ModalNickname v-if="showNicknameModal" @nicknameAdded="showNicknameModal = false" />
  </div>
</template>

<style scoped>
.login-type {
  margin: auto;
}
.login-title {
  font-size: 25px;
  margin-bottom: 50px;
  font-weight: bold;
}
.content-box {
  border-radius: 43px;
  background: #FFF;
  box-shadow: 0px 4px 13px 0px rgba(0, 0, 0, 0.13) inset;
  padding: 100px 130px;
  margin: 100px 300px;
  align-items: center;
  display: flex;
  flex-direction: column;
}
.gray-btn {
  display: flex;
  background-color: var(--button-gray);
  border-radius: 52px;
  align-items: center;
  width: 40%;
  height: 44px;
  justify-content: flex-start;
  padding: 0px 20px;
  margin: 5px 0px;
}
</style>
