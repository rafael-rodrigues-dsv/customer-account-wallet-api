# 🌐 Customer Account Wallet API

## Tópicos

1. [🌐 Customer Walltet API](#-customer-account-walltet-api)
1. [📖 Descrição](#-descrição)
2. [🚀 Funcionalidades](#-funcionalidades)
   - [🧑‍💼 Customers (Clientes)](#-customers-clientes)
   - [🏠 Customer Addresses (Endereços do Cliente)](#-customer-addresses-endereços-do-cliente)
   - [💳 Customer Accounts (Contas do Cliente)](#-customer-accounts-contas-do-cliente)
   - [🔄 Transfers (Transferências)](#-transfers-transferências)
3. [🔧 Tecnologias Utilizadas](#-tecnologias-utilizadas)
4. [🏗️ Arquitetura](#️-arquitetura)
5. [📚 Documentação da API](#-documentação-da-api)

## 📖 Descrição

Bem-vindo à **Customer Account Wallet API**, uma aplicação desenvolvida com Spring Boot que oferece funcionalidades relacionadas a transferências, clientes, endereços de clientes e contas de clientes. Abaixo, você encontrará um resumo das principais funcionalidades, tecnologias utilizadas e a arquitetura da aplicação.

## 🚀 Funcionalidades

### 🧑‍💼 Customers (Clientes)
- Criar um novo cliente.
- Validar a senha de um cliente.
- Obter informações de um cliente por ID.
- Atualizar informações de um cliente por ID.

### 🏠 Customer Addresses (Endereços do Cliente)
- Obter todos os endereços de um cliente por ID.
- Criar um novo endereço para um cliente.
- Obter informações de um endereço de cliente por ID.
- Atualizar informações de um endereço de cliente por ID.

### 💳 Customer Accounts (Contas do Cliente)
- Obter todas as contas de um cliente por ID.
- Criar uma nova conta para um cliente.
- Obter informações de uma conta de cliente por ID.
- Atualizar informações de uma conta de cliente por ID.
- Atualizar saldo de uma conta de cliente por ID.
- Atualizar status de uma conta de cliente por ID.

### 🔄 Transfers (Transferências)
- Realizar uma transferência entre contas bancárias.
- Obter informações de uma transferência por ID.
- Cancelar uma transferência por ID

## 🔧 Tecnologias Utilizadas

- **Spring Boot 3.0.2**
- **Java 17**
- **H2 Database (Runtime Scope)**
- **Lombok**
- **Model Mapper 2.4.4**
- **Hibernate Core 6.4.1.Final**
- **Hibernate Commons Annotations 6.0.5.Final**
- **JUnit Jupiter 5.10.1**
- **Mockito JUnit Jupiter 3.6.0**
- **Jacoco Maven Plugin 0.8.7**
- **Apache Camel 3.15.0**

## 🏗️ Arquitetura

- **Arquitetura Exagonal**
- **Testes de Unidade**
- **SOLID**
- **Clean Code**
- **Repository**
- **Design Patterns**

## 📚 Documentação da API

Para acessar a documentação completa de como consumir a API RESTFull, inicie o projeto local e abra o seguinte link no seu navegador:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Como Rodar Localmente com o IntelliJ

1. Abra o IntelliJ e importe o projeto.
2. Certifique-se de ter configurado um ambiente Java compatível (Java 17).
3. Execute a classe principal `CustomerApiApplication`.
4. Aguarde até que o aplicativo seja iniciado e esteja pronto para aceitar solicitações.
5. Abra o seguinte link no seu navegador: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
