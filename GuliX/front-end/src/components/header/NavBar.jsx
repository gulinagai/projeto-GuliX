import React from "react"
import { NavLink } from 'react-router-dom'

const NavBar = ()=> {
    return (
            <nav className="bg-[#0f172a] h-9 grid text-center items-center whitespace-nowrap auto-cols-max gap-13 pl-13" style={{ fontFamily: 'Roboto' }}>
                <NavLink className="col-start-1" to="/">HOME</NavLink>
                <NavLink className="col-start-2" to="/produtos">HARDWARE</NavLink>
                <NavLink className="col-start-3" to="/monte-seu-pc">MONTE SEU PC</NavLink>
                <NavLink className="col-start-4" to="/sobre">SOBRE A EMPRESA</NavLink>
                <NavLink className="col-start-5" to="/suporte">SUPORTE / FAQ</NavLink>
            </nav>
    )
}

export default NavBar

// nav bg #0f172a atual

// nav bg-[#132C55]