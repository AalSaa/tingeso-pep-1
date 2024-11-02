import { Link } from "wouter"

export function SideBar({ showSideBar, handleShowSideBar }) {
    if (!showSideBar) {return null}

    return (
        <aside onClick={handleShowSideBar}
        className="bg-black bg-opacity-50 fixed z-50 w-full h-full">
            <section onClick={(e) => e.stopPropagation()}
            className="bg-cyan-800 text-white text-xl flex flex-col items-start gap-4 w-80 h-full p-4">
               <Link href="/">Inicio</Link>
               <Link href="/users">Clientes</Link>
               <Link href="/simulations">Simulación</Link>
               <Link href="/loans">Préstamo</Link>
            </section>
        </aside>
    )
}