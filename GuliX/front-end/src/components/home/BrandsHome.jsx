import React from 'react'
import amdMarca from '../../assets/imagens/marcas/amd-marca.jpg'
import intelMarca from '../../assets/imagens/marcas/intel-marca.jpg'

const BrandsHome = () => {

    

  return (
    <div className='flex justify-center gap-20'>
      <img className="rounded-lg shadow-[0_8px_30px_rgba(0,0,0,0.7)] hover:opacity-80 transition duration-300" src={amdMarca} alt="" />
      <img className="rounded-lg shadow-[0_8px_30px_rgba(0,0,0,0.7)] hover:opacity-80 transition duration-300" src={intelMarca} alt="" />
    </div>
  )
}

export default BrandsHome
