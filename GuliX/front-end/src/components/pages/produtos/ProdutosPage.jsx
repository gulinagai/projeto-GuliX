import React from 'react'
import Catalogo from './Catalogo'
import FiltroLateral from './FiltroLateral'
import FiltroSuperior from './FiltroSuperior'

const ProdutosPage = () => {

        const [data, setData] = React.useState([])
        const [page, setPage] = React.useState(1)

        React.useEffect(()=> {
            fetch(`http://localhost:3000/produtos?page=${page}&limit=10`)
            .then((res) => res.json())
            .then(data => {
                console.log(data)
                setData(data)
            })
        }, [page])
    return (<div className='bg-[#0d0d0d] h-[3000px]'>
    <div className='bg-amber-200 pt-34'>Você pesquisou por: x</div>
    <div className='grid grid-cols-[minmax(0px,50px)230px_1fr_minmax(0px,50px)] grid-rows-[160px_900px_1fr]'>
      <div className='grid-row-1 col-start-3 grid grid-rows-[25%_75%]'>
        <h2 className='text-[#FAFAFA]'>Você pesquisou por: x</h2>
      </div>
      <Catalogo data={data}/>
      <FiltroLateral />
      <FiltroSuperior />
    </div>
    </div>
  )
}

export default ProdutosPage
