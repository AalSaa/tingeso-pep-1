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
        <form action="" className="bg-slate-50 space-y-8 w-96 rounded-2xl p-4">
            <div>
                <label htmlFor="">Archivo</label>
                <input 
                id="file"
                name="file"
                onChange={(e) => setFile(e.target.files[0])}
                type="file"
                className="bg-white border w-full rounded-lg p-2"
                />
            </div>
            <button onClick={submitDocumentForm} className="bg-lime-500 text-white w-full rounded-lg p-2">
                Subir archivo
            </button>
        </form>
    )
}