import axiosClient from "./axiosClient";

const articleApi = {
    getAll: (params) => {
        const url = '/articles';

        return axiosClient.get(url, {params});
    },

    post: (data) => {
        const url = '/articles';

        return axiosClient.post(url, data, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem("token")
            }
        })
    },
    
    put: (articleId, data) => {
        const url = `/articles/${articleId}`;

        return axiosClient.put(url, data, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem("token")
            }
        })
    },

    delete: (articleId) => {
        const url = `/articles/${articleId}`;

        return axiosClient.delete(url, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem("token")
            }
        })
    }
};

export default articleApi;