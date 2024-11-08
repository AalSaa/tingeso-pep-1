import { useState, useEffect } from "react";
import { useLocation } from "wouter";
import { getUsers, putUser, deleteUser } from "../services/UserService";

export function UsersTable() {
    const [users, setUsers] = useState([]);

    const [, setLocation] = useLocation();

    const fetchUsers = async () => {
        try {
            const fetchedUsers = await getUsers();
            setUsers(fetchedUsers);
        } catch (error) {
            console.error(error);
        }
    }

    useEffect(() => {
        fetchUsers();
    },[])

    const handleValidateClick = async (user) => {
        try {
            user.status = 'Active';
            await putUser(user.id, user);
            fetchUsers();
        } catch (error) {
            console.error(error);   
        }
    }

    const handleEditClick = (id) => {
        setLocation(`/edituser/${id}`);
    }

    const handleDeleteClick = async (id) => {
        try {
            await deleteUser(id);
            fetchUsers();
        } catch (error) {
            console.error(error);
        }
    }


    const UserRow = ({ user }) => {
        return (
            <tr className="odd:bg-white even:bg-slate-50">
                <td className="text-start p-4">{user.first_name}</td>
                <td className="text-start p-4">{user.last_name}</td>
                <td className="text-start p-4">{user.rut}</td>
                <td className="text-start p-4">{user.status}</td>
                <td className="flex items-center p-2 space-x-2">
                    {(user.status == 'In validation') ? (
                        <button onClick={() => handleValidateClick(user)}
                        className="bg-lime-500 text-white flex justify-between flex-1 rounded-lg p-2">
                            <p>Validar</p>
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="size-6">
                            <path fillRule="evenodd" d="M2.25 12c0-5.385 4.365-9.75 9.75-9.75s9.75 4.365 9.75 9.75-4.365 9.75-9.75 9.75S2.25 17.385 2.25 12Zm13.36-1.814a.75.75 0 1 0-1.22-.872l-3.236 4.53L9.53 12.22a.75.75 0 0 0-1.06 1.06l2.25 2.25a.75.75 0 0 0 1.14-.094l3.75-5.25Z" clipRule="evenodd" />
                            </svg>
                        </button>
                    ) : null}
                    <button onClick={() => handleEditClick(user.id)}
                    className="bg-yellow-500 text-white flex justify-between flex-1 rounded-lg p-2">
                        <p>Editar</p>
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="size-6">
                        <path d="M21.731 2.269a2.625 2.625 0 0 0-3.712 0l-1.157 1.157 3.712 3.712 1.157-1.157a2.625 2.625 0 0 0 0-3.712ZM19.513 8.199l-3.712-3.712-12.15 12.15a5.25 5.25 0 0 0-1.32 2.214l-.8 2.685a.75.75 0 0 0 .933.933l2.685-.8a5.25 5.25 0 0 0 2.214-1.32L19.513 8.2Z" />
                        </svg>  
                    </button>
                    <button onClick={() => handleDeleteClick(user.id)}
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
        <div className="overflow-y-auto">
            <table className="w-full">
                <thead>
                    <tr className="bg-slate-50">
                        <th className="text-start p-4">Nombre</th>
                        <th className="text-start p-4">Apellido</th>
                        <th className="text-start p-4">Rut</th>
                        <th className="text-start p-4">Estado</th>
                        <th className="text-start w-80 p-4">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {users?.map((user, index) => (
                        <UserRow key={index} user={user} />
                    ))}
                </tbody>
            </table>
        </div>
    )
}