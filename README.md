# Order Management API

API REST para gerenciamento de pedidos desenvolvida com **Spring Boot**, seguindo boas práticas de arquitetura em camadas e princípios SOLID.

Projeto desenvolvido como desafio da plataforma [DIO - Digital Innovation One](https://www.dio.me).

---

## 🛠️ Tecnologias utilizadas

- Java 17
- Spring Boot 4.2.0
- Spring Data JPA
- Spring Web (MVC)
- Bean Validation
- Banco de dados H2 (in-memory)
- Maven
- JUnit 5 + Mockito (testes unitários)

---

## 🏗️ Arquitetura

O projeto segue o padrão de arquitetura em camadas:

```
Controller  →  Service  →  Repository  →  Banco de Dados
    ↑               ↑
  HTTP            Regras
 Request        de Negócio
```

### Estrutura de pacotes

```
src/main/java/br/com/dio/order_management_api/
├── controller/
│   ├── ProdutoController.java
│   └── PedidoController.java
├── service/
│   ├── ProdutoService.java
│   └── PedidoService.java
├── repository/
│   ├── ProdutoRepository.java
│   └── PedidoRepository.java
├── model/
│   ├── entity/
│   │   ├── Produto.java
│   │   ├── Pedido.java
│   │   └── ItemPedido.java
│   └── enums/
│       └── StatusPedido.java
└── exception/
    └── GlobalExceptionHandler.java
```

---

## 🚀 Como executar

### Pré-requisitos

- Java 17+
- Maven 3.8+ (ou usar o `mvnw` incluso no projeto)

### Rodando a aplicação

```bash
# Clone o repositório
git clone https://github.com/leon-Sab/order-management-api.git

# Acesse o diretório
cd order-management-api

# Execute com o Maven Wrapper
./mvnw spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

---

## 📋 Endpoints

### Produtos

| Método | Rota | Descrição |
|--------|------|-----------|
| `POST` | `/produtos` | Cadastrar novo produto |
| `GET` | `/produtos` | Listar todos os produtos |
| `GET` | `/produtos/{id}` | Buscar produto por ID |

#### Exemplo — Cadastrar produto

```http
POST /produtos
Content-Type: application/json

{
  "nome": "Notebook",
  "preco": 3500.00
}
```

---

### Pedidos

| Método | Rota | Descrição |
|--------|------|-----------|
| `POST` | `/pedidos` | Criar novo pedido |
| `POST` | `/pedidos/{id}/itens` | Adicionar item ao pedido |
| `GET` | `/pedidos` | Listar todos os pedidos |
| `GET` | `/pedidos/{id}` | Buscar pedido por ID |
| `GET` | `/pedidos/status/{status}` | Listar pedidos por status |

#### Exemplo — Adicionar item ao pedido

```http
POST /pedidos/1/itens
Content-Type: application/json

{
  "produtoId": 1,
  "quantidade": 2
}
```

#### Status disponíveis

`CRIADO` · `PAGO` · `ENVIADO` · `ENTREGUE` · `CANCELADO`

---

## ⚠️ Tratamento de erros

A API retorna respostas de erro padronizadas:

```json
{
  "status": 404,
  "erro": "Não encontrado",
  "mensagem": "Produto não encontrado: 99",
  "timestamp": "2026-11-21T22:00:00"
}
```

| Código | Situação |
|--------|----------|
| `400` | Argumento inválido (ex: quantidade ≤ 0) |
| `404` | Recurso não encontrado |

---

## 🧪 Testes

```bash
# Executar todos os testes
./mvnw test
```

Cobertura atual:

| Classe | Testes |
|--------|--------|
| `ProdutoServiceTest` | 4 cenários |
| `PedidoServiceTest` | 5 cenários |

---

## 🗄️ Console H2

Durante o desenvolvimento, o banco de dados pode ser acessado via browser:

```
URL:      http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:orderdb
Usuário:  sa
Senha:    (vazio)
```

---

## 👨‍💻 Autor

Desenvolvido por **[Leonardo Sabino Oliveira De Araujo]**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-blue?style=flat&logo=linkedin)](https://www.linkedin.com/in/leonardo-sabino-43551716a/)
[![GitHub](https://img.shields.io/badge/GitHub-black?style=flat&logo=github)](https://github.com/leon-Sab)
