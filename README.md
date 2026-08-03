# Mini Rastreador de Pedidos — Foody Delivery

Sistema completo de rastreamento de pedidos de delivery com back-end em Java/Spring Boot e front-end em React.

---

## Descrição

API REST + interface web que permite:

- Cadastro e autenticação de usuários via JWT
- Criação de pedidos com itens
- Listagem e visualização de pedidos
- Atualização de status do pedido (RECEBIDO → EM_PREPARO → SAIU_PARA_ENTREGA → ENTREGUE / CANCELADO)

---

## Tecnologias

### Back-end
| Tecnologia | Versão |
|---|---|
| Java | 25 (LTS) |
| Spring Boot | 3.5.4 |
| Spring Security + JWT (jjwt) | 0.12.6 |
| Spring Data JPA + Hibernate | gerenciado pelo Spring Boot |
| SQLite (sqlite-jdbc) | gerenciado pelo Spring Boot |
| Lombok | gerenciado pelo Spring Boot |
| Bean Validation | gerenciado pelo Spring Boot |
| Springdoc OpenAPI (Swagger UI) | 2.8.9 |
| Maven | 3.9+ |

### Front-end
| Tecnologia | Versão |
|---|---|
| React | 18 |
| Vite | 5 |
| React Router DOM | 6 |
| Axios | 1 |
| CSS puro | — |

---

## Pré-requisitos

- **Java 25** (ou Java 21+)
- **Maven 3.9+** — ou use o Maven embutido no VS Code Java Extension
- **Node.js 18+** e **npm**

---

## Como executar

### Opção 1 — Docker Compose (mais fácil)

```bash
docker-compose up --build
```

- API disponível em: `http://localhost:8080`
- Front disponível em: `http://localhost:5173`

---

### Opção 2 — Execução manual

#### Back-end (API)

```bash
cd services/api
mvn clean spring-boot:run
```

A API sobe em `http://localhost:8080`.

O banco SQLite (`foody.db`) é criado automaticamente na pasta `services/api/` na primeira execução.

#### Como executar com Maven (package + jar)

```bash
cd services/api
mvn clean package -DskipTests
java -jar target/foody-delivery-tracker-api-1.0.0.jar
```

#### Front-end

```bash
cd apps/web
npm install
npm run dev
```

O front sobe em `http://localhost:5173`.

---

## Como testar no Swagger

1. Inicie o back-end
2. Acesse: `http://localhost:8080/swagger-ui.html`
3. Os endpoints de autenticação (`/auth/register` e `/auth/login`) estão abertos
4. Para endpoints protegidos:
   - Execute `POST /auth/login` e copie o `token` da resposta
   - Clique em **Authorize** (cadeado) no topo da página
   - Insira: `Bearer <seu_token>` e clique em **Authorize**

---

## Como testar login e gerar token JWT

### 1. Cadastrar usuário

```http
POST http://localhost:8080/auth/register
Content-Type: application/json

{
  "nome": "João Silva",
  "email": "joao@email.com",
  "senha": "123456"
}
```

### 2. Fazer login

```http
POST http://localhost:8080/auth/login
Content-Type: application/json

{
  "email": "joao@email.com",
  "senha": "123456"
}
```

Resposta:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "nome": "João Silva",
  "email": "joao@email.com"
}
```

### 3. Usar o token nas requisições protegidas

```http
GET http://localhost:8080/pedidos
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

## Endpoints da API

| Método | Rota | Descrição | Auth |
|---|---|---|---|
| POST | `/auth/register` | Cadastrar usuário | Não |
| POST | `/auth/login` | Autenticar e obter token | Não |
| GET | `/pedidos` | Listar todos os pedidos | JWT |
| GET | `/pedidos/{id}` | Buscar pedido por ID | JWT |
| POST | `/pedidos` | Criar novo pedido | JWT |
| PUT | `/pedidos/{id}/status` | Atualizar status do pedido | JWT |

---

## Telas do Front-end

| Rota | Tela |
|---|---|
| `/login` | Login |
| `/register` | Cadastro |
| `/dashboard` | Dashboard com métricas |
| `/pedidos` | Lista de pedidos |
| `/pedidos/novo` | Formulário de novo pedido |

---

## Estrutura de pastas

```
foody-delivery/
├── apps/
│   └── web/                        # Front-end React + Vite
│       └── src/
│           ├── components/         # AppShell, Sidebar, StatusBadge, ProtectedRoute
│           ├── contexts/           # AuthContext (JWT, login, logout)
│           ├── hooks/              # useAuth
│           ├── pages/              # Login, Register, Dashboard, Pedidos, NovoPedido
│           ├── routes/             # AppRoutes (rotas protegidas)
│           ├── services/           # api.js (Axios), authService.js, pedidoService.js
│           └── styles/             # global.css
├── services/
│   └── api/                        # Back-end Spring Boot
│       └── src/main/java/com/foody/tracker/
│           ├── config/             # SecurityConfig, OpenApiConfig
│           ├── controller/         # AuthController, PedidoController
│           ├── dto/                # Request/Response DTOs
│           ├── entity/             # AppUser, Pedido, ItemPedido, StatusPedido
│           ├── exception/          # GlobalExceptionHandler, BusinessException, ...
│           ├── mapper/             # PedidoMapper
│           ├── repository/         # UserRepository, PedidoRepository, ItemPedidoRepository
│           ├── security/           # JwtService, JwtAuthenticationFilter, ...
│           ├── service/            # AuthService, PedidoService
│           └── util/               # ApiPaths
├── docs/
│   └── Foody-Delivery-Order-Tracker.postman_collection.json
├── docker-compose.yml
└── README.md
```

---

## Coleção Postman

Importe o arquivo `docs/Foody-Delivery-Order-Tracker.postman_collection.json` no Postman para testar todos os endpoints com exemplos prontos.
