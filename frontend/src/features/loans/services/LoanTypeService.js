import axios from "axios";

const API_URL = "http://191.233.252.88:8090/api/v1/loan_types";

export const getLoanTypes = async () => {
    try {
        const response = await axios.get(API_URL);
        return response.data;
    } catch (error) {
        console.error(error);
    }
}