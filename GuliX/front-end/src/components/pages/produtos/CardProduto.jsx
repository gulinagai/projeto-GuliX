import React from 'react'

const CardProduto = ({ data, index }) => {
  return (
   <div className="max-w-[250px] h-[500px] rounded-2xl shadow-md hover:shadow-xl transition-shadow duration-300 overflow-hidden grid grid-rows-[40%_60%] border-[1px] border-[#ffffff50] bg-[#0d0d0d]">
      <div className="h-48 flex items-center justify-center border-b-[1px] border-[#ffffff50]">
        <img
          src="https://via.placeholder.com/200"
          alt="Produto"
          className="h-40 object-contain"
        />
      </div>

      <div className="p-4 flex flex-col gap-3">
        <h2 className="text-lg font-semibold text-[#4983e0]">
          {`${data[index].nome}`}
        </h2>
        <div className='mt-auto'>
          <span className="text-xl font-bold text-[#2454a3]">{`R$ ${data[index].preco}`}</span>
          <button className=" bg-[#2454a3] hover:bg-[#4D92D1] text-white font-medium py-2 px-4 rounded-lg transition-colors h-[40px]  mt-3">
          Adicionar ao carrinho
        </button>
        </div>
        
      </div>
    </div>
  )
}

// #2454a3] hover:fill-[#4D92D1

export default CardProduto
