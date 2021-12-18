import axiosClient from "./axiosClient";

const exampleApi = {
    getAll: (params) => {
        const url = '/products';
        
        // return axiosClient.get(url, {params});

        // if you want add another headers here: (overwrite default method)
        // or you can add baseURL, ... here to overwrite default method
        return axiosClient.get(url, {
            params,
            headers: {
                'testing': 'test1'
            }
        });
    },

    get: (id) => {
        const url = `/products/${id}`;

        return axiosClient.get(url);
    }
}

export default exampleApi;