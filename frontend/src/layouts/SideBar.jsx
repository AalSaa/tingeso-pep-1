export function SideBar({ showSideBar, handleShowSideBar }) {
    if (!showSideBar) {return null}

    return (
        <aside onClick={handleShowSideBar}
        className="bg-black bg-opacity-50 absolute w-full h-full">
            <section onClick={(e) => e.stopPropagation()}
            className="bg-cyan-800 text-white text-xl flex flex-col items-start gap-2 w-80 h-full p-4">
               <ul className="space-y-4">
                    <li>Inicio</li>
                    <li>Clientes</li>
                    <li>Simulación</li>
                    <li>Prestamo</li>
                    <li>Evaluación</li>
               </ul>
            </section>
        </aside>
    )
}