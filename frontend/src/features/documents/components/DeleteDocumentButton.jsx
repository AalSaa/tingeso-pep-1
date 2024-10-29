import { deleteDocument } from "../services/DocumentService";

export function DeleteDocumentButton({ id }) {
    const handleClick = async (id) => {
        try {
            await deleteDocument(id);
        } catch (error) {
            console.error(error);
        }
    }

    return (
        <button onClick={() => handleClick(id)}
            className="bg-red-500 text-white flex justify-between flex-1 rounded-lg p-2">
            <p>Eliminar</p>
        </button>
    )
}