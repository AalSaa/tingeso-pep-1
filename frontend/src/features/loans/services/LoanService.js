import axios from "axios";

const API_URL = "http://191.233.252.88:8090/api/v1/loans";

export const getLoans = async () => {
    try {
        const response = await axios.get(API_URL);
        return response.data;
    } catch (error) {
        console.error(error);
    }
}

export const getLoansByStatus = async (status) => {
    try {
        const response = await axios.get(`${API_URL}/status/${status}`);
        return response.data;
    } catch (error) {
        console.error(error);
    }
}

export const getLoansByStatusNot = async (status) => {
    try {
        const response = await axios.get(`${API_URL}/status/not/${status}`);
        return response.data;
    } catch (error) {
        console.error(error);
    }
}

export const getLoansByUserIdAndStatusNot = async (userId, status) => {
    try {
        const response = await axios.get(`${API_URL}/status/not/${status}/user/${userId}`);
        return response.data;
    } catch (error) {
        console.error(error);
    }
}

export const getLoanById = async (id) => {
    try {
        const response = await axios.get(`${API_URL}/${id}`);
        return response.data;
    } catch (error) {
        console.error(error);
    }
}

export const postLoan = async (loan) => {
    try {
        const response = await axios.post(API_URL, loan);
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

export const putLoan = async (id, loan) => {
    try {
        const response = await axios.put(`${API_URL}/${id}`, loan);
        return response.data;
    } catch (error) {
        console.error(error);
    }
}