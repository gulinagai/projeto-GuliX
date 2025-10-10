const express = require("express")
const cors = require("cors")
const app = express()
const path = require('path')

const PORT  = 3000;

app.use(cors()) // 
// CORS = Cross-Origin Resouce Sharing, é um mecanismo de segurança dos navegadores. Ele define quem pode acessar a API, no caso a minha API (http://localhost:3000)

// nesse caso, estou liberando o acesso à api que está na porta 3000, por qualquer outra porta/origem, caso eu queira definir uma porta específica que terá acesso, posso colocar:

 // app.use(cors({
//   origin: "http://localhost:5173"
// }))

// isso restringe o acesso a apenas a essa origem/porta

// como estou fazendo o fetch pelo front que está vite, rodando em uma outra porta (5173), o navegador automaticamente bloqueia o acesso a API.

app.use('/uploads', express.static(path.join(__dirname, 'uploads')))

app.get('/', (req, res)=> {
    res.send('Servidor Node Express rodando :)') // envia a resposta http ao cliente, contém Status (por padrão 200 OK, a não ser que você troque com res.status(404) etc,Headers (por exemplo, Content-Type: application/json quando usa res.json) e Body (o conteúdo em si — JSON, texto, HTML, etc).
    

})

// importando o router:
const produtoRoutes = require("./routes/produto")
app.use("/produtos", produtoRoutes) 

// O app.use pode ser usado para configurar middleware ou para configurar route.
// Nesse caso foi para configurar um route, o primeiro argumento é a rota raíz, ou seja localhost:3000/produtos

// Routes simplesmente servem para organizar as rotas de uma forma melhor, separando em arquivos, para não precisar deixar tudo em um mesmo arquivo.





app.listen(PORT, ()=> {
    console.log(`Servidor rodando em localhost:${PORT}`)
})

