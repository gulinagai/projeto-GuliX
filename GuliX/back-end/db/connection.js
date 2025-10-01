// db/connection.js:

const mysql = require("mysql2")

const connection = mysql.createConnection({
    host: "localhost",
    user: "root",
    password:"Xperiasp575@",
    database: "gulix_bd",
    port: 2006
})

connection.connect((err)=> {
    if(err) {
        console.log("Erro ao conectar ao banco", err)
        return
    }
    console.log("Conectado ao MySQL") 
})

module.exports = connection