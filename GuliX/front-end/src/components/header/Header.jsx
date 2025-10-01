import React from 'react'
import NavBar from './NavBar'
import MyAccountHeader from './MyAccountHeader'
import SearchBar from './SearchBar'
import Logo from './Logo'
import IconsHeader from './IconsHeader'

const Header = () => {
  return (
    <header className='bg-[#121212] text-[#fafafa]  h-40 grid grid-rows-2 grid-cols-[1fr_760px_1fr_300px] fixed w-full shadow-md z-50'>
      <Logo/>
      <MyAccountHeader/>
      <div className='col-start-2 col-span-3 row-start-2 flex flex-col justify-between h-full '>
        <SearchBar/>
        <NavBar/>
      </div>
      <IconsHeader />
    </header>
  )
}

export default Header
