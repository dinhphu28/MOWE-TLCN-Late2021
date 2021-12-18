import axiosClient from "./axiosClient";

const commentApi = {
    getAll: (articleId) => {
        const url = `/articles/${articleId}/comments`;

        return axiosClient.get(url);
    },

    post: (articleId, data) => {
        const url = `/articles/${articleId}/comments`;

        return axiosClient.post(url, data, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem("token")
            }
        });
    }
};

export default commentApi;