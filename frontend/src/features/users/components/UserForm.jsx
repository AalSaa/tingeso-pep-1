import { useLocation } from 'wouter';

export function SignupForm({ user, setUser, submitForm, isEdit }) {
    const [, setLocation] = useLocation();

    const handleClick = () => {
        setLocation('/users');
    }

    return (
        <form onSubmit={submitForm} className="bg-slate-50 space-y-8 w-96 rounded-2xl p-4">
            <div>
                <button type="button" onClick={handleClick}
                className="absolute">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="currentColor" className="size-6">
                    <path fillRule="evenodd" d="M9.53 2.47a.75.75 0 0 1 0 1.06L4.81 8.25H15a6.75 6.75 0 0 1 0 13.5h-3a.75.75 0 0 1 0-1.5h3a5.25 5.25 0 1 0 0-10.5H4.81l4.72 4.72a.75.75 0 1 1-1.06 1.06l-6-6a.75.75 0 0 1 0-1.06l6-6a.75.75 0 0 1 1.06 0Z" clipRule="evenodd" />
                    </svg>
                </button>
                <h2 className="text-2xl text-center">
                    {isEdit? "Editar cliente": "Registrar cliente"}
                </h2>
            </div>
            <div className="space-y-4">
                <div>
                    <label htmlFor="first_name" className="block ml-2">Nombre</label>
                    <input 
                    id="first_name"
                    name="first_name"
                    value={user.first_name}
                    onChange={(e) => setUser({ ...user, first_name: e.target.value })}
                    type="text" 
                    placeholder="Ingrese su nombre"
                    className="border w-full rounded-lg p-2"
                    />
                </div>
                <div>
                    <label htmlFor="last_name" className="block ml-2">Apellido</label>
                    <input 
                    id="last_name"
                    name="last_name"
                    value={user.last_name}
                    onChange={(e) => setUser({ ...user, last_name: e.target.value })}
                    type="text" 
                    placeholder="Ingrese su apellido"
                    className="border w-full rounded-lg p-2"
                    />
                </div>
                <div>
                    <label htmlFor="rut" className="block ml-2">
                        Rut (con guión y sin puntos)
                    </label>
                    <input 
                    id="rut"
                    name="rut"
                    value={user.rut}
                    onChange={(e) => setUser({ ...user, rut: e.target.value })}
                    type="text" 
                    placeholder="Ingrese su rut"
                    className="border w-full rounded-lg p-2"
                    />
                </div>
                <div>
                    <label htmlFor="birth_date" className="block ml-2">Año de nacimiento</label>
                    <input 
                    id="birth_date"
                    name="birth_date"
                    value={user.birth_date}
                    onChange={(e) => setUser({ ...user, birth_date: e.target.value })}
                    type="date" 
                    className="border w-full rounded-lg p-2"
                    />
                </div>
            </div>
            <button type='submit'
            className="bg-lime-500 text-white w-full rounded-lg p-2">
                {isEdit? "Editar cliente": "Registrar cliente"}
            </button>
        </form>
    )
}