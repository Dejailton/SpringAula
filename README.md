# Projeto Acadêmico para Gerenciamento de Produtos com Spring Boot

[![Java](https://img.shields.io/badge/Linguagem-Java-%23ED8B00.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Framework-Spring%20Boot-brightgreen)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Build-Maven-C71A36?logo=apache-maven)](https://maven.apache.org/)
[![Azure](https://img.shields.io/badge/Deploy-Azure-0078D4?logo=microsoft-azure)](https://bootcamp-produtos-dejailton.azurewebsites.net/)

## 📚 Descrição
Este é um projeto acadêmico desenvolvido com o objetivo de estudar e experimentar o framework **Spring Boot**. Ele foi projetado para explorar conceitos como criação de APIs RESTful, injeção de dependências, gerenciamento de banco de dados com **JPA/Hibernate**, entre outros aspectos fundamentais do desenvolvimento backend com Java.

O sistema gerencia um catálogo de **produtos**, permitindo operações CRUD completas através de uma API REST e uma interface web interativa.

## 🚀 Funcionalidades
- ✅ API RESTful para gerenciamento de produtos
- ✅ Interface web responsiva com Bootstrap
- ✅ Operações CRUD completas (Create, Read, Update, Delete)
- ✅ Validação de dados com Bean Validation
- ✅ Tratamento centralizado de exceções
- ✅ Documentação interativa com Swagger/OpenAPI
- ✅ Banco de dados H2 em memória (desenvolvimento)
- ✅ Suporte a SQL Server (produção)

---

## 🔧 Tecnologias utilizadas
As principais tecnologias e ferramentas utilizadas no projeto incluem:
- **Linguagem**: Java 21
- **Framework**: Spring Boot 4.0.2
- **Gerenciador de Dependências**: Maven
- **Banco de Dados**: H2 (em memória - desenvolvimento) / SQL Server (produção)
- **Outras Tecnologias**:
    - Lombok (redução de boilerplate)
    - Hibernate/JPA (ORM)
    - Spring Data JPA (acesso a dados)
    - Swagger/OpenAPI (documentação de API)
    - Bootstrap 5 (interface web)
- **Deploy**: Azure App Service

---

## 🎯 Casos de uso
Este projeto pode ser reutilizado para:
- **Treinamento pessoal:** explorar recursos do Spring Boot em um ambiente seguro
- **Prototipagem:** criação de pequenos serviços backend para projetos internos ou acadêmicos
- **Melhoria contínua:** praticar a adição de novas funcionalidades e soluções em um projeto educativo
- **Referência:** exemplo de boas práticas em arquitetura REST com Spring Boot

---

## 🛠️ Como executar o projeto localmente

### Pré-requisitos
Certifique-se de que as seguintes ferramentas estão **instaladas e configuradas**:
- [Java 21 ou superior](https://www.oracle.com/java/technologies/)
- [Maven 3.6+](https://maven.apache.org/)
- Uma IDE para Java como [IntelliJ IDEA](https://www.jetbrains.com/idea/) ou [Eclipse](https://www.eclipse.org/)

---

### Passo a passo para execução local

#### 1️⃣ Clone este repositório para sua máquina local:
```bash
git clone https://github.com/Dejailton/projetospring.git
```

#### 2️⃣ Acesse o diretório raiz do projeto:
```bash
cd projetospring
```

#### 3️⃣ Garanta que as dependências estão atualizadas:
```bash
mvn clean install
```

#### 4️⃣ Execute a aplicação:

**Opção A - Usando Maven:**
```bash
mvn spring-boot:run
```

**Opção B - Usando Maven Wrapper (Linux/Mac):**
```bash
./mvnw spring-boot:run
```

**Opção C - Usando Maven Wrapper (Windows):**
```bash
mvnw.cmd spring-boot:run
```

#### 5️⃣ Acesse a aplicação:

- **Interface Web:** [http://localhost:8084](http://localhost:8084)
- **API REST:** [http://localhost:8084/produtos](http://localhost:8084/produtos)
- **Swagger UI:** [http://localhost:8084/swagger-ui/index.html](http://localhost:8084/swagger-ui/index.html)
- **Console H2 (Banco de Dados):** [http://localhost:8084/h2-console](http://localhost:8084/h2-console)

---

### ⚙️ Configurações e Variáveis de Ambiente

A aplicação utiliza o banco de dados **H2 em memória** por padrão para desenvolvimento local. Você pode customizar as configurações através de variáveis de ambiente:

| Variável | Valor Padrão | Descrição |
|----------|--------------|-----------|
| `SERVER_PORT` | `8084` | Porta onde a aplicação será executada |
| `SPRING_DATASOURCE_URL` | `jdbc:h2:mem:testdb` | URL de conexão do banco de dados |
| `SPRING_DATASOURCE_DRIVER` | `org.h2.Driver` | Driver JDBC do banco de dados |
| `SPRING_DATASOURCE_USERNAME` | `sa` | Usuário do banco de dados |
| `SPRING_DATASOURCE_PASSWORD` | _(vazio)_ | Senha do banco de dados |
| `SPRING_JPA_DATABASE_PLATFORM` | `org.hibernate.dialect.H2Dialect` | Dialeto do Hibernate |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | `update` | Estratégia de criação do schema |
| `SPRING_H2_CONSOLE_ENABLED` | `true` | Habilita o console H2 |
| `SPRING_H2_CONSOLE_PATH` | `/h2-console` | Caminho do console H2 |

#### Exemplo de execução com variáveis personalizadas:

**Linux/Mac:**
```bash
export SERVER_PORT=9090
mvn spring-boot:run
```

**Windows (CMD):**
```cmd
set SERVER_PORT=9090
mvn spring-boot:run
```

**Windows (PowerShell):**
```powershell
$env:SERVER_PORT="9090"
mvn spring-boot:run
```

---

### 🗄️ Acessando o Console H2

O Console H2 permite visualizar e manipular os dados em tempo de execução:

1. Acesse: [http://localhost:8084/h2-console](http://localhost:8084/h2-console)
2. Configure a conexão:
    - **JDBC URL:** `jdbc:h2:mem:testdb`
    - **User Name:** `sa`
    - **Password:** _(deixe em branco)_
3. Clique em **Connect**

---

## 🗒️ API - Rotas e Endpoints

A API REST segue os princípios RESTful e oferece os seguintes endpoints:

### **Base URL (Local):** `http://localhost:8084`
### **Base URL (Produção):** `https://bootcamp-produtos-dejailton.azurewebsites.net/`

---

### 📋 **Modelo de Dados - Produto**

```json
{
  "id": 1,
  "nome": "Café Premium",
  "preco": 29.90
}
```

**Atributos:**
- `id` (Long): Identificador único do produto (gerado automaticamente)
- `nome` (String): Nome do produto (obrigatório)
- `preco` (Double): Preço do produto (obrigatório, deve ser maior que zero)

---

### 1️⃣ **GET /produtos**
Lista todos os produtos cadastrados.

**Requisição:**
```bash
curl -X GET http://localhost:8084/produtos
```

**Resposta de Sucesso (200 OK):**
```json
[
  {
    "id": 1,
    "nome": "Café Premium",
    "preco": 29.90
  },
  {
    "id": 2,
    "nome": "Açúcar Cristal",
    "preco": 5.50
  },
  {
    "id": 3,
    "nome": "Leite Integral",
    "preco": 4.99
  }
]
```

---

### 2️⃣ **GET /produtos/{id}**
Busca um produto específico pelo ID.

**Parâmetros:**
- `id` (Long): Identificador único do produto

**Requisição:**
```bash
curl -X GET http://localhost:8084/produtos/1
```

**Resposta de Sucesso (200 OK):**
```json
{
  "id": 1,
  "nome": "Café Premium",
  "preco": 29.90
}
```

**Resposta de Erro (404 Not Found):**
```json
{
  "message": "Produto não encontrado com id: 999"
}
```

---

### 3️⃣ **POST /produtos**
Cadastra um novo produto no sistema.

**Corpo da Requisição:**
```json
{
  "nome": "Chocolate ao Leite",
  "preco": 8.50
}
```

**Requisição:**
```bash
curl -X POST http://localhost:8084/produtos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Chocolate ao Leite",
    "preco": 8.50
  }'
```

**Resposta de Sucesso (201 Created):**
```json
{
  "id": 4,
  "nome": "Chocolate ao Leite",
  "preco": 8.50
}
```

**Resposta de Erro (400 Bad Request) - Validação:**
```json
{
  "errors": {
    "nome": "nome é obrigatório",
    "preco": "preco deve ser maior que zero"
  }
}
```

---

### 4️⃣ **PUT /produtos/{id}**
Atualiza os dados de um produto existente.

**Parâmetros:**
- `id` (Long): Identificador do produto a ser atualizado

**Corpo da Requisição:**
```json
{
  "nome": "Café Premium Especial",
  "preco": 35.00
}
```

**Requisição:**
```bash
curl -X PUT http://localhost:8084/produtos/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Café Premium Especial",
    "preco": 35.00
  }'
```

**Resposta de Sucesso (200 OK):**
```json
{
  "id": 1,
  "nome": "Café Premium Especial",
  "preco": 35.00
}
```

**Resposta de Erro (404 Not Found):**
```json
{
  "message": "Produto não encontrado com id: 999"
}
```

---

### 5️⃣ **DELETE /produtos/{id}**
Remove um produto do sistema.

**Parâmetros:**
- `id` (Long): Identificador único do produto

**Requisição:**
```bash
curl -X DELETE http://localhost:8084/produtos/1
```

**Resposta de Sucesso (204 No Content):**
_Sem conteúdo retornado_

**Resposta de Erro (404 Not Found):**
```json
{
  "message": "Produto não encontrado com id: 999"
}
```

---

## 📖 Documentação Interativa (Swagger)

A aplicação possui documentação interativa da API gerada automaticamente com Swagger/OpenAPI.

**Acesso:**
- **Local:** [http://localhost:8084/swagger-ui/index.html](http://localhost:8084/swagger-ui/index.html)


## 🗂️ Estrutura do Projeto

O projeto segue a arquitetura em camadas do Spring Boot:

```
src/
├── main/
│   ├── java/com/deloitte/springaula/
│   │   ├── controller/          # Controladores REST
│   │   │   └── ProdutoController.java
│   │   ├── service/             # Camada de serviço (lógica de negócio)
│   │   │   ├── ProdutoService.java
│   │   │   ├── ProdutoReadService.java
│   │   │   ├── ProdutoWriteService.java
│   │   │   └── impl/
│   │   │       └── ProdutoServiceImpl.java
│   │   ├── repository/          # Camada de acesso a dados
│   │   │   └── ProdutoRepository.java
│   │   ├── model/               # Entidades JPA
│   │   │   └── Produto.java
│   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── ProdutoRequestDTO.java
│   │   │   └── ProdutoResponseDTO.java
│   │   ├── mapper/              # Conversores Entity <-> DTO
│   │   │   └── ProdutoMapper.java
│   │   ├── exception/           # Exceções customizadas
│   │   │   ├── ProdutoNotFoundException.java
│   │   │   ├── BusinessRuleViolationException.java
│   │   │   └── GlobalExceptionHandler.java
│   │   └── SpringAulaApplication.java
│   └── resources/
│       ├── application.properties    # Configurações da aplicação
│       └── static/                   # Interface web (HTML/CSS/JS)
│           ├── index.html
│           ├── app.js
│           └── style.css
└── test/                        # Testes automatizados
```

### Descrição das Camadas:

- **Controller**: Gerencia as requisições HTTP e respostas REST
- **Service**: Contém a lógica de negócios e regras de validação
- **Repository**: Comunicação com o banco de dados (Spring Data JPA)
- **Model**: Representa as entidades do domínio (tabelas do banco)
- **DTO**: Objetos de transferência de dados (Request/Response)
- **Mapper**: Conversão entre Entities e DTOs
- **Exception**: Tratamento centralizado de erros

---

## ☁️ Deploy no Azure

A aplicação está hospedada no **Azure App Service**, proporcionando alta disponibilidade e escalabilidade.

### 📋 Informações do Deploy

- **Plataforma:** Azure App Service
- **Banco de Dados:** SQL Server (Azure SQL Database)
- **Build:** Maven
- **Runtime:** Java 21
- **Sistema Operacional:** Linux

### 🚀 Processo de Deploy

O deploy no Azure é realizado através de CI/CD integrado ao GitHub. O fluxo básico inclui:

1. **Commit no GitHub** - Código é enviado para o repositório
2. **Build automático** - GitHub Actions executa `mvn clean package`
3. **Geração do artefato** - Arquivo `.jar` é criado
4. **Deploy no Azure** - Artefato é implantado no App Service
5. **Restart automático** - Aplicação é reiniciada com a nova versão

### 🔐 Variáveis de Ambiente (Produção)

As seguintes variáveis de ambiente são configuradas no Azure Portal (Configuration > Application settings):

| Variável | Valor (Produção)                               |
|----------|------------------------------------------------|
| `SERVER_PORT` | 80                                             |
| `SPRING_DATASOURCE_URL` | URL do Azure SQL Database                      |
| `SPRING_DATASOURCE_DRIVER` | `com.microsoft.sqlserver.jdbc.SQLServerDriver` |
| `SPRING_DATASOURCE_USERNAME` | Usuário do SQL Server                          |
| `SPRING_DATASOURCE_PASSWORD` | Senha do SQL Server (secret)                   |
| `SPRING_JPA_DATABASE_PLATFORM` | `org.hibernate.dialect.SQLServerDialect`       |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | `update`                                       |
| `SPRING_H2_CONSOLE_ENABLED` | `false`                                        |

### 🔄 CI/CD com GitHub Actions

O projeto utiliza **GitHub Actions** para automatizar o processo de integração contínua (CI) e entrega contínua (CD). Cada commit ou pull request na branch `main` dispara automaticamente o pipeline de CI/CD.

#### 📋 Pipeline de CI/CD

O workflow está configurado no arquivo `.github/workflows/build-azure.yml` e executa as seguintes etapas:

##### **1️⃣ Build e Testes** (Job: `build`)
- ✅ Checkout do código
- ✅ Configuração do ambiente Java 21
- ✅ Cache de dependências Maven
- ✅ Compilação do projeto (`mvn clean package`)
- ✅ Execução de testes automatizados
- ✅ Geração do artefato `.jar`
- ✅ Upload do artefato para o próximo job

##### **2️⃣ Deploy para Azure** (Job: `deploy`)
- ✅ Download do artefato gerado
- ✅ Login no Azure usando credenciais seguras
- ✅ Deploy automático no Azure App Service
- ✅ Verificação do deploy
- ✅ Logout do Azure

#### 🚦 Gatilhos do Workflow

O pipeline é acionado nas seguintes situações:
- **Push na branch `main`**: Executa build + deploy
- **Pull Request para `main`**: Executa apenas build e testes
- **Manualmente**: Via `workflow_dispatch` no GitHub Actions

#### 🔐 Secrets e Variáveis

O workflow utiliza secrets e variáveis configuradas no GitHub:

| Tipo | Nome | Descrição |
|------|------|-----------|
| Secret | `AZURE_CREDENTIALS` | Credenciais de autenticação do Azure |
| Variable | `AZURE_RESOURCE_GROUP` | Nome do Resource Group no Azure |
| Variable | `AZURE_WEBAPP_NAME` | Nome do Web App no Azure |

---
### 📊 Monitoramento

O Azure fornece ferramentas de monitoramento integradas:

- **Application Insights**: Telemetria, métricas de performance e rastreamento de requisições
- **Log Stream**: Visualização de logs em tempo real
- **Metrics**: Gráficos de CPU, memória, requisições HTTP
- **Alerts**: Notificações automáticas para eventos críticos

**Acesso ao Painel:** [Azure Portal](https://portal.azure.com/)

---

## 🧪 Testes

### Executar todos os testes:
```bash
mvn test
```

### Executar testes com relatório de cobertura:
```bash
mvn clean test jacoco:report
```

---

## 📝 Notas Importantes

- ⚠️ O banco H2 em memória é utilizado **apenas em ambiente de desenvolvimento**
- ⚠️ Em produção, a aplicação utiliza **SQL Server no Azure**
- ⚠️ O Console H2 é **desabilitado em produção** por questões de segurança
- ⚠️ Os dados do H2 são **perdidos quando a aplicação é reiniciada** (banco em memória)
- ✅ A aplicação suporta configuração via **variáveis de ambiente**
- ✅ O projeto utiliza **Java 21** e **Spring Boot 4.0.2**
- ✅ Validações são feitas tanto no backend (Bean Validation) quanto no frontend (JavaScript)

---

## 🤝 Contribuindo

Este é um projeto acadêmico, mas contribuições são bem-vindas! Para contribuir:

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/MinhaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona MinhaFeature'`)
4. Push para a branch (`git push origin feature/MinhaFeature`)
5. Abra um Pull Request

---

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos e de aprendizado.

---

## 👤 Autor

**Dejailton**
- Linkedin: [@Dejailton](https://www.linkedin.com/in/dejailton-da-silva-queiroz-771867319/)

---

**Desenvolvido com ☕ e Spring Boot** 🚀
