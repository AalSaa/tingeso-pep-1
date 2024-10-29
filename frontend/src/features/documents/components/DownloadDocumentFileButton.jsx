import { downloadDocumentFile } from "../services/DocumentService";

export function DownloadDocumentFileButton({ id }) {
    const handleClick = async (id) => {
        try {
            await downloadDocumentFile(id);
        } catch (error) {
            console.error(error);
        }
    }

    return (
        <button onClick={() => handleClick(id)}
        className="bg-lime-500 text-white flex justify-between flex-1 rounded-lg p-2">
            <p>Descargar</p>
        </button>
    )
}