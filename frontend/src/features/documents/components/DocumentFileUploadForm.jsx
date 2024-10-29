import { useState } from "react";

export function DocumentFileUploadForm({ loanId }) {
    const [file, setFile] = useState(null);

    const submitDocumentForm = async (event) => {
        event.preventDefault();
        if (!file) {
            console.error("No file selected");
            return;
        }
        try {
            await postDocument(loanId, file);
            fetchedDocuments(loanId);
            setFile(null);
            document.getElementById('file').value = ''
        } catch (error) {
            console.error(error);
        }
    }

    return (
        <form action="">
            <div>
                <label htmlFor="">Archivos</label>
                <input 
                id="file"
                name="file"
                onChange={(e) => setFile(e.target.files[0])}
                type="file"/>
            </div>
            <button onClick={submitDocumentForm}>Subir archivo</button>
        </form>
    )
}