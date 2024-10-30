import { getUserByRut } from "../services/UserService";

export function UserSearchForm({ user, setUser }) {
    const submitUserForm = async (event) => {
        event.preventDefault();
        try {
            const fetchedUser = await getUserByRut(user.rut);
            if (fetchedUser) {
                setUser(fetchedUser);
                console.log(fetchedUser);
            }
        } catch (error) {
            console.error(error);
        }
    }

    return (
        <form action="" className="bg-slate-50 space-y-4 w-96 rounded-2xl p-4">
            <div>
                <label htmlFor="" className="block ml-2">Rut del solicitante</label>
                <input 
                id="rut"
                name="rut"
                value={user.rut}
                onChange={(e) => setUser({ ...user, rut: e.target.value })}
                placeholder="Ingrese el rut del solicitante"
                type="text" 
                className="border w-full rounded-lg p-2"
                />
            </div>
            <button onClick={submitUserForm}
            className="bg-cyan-500 text-white w-full rounded-lg p-2">
                Buscar usuario
            </button>
        </form>
    )
}