<template>
  <div class="modal">
    <input v-model="nickname" placeholder="닉네임 입력" />
    <button @click="addNickname">닉네임 추가</button>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useUserStore } from '@/store/userStore';

const nickname = ref('');
const userStore = useUserStore();
const emit = defineEmits(['nicknameAdded']);



const addNickname = async () => {
  await userStore.addNickname(nickname.value);
  console.log("-------------------------------------");
  console.log("-------------------------------------");
  console.log("-------------------------------------");
  console.log("-------------------------------------");
  console.log("-------------------------------------");
  console.log("-------------------------------------");
  console.log("-------------------------------------");
  emit('nicknameAdded'); // 닉네임 추가 완료 이벤트
  window.location.href = '/'; // 메인 페이지로 리다이렉션
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  width: 500px;
  height: 600px;
  background-color: #fff;
  border-radius: 10px;
  overflow: hidden;
}

.modal-iframe {
  width: 100%;
  height: 100%;
}
</style>
