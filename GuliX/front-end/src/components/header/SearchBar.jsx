import React from 'react'
import SearchIcon from './icons/SearchIcon'

const SearchBar = () => {
  return (
    <div className='flex gap-3 items-center'>
      <input type="text" id="search" name="search" placeholder=" Procure o seu produto aqui!" className='pl-4 focus:outline-none cursor-text bg-[#fafafa] placeholder-[#A0A0A0] w-[700px] h-8 text-[#000000] rounded-sm'/>
      <SearchIcon 
        styles={"fill-[#132C55] hover:fill-[#4D92D1] transition-colors duration-300 cursor-pointer"}/>
    </div>
  )
}

export default SearchBar
