# Projeto Acadêmico com Spring Boot

[![Java](https://img.shields.io/badge/Linguagem-Java-%23ED8B00.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Framework-Spring%20Boot-brightgreen)](https://spring.io/projects/spring-boot)
[![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow)](#)

## 📚 Descrição
Este é um projeto acadêmico desenvolvido com o objetivo de estudar e experimentar o framework **Spring Boot**. Ele foi projetado para explorar conceitos como criação de APIs RESTful, injeção de dependências, gerenciamento de banco de dados com **JPA/Hibernate**, entre outros aspectos fundamentais do desenvolvimento backend com Java.

Além disso, o projeto serve como base para boas práticas de código, análises arquiteturais e integração com ferramentas modernas de desenvolvimento.

---

## 🚀 Funcionalidades
- Configuração inicial e estruturação de um projeto Spring Boot.
- Criação de endpoints RESTful.
- Integração com banco de dados utilizando **Spring Data JPA**.
- Manipulação de **entidades** e **relacionamentos** no banco de dados.

---

## 🔧 Tecnologias utilizadas
As principais tecnologias e ferramentas utilizadas no projeto incluem:
- **Linguagem**: Java 11+
- **Framework**: Spring Boot
- **Gerenciador de Dependências**: Maven
- **Banco de Dados**: H2 (em memória)
- **Outras Tecnologias**: Lombok, Hibernate, e Spring Data JPA.

---

## 🎯 Casos de uso
Este projeto pode ser reutilizado para:
- **Treinamento pessoal:** explorar recursos do Spring Boot em um ambiente seguro.
- **Prototipagem:** criação de pequenos serviços backend para projetos internos ou acadêmicos.
- **Melhoria contínua:** praticar a adição de novas funcionalidades e soluções em um projeto educativo.

---

## 🛠️ Como executar o projeto

### Pré-requisitos
Certifique-se de que as seguintes ferramentas estão **instaladas e configuradas**:
- [Java 11 ou superior](https://www.oracle.com/java/technologies/)
- [Maven](https://maven.apache.org/)
- Uma IDE para Java como [IntelliJ IDEA](https://www.jetbrains.com/idea/) ou [Eclipse](https://www.eclipse.org/)

---

### Passo a passo
1. Clone este repositório para sua máquina local:
   ```bash
   git clone https://github.com/Dejailton/SpringAula.git
   ```

2. Acesse o diretório raiz do projeto:
   ```bash
   cd SpringAula
   ```

3. Garanta que as dependências estão atualizadas:
   ```bash
   mvn clean install
   ```

4. Execute a aplicação:
   ```bash
   mvn spring-boot:run
   ```

5. Acesse a API no navegador ou no cliente HTTP:  
   **http://localhost:8080**

---

## 🗒️ Rotas da aplicação

### **Rotas disponíveis**

#### 1️⃣ **GET /api/v1/entidades**
- **Descrição**: Retorna a lista de todas as entidades cadastradas.
- **Exemplo de Resposta**:
    ```json
    [
      {
        "id": 1,
        "nome": "Entidade Exemplo",
        "descricao": "Uma descrição aqui"
      },
      {
        "id": 2,
        "nome": "Outra Entidade",
        "descricao": "Mais detalhes aqui"
      }
    ]
    ```

#### 2️⃣ **GET /api/v1/entidades/{id}**
- **Descrição**: Busca uma entidade específica pelo `id`.
- **Parâmetro**:
    - `id` (long): identificador único da entidade.
- **Exemplo de Resposta**:
    ```json
    {
      "id": 1,
      "nome": "Entidade Exemplo",
      "descricao": "Uma descrição detalhada."
    }
    ```

#### 3️⃣ **POST /api/v1/entidades**
- **Descrição**: Adiciona uma nova entidade ao banco de dados.
- **Corpo da Requisição**:
    ```json
    {
      "nome": "Entidade Nova",
      "descricao": "Detalhes da entidade nova aqui"
    }
    ```
- **Exemplo de Resposta** (201 Created):
    ```json
    {
      "id": 3,
      "nome": "Entidade Nova",
      "descricao": "Detalhes da entidade nova aqui"
    }
    ```

#### 4️⃣ **PUT /api/v1/entidades/{id}**
- **Descrição**: Atualiza as informações de uma entidade existente.
- **Parâmetros**:
    - `id`: identificador da entidade a ser atualizado.
- **Corpo da Requisição**:
    ```json
    {
      "nome": "Entidade Atualizada",
      "descricao": "Descrição atualizada da entidade."
    }
    ```
- **Exemplo de Resposta** (200 OK):
    ```json
    {
      "id": 1,
      "nome": "Entidade Atualizada",
      "descricao": "Descrição atualizada da entidade."
    }
    ```

#### 5️⃣ **DELETE /api/v1/entidades/{id}**
- **Descrição**: Remove uma entidade específica pelo `id`.
- **Parâmetro**:
    - `id`: identificador único da entidade.
- **Exemplo de Resposta** (204 No Content): _Sem conteúdo retornado._

---

## 🗂️ Estrutura do Projeto
O projeto possui a seguinte estrutura:
- **`src/main/java`**:  
  Código-fonte principal da aplicação. Contém as camadas:
    - **Controller**: Gerencia as requisições e respostas REST.
    - **Service**: Contém a lógica de negócios e o processamento dos dados.
    - **Repository**: Faz a comunicação com o banco de dados.
    - **Model**: Representa as entidades e seus atributos.
- **`src/main/resources`**: Configuração do projeto:
    - `application.properties` ou `application.yml`: Configurações do banco de dados e do servidor.
- **`src/test/java`**: Contém os testes automatizados do projeto.

---


**Nota**: Este projeto foi desenvolvido para fins acadêmicos e de aprendizado.