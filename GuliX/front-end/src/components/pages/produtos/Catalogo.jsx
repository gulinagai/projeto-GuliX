import React from 'react'
import CardProduto from './CardProduto'

const Catalogo = ({data}) => {
  console.log(data.length)
  
  const limite = 10
  return (
    <div className='col-start-3 row-start-2 grid grid-cols-[1fr_1fr_1fr_1fr_1fr] grid-rows-[1fr_1fr] gap-y-10'>
        
        {
            data && 
            data.map((card, index)=> {
              if(index + 1 <= limite) {
                return <CardProduto
                    data={data}
                    index={index}
                />
              } else null
            })
            
        }
        
    </div>
    
  )
}

export default Catalogo
