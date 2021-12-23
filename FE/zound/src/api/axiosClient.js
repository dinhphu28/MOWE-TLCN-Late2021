import axios from "axios";
import queryString from 'query-string';
import { BASE_URL_API_BE } from "../constants/global";

// Setup default config for http requests here
// Please have a look at here `https://github.com/axios/axios#request-config` for the full list of configs
const axiosClient = axios.create({
    // baseURL: process.env.REACT_APP_BE_API_V1_URL,
    baseURL: BASE_URL_API_BE,
    headers: {
        'content-type': 'application/json'
    },
    paramsSerializer: params => queryString.stringify(params)
});

axiosClient.interceptors.request.use(async (config) => {
    // Handle token here ...
    return config;
});

axiosClient.interceptors.response.use((response) => {
    if(response && response.data) {
        return response.data;
    }

    return response;
}, (error) => {
    // Handle errors
    throw error;
});

export default axiosClient;