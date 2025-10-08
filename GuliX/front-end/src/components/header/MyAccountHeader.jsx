import React from 'react'
import MyAccountIcon from './icons/MyAccountIcon'


const MyAccountHeader = ({ textChange }) => {
  return (
    <div className='flex justify-end w-full place-self-center pr-5 bg-[#0d0d0d] flex-1 items-center'>
      <MyAccountIcon styles='fill-[#2454a3] hover:fill-[#4D92D1] transition-colors duration-300 cursor-pointer'/>
      <p>Olá, <strong className={textChange}>Entre</strong> ou <strong className={textChange}>Cadastre-se</strong></p>
    </div>
  )
}

export default MyAccountHeader
