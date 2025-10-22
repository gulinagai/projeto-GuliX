import React from 'react';
import Home from './components/pages/home/Home'
import Header from './components/header/Header';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import ProdutosPage from './components/pages/produtos/ProdutosPage';
import ProdutoPage from './components/pages/produtos/ProdutoPage';

const App = ()=> {

  return (
    
      <div>
        <BrowserRouter>
          <Header />
          <Routes>
            <Route path='/' element={<Home />}></Route>
          </Routes>
          <Routes>
            <Route path='produtos/' element={<ProdutosPage />}></Route>
            <Route path="produtos/:produto" element={<ProdutoPage/>}/>
          </Routes>
        </BrowserRouter>
      </div>
    )
}

export default App
