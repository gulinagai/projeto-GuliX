// routes/produto.js
const express = require("express")
const router = express.Router() // Cria um "mini app" só para essas rotas desse arquivo
const connectDB = require("../db/connection")

router.get("/", async (req, res) => {
    const page = parseInt(req.query.page)
    const limit = parseInt(req.query.limit)
    const offset = (page - 1) * limit

    // forma moderna e atual de se conectar e trocar informações com o banco:
    const connection = await connectDB()
    const [rows] = await connection.query("SELECT * FROM produtos LIMIT ? OFFSET ?", [limit, offset]) // esse método de connection é responsável por fazer uma consulta SQL e retornar o resultado em results dentro de um objeto
    
    // (O OFFSET diz quantos registros o banco deve pular antes de começar a retornar os resultados, e o LIMIT define quantos registros devem ser apresentados a partir desse ponto.)

    // o métódo connection.query retorna uma Promise, como eu to usando o mysql2 com promise (mysql2/promise), esse método vai retornar uma promise, mas se fosse o mysql2 normal, sem promise (importando sem promise), não se usaria await, e se usaria a forma antiga, que se usava callback, descrita abaixo:
        
        // forma antiga:
        // connection.query("SELECT * FROM produtos, (err, results)=> {
        // if(err) {  // lidando com possível erro ao consultar
        //     return res.status(500).send("Erro ao buscar produtos")
        // }  
        // })
       
       
         
        console.log(rows)
        res.json(rows)  // envia os resultados em JSON (converte o array de objetos results para json) para o cliente
        
        // res.json() faz duas coisas:
        // 1. transforma o objeto results que veio do bd para json
        // 2. envia esses dados para o front

                    //         - 'results' é o array de objetos retornado pelo MySQL (cada objeto representa uma linha da tabela 'produtos').
                    //   - Express automaticamente converte o objeto JavaScript em JSON e define o cabeçalho HTTP 
                    //     'Content-Type: application/json', indicando que o conteúdo é JSON.
                    //   - Se você acessar a rota direto pelo navegador (ex: http://localhost:3000/produtos), o navegador
                    //     simplesmente mostra o JSON como texto na tela.
                    //   - Se a requisição vier de um front-end (React, fetch, Axios, etc), o JSON não aparece automaticamente
                    //     na tela; ele fica disponível na resposta da requisição e você decide como renderizar no HTML.
                    //   - É diferente de res.send(), que pode enviar texto, HTML, Buffer ou JSON, mas não garante que
                    //     o Content-Type seja application/json.
                    //   - res.json() é a forma padrão de enviar dados de API no formato correto e seguro.

    
})

module.exports = router // exporta o router pra usar no arquivo server.js