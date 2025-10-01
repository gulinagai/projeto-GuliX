import React from 'react'

const ProdutosPage = () => {
        React.useEffect(()=> {
            fetch('http://localhost:3000/produtos')
            .then((res) => res.json())
            .then(data => {
                console.log(data)
            })
        } )
    return (
    <div>
      
    </div>
  )
}

export default ProdutosPage
