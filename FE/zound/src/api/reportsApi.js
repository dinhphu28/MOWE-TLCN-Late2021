import axiosClient from "./axiosClient";

const reportsApi = {
    getAll: (params) => {
        const url = '/reports';

        return axiosClient.get(url, {params});
    },

    getAllOfArticle: (articleId) => {
        const url = `/reports/${articleId}`

        return axiosClient.get(url);
    },

    post: (data) => {
        const url = '/reports';

        return axiosClient.post(url, data);
    },
    putSolvedUnsolved: (id, data) => {
        const url = `/reports/${id}`;

        return axiosClient.put(url, data);
    }
}

export default reportsApi;