import { defineStore } from 'pinia';
import axios from 'axios';

export const useReviewStore = defineStore('reviewStore', {
    state: () => ({
        reviews: [],
    }),
    actions: {
        async fetchReviews(shopSeq) {
        try {
            const response = await axios.get(`/api/v1/shop/${shopSeq}/review`);
            this.reviews = response.data.data;
        } catch (error) {
            console.error('리뷰 데이터를 가져오는 중 오류가 발생했습니다:', error);
        }
        },
    },
});
