import { useState } from "react";

import { postDocument } from "../services/DocumentService";

export function DocumentFileUploadForm({ loanId, fetchedDocuments }) {
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
        <form action="" className="flex">
            <div>
                <input 
                id="file"
                name="file"
                onChange={(e) => setFile(e.target.files[0])}
                type="file"
                className="bg-white border w-full rounded-l-lg p-2"
                />
            </div>
            <button onClick={submitDocumentForm} className="bg-lime-500 text-white flex items-center space-x-4 rounded-r-lg p-2">
                <p>Subir archivo</p>
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="size-6">
                <path fillRule="evenodd" d="M11.47 2.47a.75.75 0 0 1 1.06 0l4.5 4.5a.75.75 0 0 1-1.06 1.06l-3.22-3.22V16.5a.75.75 0 0 1-1.5 0V4.81L8.03 8.03a.75.75 0 0 1-1.06-1.06l4.5-4.5ZM3 15.75a.75.75 0 0 1 .75.75v2.25a1.5 1.5 0 0 0 1.5 1.5h13.5a1.5 1.5 0 0 0 1.5-1.5V16.5a.75.75 0 0 1 1.5 0v2.25a3 3 0 0 1-3 3H5.25a3 3 0 0 1-3-3V16.5a.75.75 0 0 1 .75-.75Z" clipRule="evenodd" />
                </svg>
            </button>
        </form>
    )
}