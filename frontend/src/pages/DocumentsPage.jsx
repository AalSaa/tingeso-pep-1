import { useState, useEffect } from "react";
import { useLocation, useRoute } from "wouter";

import { DocumentsTable } from "../features/documents/components/DocumentsTable";
import { DocumentFileUploadForm } from "../features/documents/components/DocumentFileUploadForm";
import { getDocumentsByLoanId } from "../features/documents/services/DocumentService";

export function DocumentsPage() {
    const [match, params] = useRoute("/loan/:id/documents");
    const [, setLocation] = useLocation();

    const [documents, setDocuments] = useState([]);

    const fetchedDocuments = async () => {
        try {
            if (params.id) {
                const fetchedDocuments = await getDocumentsByLoanId(params.id);
                setDocuments(fetchedDocuments);
                console.log(fetchedDocuments);
            }
        } catch (error) {
            console.error(error);
        }
    }

    useEffect(() => {
        fetchedDocuments();
    }, [match, params.id])

    const handleClick = () => {
        setLocation('/loans');
    }

    return (
        <div className="space-y-4">
            <div className="flex items-center">
                <h1 className="text-4xl p-4">Documentos del préstamo</h1>
                <button onClick={handleClick}
                className="bg-cyan-500 text-white flex rounded-lg gap-4 p-2">
                    <p>Ir a préstamos</p>
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="size-6">
                    <path fillRule="evenodd" d="M5.25 6.31v9.44a.75.75 0 0 1-1.5 0V4.5a.75.75 0 0 1 .75-.75h11.25a.75.75 0 0 1 0 1.5H6.31l13.72 13.72a.75.75 0 1 1-1.06 1.06L5.25 6.31Z" clipRule="evenodd" />
                    </svg>
                </button>
            </div>
            <DocumentFileUploadForm loanId={params.id} fetchedDocuments={fetchedDocuments} />
            <DocumentsTable documents={documents} fetchedDocuments={fetchedDocuments}/>
        </div>
    )
}