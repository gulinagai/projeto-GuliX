import React from 'react'
import amdMarca from '../../../assets/imagens/marcas/amd-marca.jpg'
import intelMarca from '../../../assets/imagens/marcas/intel-marca.jpg'

const BrandsHome = () => {

    

  return (
    <div className='flex justify-center gap-20'>
      <img className=" cursor-pointer rounded-lg shadow-[0_8px_30px_rgba(0,0,0,0.7)] opacity-80 hover:opacity-100 transition duration-300" src={amdMarca} alt="" />
      <img className=" cursor-pointer rounded-lg shadow-[0_8px_30px_rgba(0,0,0,0.7)] opacity-80 hover:opacity-100 transition duration-300" src={intelMarca} alt="" />
    </div>
  )
}

export default BrandsHome
