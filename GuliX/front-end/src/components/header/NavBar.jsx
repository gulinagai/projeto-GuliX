import React from "react"
import { Link } from 'react-router-dom'

const NavBar = ()=> {
    return (
        <nav className="bg-[#132C55] h-9 grid text-center items-center whitespace-nowrap auto-cols-max gap-13 pl-13" style={{ fontFamily: 'Roboto' }}>
            <Link className="col-start-1" to="/">HOME</Link>
            <Link className="col-start-2" to="/produtos">HARDWARE</Link>
            <Link className="col-start-3" to="/">MONTE SEU PC</Link>
            <Link className="col-start-4" to="/">SOBRE A EMPRESA</Link>
            <Link className="col-start-5" to="/">SUPORTE / FAQ</Link>
        </nav>
    )
}

export default NavBar