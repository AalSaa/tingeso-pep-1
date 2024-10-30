import { useState, useEffect } from "react";
import { useLocation, useRoute } from "wouter";

import { downloadDocumentFile ,deleteDocument } from "../services/DocumentService";


export function DocumentsTable({documents, fetchedDocuments}) {
    const [match, params] = useRoute("/loan/:id/documents");
    const [, setLocation] = useLocation();

    

    const handleDownloadClick = async(documentId) => {
        try {
            await downloadDocumentFile(documentId);
        } catch (error) {
            console.error(error);
        }
    }

    const handleDeleteClick = async(documentId) => {
        try {
            await deleteDocument(documentId);
            fetchedDocuments();
        } catch (error) {
            console.error(error);
        }
    }

    const DocumentRow = ({document}) => {
        return (
            <tr className="odd:bg-white even:bg-slate-50">
                <td className="text-start p-4">{document.file_name}</td>
                <td className="flex items-center p-2 space-x-2">
                    <button onClick={(e) => handleDownloadClick(document.id)}
                        className="bg-cyan-500 text-white flex justify-between flex-1 rounded-lg p-2">
                            <p>Descargar</p>
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="size-6">
                            <path fillRule="evenodd" d="M12 2.25a.75.75 0 0 1 .75.75v11.69l3.22-3.22a.75.75 0 1 1 1.06 1.06l-4.5 4.5a.75.75 0 0 1-1.06 0l-4.5-4.5a.75.75 0 1 1 1.06-1.06l3.22 3.22V3a.75.75 0 0 1 .75-.75Zm-9 13.5a.75.75 0 0 1 .75.75v2.25a1.5 1.5 0 0 0 1.5 1.5h13.5a1.5 1.5 0 0 0 1.5-1.5V16.5a.75.75 0 0 1 1.5 0v2.25a3 3 0 0 1-3 3H5.25a3 3 0 0 1-3-3V16.5a.75.75 0 0 1 .75-.75Z" clipRule="evenodd" />
                            </svg>
                        </button>
                        <button onClick={(e) => handleDeleteClick(document.id)}
                        className="bg-red-500 text-white flex justify-between flex-1 rounded-lg p-2">
                            <p>Borrar</p>
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="size-6">
                            <path fillRule="evenodd" d="M16.5 4.478v.227a48.816 48.816 0 0 1 3.878.512.75.75 0 1 1-.256 1.478l-.209-.035-1.005 13.07a3 3 0 0 1-2.991 2.77H8.084a3 3 0 0 1-2.991-2.77L4.087 6.66l-.209.035a.75.75 0 0 1-.256-1.478A48.567 48.567 0 0 1 7.5 4.705v-.227c0-1.564 1.213-2.9 2.816-2.951a52.662 52.662 0 0 1 3.369 0c1.603.051 2.815 1.387 2.815 2.951Zm-6.136-1.452a51.196 51.196 0 0 1 3.273 0C14.39 3.05 15 3.684 15 4.478v.113a49.488 49.488 0 0 0-6 0v-.113c0-.794.609-1.428 1.364-1.452Zm-.355 5.945a.75.75 0 1 0-1.5.058l.347 9a.75.75 0 1 0 1.499-.058l-.346-9Zm5.48.058a.75.75 0 1 0-1.498-.058l-.347 9a.75.75 0 0 0 1.5.058l.345-9Z" clipRule="evenodd" />
                            </svg>
                        </button>
                </td>
            </tr>
        )
    }

    return (
        <div>
            <table className="w-full">
                <thead>
                    <tr className="bg-slate-50">
                        <th className="text-start p-4">Nombre de archivo</th>
                        <th className="text-start w-80 p-4">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {documents?.map((document) => (
                        <DocumentRow document={document} key={document.id}/>
                    ))}
                </tbody>
            </table>
        </div>
    )
}