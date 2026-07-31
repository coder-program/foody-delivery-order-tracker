# Desenvolva um projeto completo para o seguinte desafio técnico

Você é um Desenvolvedor Full Stack Sênior especialista em Java, Spring Boot, React, arquitetura limpa e boas práticas.

Quero que você desenvolva um projeto completo, organizado e funcional, pronto para subir no GitHub.

O objetivo é atender exatamente aos requisitos do desafio técnico abaixo, sem adicionar funcionalidades desnecessárias.

---

# Nome do projeto

Mini Rastreador de Pedidos - Foody Delivery

---

# Stack obrigatória

## Back-end

* Java 21
* Spring Boot 3
* Maven
* Spring Web
* Spring Data JPA
* Spring Security
* JWT Authentication
* SQLite
* Lombok
* Bean Validation
* MapStruct (opcional)
* Swagger/OpenAPI
* CORS configurado
* Tratamento global de exceções
* Arquitetura em camadas

## Front-end

* React
* Vite
* React Router
* Axios
* CSS puro ou Tailwind
* Context API para autenticação
* Componentização

---

# Arquitetura

Back-end organizado em:

controller

service

repository

entity

dto

mapper

config

security

exception

util

Front-end organizado em:

pages

components

services

contexts

hooks

routes

assets

styles

---

# Banco de Dados

SQLite

Criar automaticamente as tabelas.

---

# Sistema de autenticação

Implementar autenticação JWT.

Fluxo:

Cadastro

Login

Receber Token JWT

Salvar Token

Enviar Token nas requisições

Endpoints protegidos.

---

# Cadastro de Usuário

Campos:

Nome

Email

Senha

Regras:

Email único

Senha criptografada com BCrypt

Validação dos campos

---

# Login

Login por:

Email

Senha

Retornar:

Token JWT

Nome

Email

---

# Pedido

Criar entidade Pedido contendo:

id

cliente

enderecoEntrega

status

dataCriacao

dataAtualizacao

listaItens

---

# ItemPedido

Campos:

id

nome

quantidade

---

# Enum StatusPedido

Criar enum com:

RECEBIDO

EM_PREPARO

SAIU_PARA_ENTREGA

ENTREGUE

CANCELADO

---

# API REST

Implementar endpoints:

POST /auth/register

POST /auth/login

GET /pedidos

GET /pedidos/{id}

POST /pedidos

PUT /pedidos/{id}/status

Todos protegidos por JWT, exceto login e cadastro.

---

# DTOs

Criar DTOs para todas as operações.

Nunca expor Entities diretamente.

---

# Validações

Campos obrigatórios

Email válido

Quantidade maior que zero

Cliente obrigatório

Endereço obrigatório

---

# Tratamento de erros

Criar tratamento global utilizando @ControllerAdvice.

Retornar mensagens amigáveis.

---

# Swagger

Configurar Swagger para documentação automática.

---

# README

Criar README completo contendo:

Descrição

Tecnologias

Como executar

Como rodar Front

Como rodar Back

Como testar no Swagger

Como testar login

Como gerar token

Como executar com Maven

Estrutura das pastas

---

# Front-end

Criar telas:

Login

Cadastro

Dashboard

Lista de Pedidos

Novo Pedido

---

# Tela Login

Campos:

Email

Senha

Botão Entrar

---

# Tela Cadastro

Nome

Email

Senha

Botão Cadastrar

---

# Dashboard

Após login.

Mostrar menu lateral simples.

---

# Lista de Pedidos

Tabela contendo:

ID

Cliente

Status

Endereço

Data

Botão Alterar Status

Botão Visualizar

---

# Novo Pedido

Formulário contendo:

Cliente

Endereço

Adicionar Item

Nome do Item

Quantidade

Botão Adicionar

Botão Salvar Pedido

---

# Atualizar Status

Permitir selecionar:

RECEBIDO

EM_PREPARO

SAIU_PARA_ENTREGA

ENTREGUE

CANCELADO

Atualizar pela API.

---

# Axios

Criar camada de serviços.

Não fazer chamadas HTTP diretamente nas páginas.

---

# React Router

Rotas:

/login

/register

/dashboard

/pedidos

/pedidos/novo

Rotas protegidas.

---

# Context API

Criar AuthContext.

Salvar JWT.

Logout.

Proteção das páginas.

---

# Interface

Visual moderno.

Responsivo.

Cards.

Tabela limpa.

Botões consistentes.

Sem bibliotecas pesadas.

---

# Git

Durante o desenvolvimento, sugerir commits pequenos e semânticos, por exemplo:

Initial project

Create authentication

Configure JWT

Create Pedido entity

Create Pedido API

Implement React Login

Implement Pedido Screen

Integrate Front and Back

Update README

---

# Código

Gerar código limpo.

Seguir princípios SOLID.

Seguir Clean Code.

Evitar duplicação.

Utilizar injeção de dependência.

Usar ResponseEntity.

Utilizar Optional quando apropriado.

Nunca deixar código incompleto.

---

# Qualidade

Todo o projeto deve compilar sem erros.

Não deixar TODO.

Não deixar comentários indicando código faltando.

Criar todos os arquivos necessários.

---

# Objetivo

O projeto deve parecer um pequeno sistema profissional desenvolvido por um Desenvolvedor Full Stack Pleno/Sênior, mas sem exagerar em complexidade.

Ele deve atender exatamente ao desafio solicitado pela empresa, demonstrando conhecimento em:

* Java
* Spring Boot
* REST API
* React
* JWT
* JPA
* SQLite
* Arquitetura em camadas
* Boas práticas
* Git

Ao final, gere também um arquivo `docker-compose.yml` (opcional, apenas para facilitar a execução), um `.gitignore` adequado para Java e React e uma coleção do Postman com todas as requisições da API.
