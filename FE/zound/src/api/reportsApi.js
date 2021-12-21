import axiosClient from "./axiosClient";

const reportsApi = {
    getAll: () => {
        const url = '/reports';

        return axiosClient.get(url);
    },

    getAllOfArticle: (articleId) => {
        const url = `/reports/${articleId}`

        return axiosClient.get(url);
    },

    post: (data) => {
        const url = '/reports';

        return axiosClient.post(url, data);
    }
}

export default reportsApi;