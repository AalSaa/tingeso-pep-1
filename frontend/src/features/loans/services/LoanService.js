import axios from "axios";

const API_URL = "http://localhost:8080/api/v1/loans";

export const getLoans = async () => {
    try {
        const response = await axios.get(API_URL);
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