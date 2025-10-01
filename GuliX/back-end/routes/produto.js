// routes/produto.js
const express = require("express")
const router = express.Router() // Cria um "mini app" só para essas rotas desse arquivo
const connection = require("../db/connection")

router.get("/", (req, res) => {
    connection.query("SELECT * FROM produtos", (err, results)=> { // esse método de connection é responsável por fazer uma consulta SQL e retornar o resultado em results dentro de um objeto
        if(err) {  // lidando com possível erro ao consultar
            return res.status(500).send("Erro ao buscar produtos")
        }
        console.log(`Resultados: ${results}`)
        results.forEach((produto)=> {
            console.log(JSON.stringify(produto))
        })
        res.json(results)  // envia os resultados em JSON (converte o array de objetos results para json) para o cliente
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
})

module.exports = router // exporta o router pra usar no arquivo server.js