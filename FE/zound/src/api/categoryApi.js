import axiosClient from "./axiosClient";

const categoryApi = {
    getAll: () => {
        const url = '/categories';

        return axiosClient.get(url);
    },

    post: (data) => {
        const url = '/categories';

        return axiosClient.post(url, data);
    }
};

export default categoryApi;