import React from 'react'
import SearchIcon from './icons/SearchIcon'

const SearchBar = () => {
  return (
    <div className='flex gap-3 items-center'>
      <input type="text" id="search" name="search" placeholder=" Procure o seu produto aqui!" className='bg-[#fafafa] placeholder-[#A0A0A0] w-[700px] h-8 text-[#000000] rounded-sm'/>
      <SearchIcon/>
    </div>
  )
}

export default SearchBar
