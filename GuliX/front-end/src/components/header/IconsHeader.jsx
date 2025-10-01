import React from 'react'
import FavoriteIcon from './icons/FavoriteIcon'
import CartIcon from './icons/CartIcon'
import SupportIcon from './icons/SupportIcon'

const IconsHeader = () => {
  return (
    <div className='col-start-4 row-start-2 flex gap-5'>
      <FavoriteIcon styles={"fill-[#132C55] hover:fill-[#4D92D1] transition-colors duration-300 cursor-pointer"}/>
      <CartIcon styles={"fill-[#132C55] hover:fill-[#4D92D1] transition-colors duration-300 cursor-pointer"}/>
      <SupportIcon styles={"fill-[#132C55] hover:fill-[#4D92D1] transition-colors duration-300 cursor-pointer"}/>
    </div>
  )
}

export default IconsHeader
