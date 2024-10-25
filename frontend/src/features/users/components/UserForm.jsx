export function SignupForm({ user, setUser, submitForm}) {
    const handleChangeForm = (event) => {
        const { name, value } = event.target;
        setUser((prevUser) => ({
          ...prevUser,
          [name]: value,
        }));
      };

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