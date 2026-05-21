# Regras de Negócio e Requisitos - GuliX Backend

Este documento descreve os principais requisitos funcionais e regras de negócio implementados na API backend do projeto **GuliX**, um e-commerce de hardware desenvolvido com **Java + Spring Boot**.

---

## Objetivo do Projeto

O GuliX foi desenvolvido com o objetivo de simular um sistema de e-commerce de hardware, permitindo gerenciamento de produtos, carrinho, pedidos, pagamentos e autenticação de usuários.

O foco principal do projeto está em:

- Arquitetura backend
- Segurança e autenticação
- Persistência de dados
- Regras de negócio
- Modelagem relacional

---

## Perfis do Sistema

### Cliente (`USER`)
Responsável por:

- Criar conta
- Realizar login
- Consultar produtos
- Adicionar itens ao carrinho
- Criar pedidos
- Simular pagamentos
- Consultar histórico de pedidos

### Administrador (`ADMIN`)
Responsável por:

- Gerenciar produtos
- Gerenciar categorias
- Gerenciar marcas
- Controlar estoque
- Gerenciar pedidos

---

## Requisitos Funcionais

O sistema permite:

### Autenticação e Usuários
- Cadastro de usuários
- Login autenticado via JWT
- Controle de permissões baseado em roles
- Proteção de endpoints privados

### Produtos
- Cadastro de produtos
- Consulta de produtos
- Atualização de produtos
- Exclusão de produtos
- Organização por categorias
- Associação de marcas
- Produtos com desconto
- Produtos em destaque

### Carrinho
- Adicionar produtos ao carrinho
- Remover produtos do carrinho
- Atualizar quantidade de itens
- Consultar carrinho do usuário

### Pedidos
- Criar pedidos
- Consultar histórico de pedidos
- Controle de status do pedido
- Persistência do endereço de entrega

### Pagamentos
- Simulação de pagamento
- Controle de parcelas
- Aplicação de desconto
- Controle de status do pagamento

### Estoque
- Controle automático de estoque
- Impedimento de venda de produtos indisponíveis

---

## Regras de Negócio

### Usuários
- Apenas usuários autenticados podem acessar recursos protegidos.
- O sistema diferencia permissões entre usuários comuns (`USER`) e administradores (`ADMIN`).

### Produtos
- Produtos podem possuir desconto.
- Produtos podem ser marcados como destaque.
- Produtos sem estoque não podem ser vendidos.

### Carrinho
- Um usuário possui apenas um carrinho ativo.
- O carrinho pode conter múltiplos produtos.

### Pedidos
- Um pedido deve possuir pelo menos um item.
- O endereço de entrega é persistido no pedido para manter histórico da compra.
- Um usuário pode possuir múltiplos pedidos.

### Pagamentos
- Cada pedido possui um pagamento associado.
- O pagamento possui status de acompanhamento.

### Estoque
- O estoque é atualizado automaticamente após a confirmação de um pedido.
- Produtos indisponíveis não podem ser adicionados ao fluxo de compra.

---

## Modelagem do Banco de Dados

A modelagem do banco foi desenvolvida considerando regras de negócio comuns em sistemas de e-commerce.

O sistema contempla:

- Usuários
- Endereços
- Produtos
- Categorias
- Marcas
- Carrinho
- Pedidos
- Pagamentos

O DER do projeto pode ser consultado no README principal do repositório.
