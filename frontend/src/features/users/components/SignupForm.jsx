import { useState } from "react"
import { useLocation } from "wouter";
import { postUser, getUsers } from "../services/UserService";

export function SignupForm() {
    const [user, setUser] = useState({
        rut: "",
        first_name: "",
        last_name: "",
        birth_date: "",
        status: "In validation"
    })

    const [, setLocation] = useLocation();

    const handleChangeForm = (event) => {
        const { name, value } = event.target;
        setUser((prevUser) => ({
          ...prevUser,
          [name]: value,
        }));
      };

    const submitForm = async (event) => {
        event.preventDefault();
        try {
            await postUser(user);
            setLocation("/users");
        } catch (error) {
            console.error(error);
        }
    }

    return (
        <form>
            <div>
                <label htmlFor="first_name">Nombre</label>
                <input 
                id="first_name"
                name="first_name"
                value={user.first_name}
                onChange={handleChangeForm}
                type="text" 
                placeholder="Ingrese su nombre"
                />
            </div>
            <div>
                <label htmlFor="last_name">Apellido</label>
                <input 
                id="last_name"
                name="last_name"
                value={user.last_name}
                onChange={handleChangeForm}
                type="text" 
                placeholder="Ingrese su apellido"
                />
            </div>
            <div>
                <label htmlFor="rut">Rut</label>
                <input 
                id="rut"
                name="rut"
                value={user.rut}
                onChange={handleChangeForm}
                type="text" 
                placeholder="Ingrese su rut"
                />
            </div>
            <div>
                <label htmlFor="birth_date">Año de nacimiento</label>
                <input 
                id="birth_date"
                name="birth_date"
                value={user.birth_date}
                onChange={handleChangeForm}
                type="date" />
            </div>
            <button onClick={submitForm}>Registrar cliente</button>
        </form>
    )
}