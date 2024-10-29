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
        <form action="">
            <div>
                <label htmlFor="">Rut del solicitante</label>
                <input 
                id="rut"
                name="rut"
                value={user.rut}
                onChange={(e) => setUser({ ...user, rut: e.target.value })}
                placeholder="Ingrese el rut del solicitante"
                type="text" 
                />
            </div>
            <button onClick={submitUserForm}>Buscar usuario</button>
        </form>
    )
}