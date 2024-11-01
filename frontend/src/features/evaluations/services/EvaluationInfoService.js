import axios from "axios";

const API_URL = "http://localhost:8080/api/v1/evaluation_infos";

export const getEvaluationInfoByLoanId = async (loanId) => {
    try {
        const response = await axios.get(`${API_URL}/loan/${loanId}`);
        return response.data;
    } catch (error) {
        if (error.response.data.message) {
            console.error(error.response.data.message);
        } else {
            console.error(error);
        }
    }
}

export const postEvaluationInfo = async (evaluation) => {
    try {
        const response = await axios.post(API_URL, evaluation);
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

export const putEvaluationInfo = async (id, evaluation) => {  
    try {
        const response = await axios.put(`${API_URL}/${id}`, evaluation);
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