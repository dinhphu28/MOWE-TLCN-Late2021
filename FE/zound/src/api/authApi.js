import axiosClient from "./axiosClient";

const authApi = {
    put: (data) => {
        const url = '/auth';

        return axiosClient.put(url, data);
    },

    post: (data) => {
        const url = '/auth';

        return axiosClient.post(url, data);
    },

    patch: (username, data) => {
        const url = `/auth/${username}`;

        return axiosClient.patch(url, data);
    }
}

export default authApi;