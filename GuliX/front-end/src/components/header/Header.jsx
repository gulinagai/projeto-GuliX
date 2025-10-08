import React from 'react'
import NavBar from './NavBar'
import MyAccountHeader from './MyAccountHeader'
import SearchBar from './SearchBar'
import Logo from './Logo'
import IconsHeader from './IconsHeader'

const Header = () => {
  return (
    <header className='bg-[#0f172a] text-[#dddddd]  h-40 grid grid-rows-2 grid-cols-[1fr_760px_1fr_300px] fixed w-full shadow-md z-50' style={{
    boxShadow: "-5px 0 10px 5px  rgba(0, 0, 0, 0.801" }}>
      <Logo/>
      <div className='col-start-4 row-start-1 row-span-2 bg-[#0d0d0d] flex flex-col' style={{
    boxShadow: "-5px 0 5px 0 rgba(0, 0, 0, 0.801" }}>
        <MyAccountHeader textChange='hover:text-[#4D92D1] transition-colors duration-100 cursor-pointer'/>
        <IconsHeader />
      </div>
      <div className='col-start-2  row-start-2 flex flex-col justify-between h-full '>
        <SearchBar/>
        <NavBar/>
      </div>
    </header>
  )
}

export default Header


// <header> bg-[#07070a]

// 0f172a

//
