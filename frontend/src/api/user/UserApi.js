import axios from 'axios';

export const getGoogleLoginUrl = async () => {
  try {
    const response = await axios.post('http://localhost:8100/api/v1/oauth2/google');
    return response.data;
  } catch (error) {
    console.error('구글 로그인 URL 요청 실패:', error);
    throw error;
  }
};

export const handleGoogleAuthResult = async (authCode) => {
  try {
    const response = await axios.get(`http://localhost:8100/api/v1/oauth2/google?code=${authCode}`);
    return response.data;
  } catch (error) {
    console.error('구글 로그인 처리 중 오류 발생:', error);
    throw error;
  }
};

// 닉네임 추가 API 호출 함수
export const addNickname = async (userSeq, nickname, token) => {
    try {
      const response = await axios.post(
        `/api/v1/user/${userSeq}/nickname`,
        { userNickname: nickname },
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );
      return response.data;
    } catch (error) {
      console.error('닉네임 추가 중 오류:', error);
      throw error;
    }
  };
