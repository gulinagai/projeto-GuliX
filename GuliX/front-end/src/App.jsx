import React from 'react';
import Home from './components/home/Home'
import Header from './components/header/Header';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import ProdutosPage from './components/pages/ProdutosPage';

const App = ()=> {

  return (
    
      <div>
        <BrowserRouter>
          <Header />
          <Routes>
            <Route path='/' element={<Home />}></Route>
          </Routes>
          <Routes>
            <Route path='/produtos' element={<ProdutosPage />}></Route>
          </Routes>
        </BrowserRouter>
      </div>
    )
}

export default App
