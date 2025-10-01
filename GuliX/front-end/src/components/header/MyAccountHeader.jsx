import React from 'react'
import MyAccountIcon from './icons/MyAccountIcon'


const MyAccountHeader = () => {
  return (
    <div className='flex justify-end w-full  col-start-4 row-start-1 place-self-center pr-5'>
      <MyAccountIcon styles='fill-[#132C55] hover:fill-[#4D92D1] transition-colors duration-300 cursor-pointer'/>
      <p className='pt-5'>Olá, <strong>Entre</strong> ou <strong>Cadastre-se</strong></p>
    </div>
  )
}

export default MyAccountHeader
