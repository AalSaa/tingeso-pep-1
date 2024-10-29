import axios from "axios";

const API_URL = "http://localhost:8080/api/v1/documents";

export const getDocumentsByLoanId = async (loanId) => {
    try {
        const response = await axios.get(`${API_URL}/loan/${loanId}`);
        return response.data;
    
    } catch (error) {
        console.error(error);
    }
}

export const downloadDocumentFile = async (id) => {
    try {
        const response = await axios.get(`${API_URL}/download/${id}`, {
            responseType: 'blob',
        });
  
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', response.headers['content-disposition'].split('filename=')[1].replace(/"/g, ''));
        document.body.appendChild(link);
        link.click();

        link.parentNode.removeChild(link);
    } catch (error) {
        console.error('Error descargando el archivo:', error);
    }
};
  

export const postDocument = async (loanId, file) => {
    const formData = new FormData();
    formData.append("loan_id", loanId);
    formData.append("file", file);

    try {
        const response = await axios.post(API_URL, formData, {
            headers: {
                "Content-Type": "multipart/form-data"
            }
        });
        return response.data;
    
    } catch (error) {
        console.error(error);
    }
}

export const deleteDocument = async (id) => {
    try {
        const response = await axios.delete(`${API_URL}/${id}`);
        return response.data;
    
    } catch (error) {
        console.error(error);
    }
}

