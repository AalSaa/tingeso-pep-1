import axios from "axios";

const API_URL = "http://localhost:8080/api/v1/users";

export const getUsers = async () => {
    try {
        const response = await axios.get(API_URL);
        return response.data;
    } catch (error) {
        console.error(error);
    }
}

export const getUser = async (id) => {
    try {
        const response = await axios.get(`${API_URL}/${id}`);
        return response.data;
    } catch (error) {
        console.error(error);
    }
}

export const postUser = async (user) => {
    try {
        const response = await axios.post(API_URL, user);
        return response.data;
    } catch (error) {
        if (error.response.data.message) {
            console.error(error.response.data.message);
        } else if (error.response) {
            console.error(error.response.data);
        } else {
            console.error(error);
        }
    }
}

export const putUser = async (id, user) => {
    try {
        const response = await axios.put(`${API_URL}/${id}`, user);
        return response.data;
    } catch (error) {
        if (error.response.data.message) {
            console.error(error.response.data.message);
        } else if (error.response) {
            console.error(error.response.data);
        } else {
            console.error(error);
        }
    }
}

export const deleteUser = async (id) => {
    try {
        const response = await axios.delete(`${API_URL}/${id}`);
        return response.data;
    } catch (error) {
        if (error.response.data.message) {
            console.error(error.response.data.message);
        } else {
            console.error(error);
        }
    }
}