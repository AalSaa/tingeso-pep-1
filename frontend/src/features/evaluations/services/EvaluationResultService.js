import axios from "axios";

const API_URL = "http://localhost:8090/api/v1/evaluation_results";

export const postEvaluationResult = async (loanId) => {
    try {
        const response = await axios.post(`${API_URL}/loan/${loanId}`);
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