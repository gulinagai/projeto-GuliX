// db/connection.js:

const mysql = require("mysql2/promise")    // o /promise fará com que o await do const [rows] = await connection.query("SELECT ...) funcione

// existe a opção de usar o mysql que retorna promise e a que retorna um objeto não promise. Se usar promise, tem que usar o await:



async function connectDB() {
    try {
        const connection = mysql.createPool({                  
        host: "localhost",
        user: "root",
        password:"Xperiasp575@",
        database: "gulix_bd",
        port: 2006
        })
        console.log('Conectado ao MySQL!!')
        return connection
    } catch (err) {
        console.error('Erro ao conectar ao banco:', err)
    }
}

// createPool é melhor que createConnection, Para um e-commerce com várias requisições, filtros, paginação, carrinho etc., sempre use createPool(). createConnection() só vale para projetos muito pequenos ou scripts de linha de comando.

// pool é um objeto.

// Esse objeto representa uma "piscina" de conexões" com o banco de dados.

// Ele gerencia múltiplas conexões automaticamente, então você não precisa abrir e fechar conexão manualmente toda vez que faz uma query.


module.exports = connectDB