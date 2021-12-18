import axiosClient from "./axiosClient";

const usersRoleApi = {
    getAll: (params) => {
        const url = '/users';

        return axiosClient.get(url, {
            params,
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem("token")
            }
        })
    },

    putForceChangePassword: (data) => {
        const url = '/users/password';

        return axiosClient.put(url, data, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem("token")
            }
        });
    },

    putChangeActiveState: (data) => {
        const url = '/users/active-state';

        return axiosClient.put(url, data, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem("token")
            }
        });
    },

    post: (data) => {
        const url = '/users';

        return axiosClient.post(url, data, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem("token")
            }
        });
    }
}

export default usersRoleApi;