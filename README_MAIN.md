# 🏗️ Hexagonal E-Commerce Platform

Plataforma de e-commerce moderna construída com **Arquitetura Hexagonal**, **Microserviços** e **Angular 18**.

**Status:** ✅ Backend Completo | ✅ Frontend Bonito | ✅ Totalmente Integrado

---

## 📚 Documentação Rápida

| Componente | Documentação | Porta |
|-----------|---|---|
| 🏠 **Frontend** | [README Frontend](./hexagonal-ecommerce-frontend/README.md) | 80 / 4200 |
| 📦 **Product Service** | [README Product](./services/product-service/README.md) | 8081 |
| 👤 **Customer Service** | [README Customer](./services/customer-service/README.md) | 8082 |
| 🐘 **PostgreSQL** | - | 5432 |
| 🔴 **Redis** | - | 6379 |
| 📨 **Kafka** | [Kafka UI](http://localhost:8080) | 9092 |

---

## 🚀 Quick Start (Docker Compose)

### 1. Pré-requisitos
- Docker & Docker Compose instalados
- 8GB RAM disponível

### 2. Rodar Tudo com Um Comando

```bash
cd hexagonal-ecommerce-platform
docker-compose up -d
```

### 3. Aguarde os Serviços Iniciarem (1-2 min)

```bash
docker-compose logs -f
```

### 4. Acessar Aplicações

| Serviço | URL |
|---------|-----|
| 🌐 Frontend | [http://localhost](http://localhost) |
| 🍌 Kafka UI | [http://localhost:8080](http://localhost:8080) |
| 📊 PostgreSQL | localhost:5432 |

---

## 🛑 Parar Tudo

```bash
docker-compose down
```

---

## 💻 Desenvolvimento Local (Sem Docker)

### 1️⃣ Frontend (Angular)

```bash
cd hexagonal-ecommerce-frontend
npm install
npm start
# Acessa: http://localhost:4200
```

### 2️⃣ Product Service (Java Spring Boot)

```bash
cd services/product-service
# Ter Java 21+ e Maven instalados
mvn clean spring-boot:run
# Roda na porta 8081
```

### 3️⃣ Customer Service (Java Spring Boot)

```bash
cd services/customer-service
mvn clean spring-boot:run
# Roda na porta 8082
```

### 4️⃣ Dependências (PostgreSQL, Redis, Kafka)

```bash
# Rodar apenas os serviços de suporte (sem apps)
docker-compose up postgres redis kafka -d
```

---

## 📊 Arquitetura

```
┌─────────────────────────────────────────────┐
│         Angular Frontend (Port 80)           │
│         ✅ Login, Produtos, Perfil           │
└────────────────┬────────────────────────────┘
                 │ HTTP/REST
    ┌────────────┼────────────┐
    │            │            │
┌───▼────┐  ┌───▼────┐  ┌───▼────┐
│Product │  │Customer│  │  Order │ (Future)
│Service │  │Service │  │Service │
│:8081   │  │:8082   │  │:8083   │
└───┬────┘  └───┬────┘  └───┬────┘
    │          │           │
    └──────────┬───────────┘
               │ (Kafka Events)
    ┌──────────┼──────────┐
    │          │          │
┌───▼───┐  ┌──▼──┐  ┌───▼──┐
│ PostgreSQL │ Redis │ Kafka │
│  :5432     │ :6379 │ :9092 │
└────────────┴───────┴───────┘
```

---

## 🏷️ Endpoints Principais

### 🔐 Autenticação
```
POST   /auth/login                    Login
POST   /customers/register            Registrar novo cliente
```

### 📦 Produtos
```
GET    /products                      Listar todos
GET    /products/{id}                 Detalhe do produto
POST   /products                      Criar (Admin)
PUT    /products/{id}                 Atualizar (Admin)
DELETE /products/{id}                 Deletar (Admin)
```

### 👤 Clientes
```
GET    /customers/me                  Dados do cliente autenticado
PUT    /customers/me                  Atualizar perfil
GET    /customers/{id}                Dados do cliente (Admin)
POST   /customers                     Criar cliente (Admin)
```

---

## 📁 Estrutura do Projeto

```
hexagonal-ecommerce-platform/
├── hexagonal-ecommerce-frontend/     # 🎨 Frontend Angular
│   ├── src/
│   │   ├── app/pages/                # 📄 Páginas (Home, Products, Login, etc)
│   │   ├── app/services/             # 🔧 Serviço de API
│   │   └── styles.scss               # 🎨 Bootstrap + Estilos
│   ├── package.json
│   └── Dockerfile
│
├── services/
│   ├── product-service/              # 📦 Serviço de Produtos
│   │   ├── src/main/java/com/hexagonal/product/
│   │   │   ├── application/          # 🎯 Casos de Uso (Ports & Use Cases)
│   │   │   ├── domain/               # 💎 Entidades & Lógica de Negócio
│   │   │   ├── infrastructure/       # 🔌 Adapters (HTTP, BD, etc)
│   │   │   └── presentation/         # 🎤 Controllers
│   │   ├── pom.xml
│   │   └── Dockerfile
│   │
│   └── customer-service/             # 👤 Serviço de Clientes
│       ├── src/main/java/com/hexagonal/customer/
│       ├── pom.xml
│       └── Dockerfile
│
└── docker-compose.yml                # 🐳 Orquestração
```

---

## 🔧 Variáveis de Ambiente

### Frontend (.env)
```
REACT_APP_API_URL=http://localhost:8080/api
```

### Services (via Docker)
```
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/product_db
SPRING_DATASOURCE_USERNAME=admin
SPRING_DATASOURCE_PASSWORD=admin
KAFKA_BOOTSTRAP_SERVERS=kafka:9092
```

---

## 🧪 Testes

### Frontend
```bash
cd hexagonal-ecommerce-frontend
npm test
```

### Product Service
```bash
cd services/product-service
mvn test
```

### Customer Service
```bash
cd services/customer-service
mvn test
```

---

## 📚 Tecnologias

### Frontend
- ✅ **Angular 18** - Framework web moderno
- ✅ **Bootstrap 5** - UI responsiva
- ✅ **RxJS** - Reactive programming
- ✅ **Reactive Forms** - Formulários robustos

### Backend
- ✅ **Spring Boot 3.3** - Framework Java
- ✅ **PostgreSQL 17** - Banco de dados
- ✅ **Redis 7** - Cache
- ✅ **Kafka** - Event streaming
- ✅ **Arquitetura Hexagonal** - Design patterns

### DevOps
- ✅ **Docker** - Containerização
- ✅ **Docker Compose** - Orquestração local

---

## 🚨 Troubleshooting

### Porta já em uso
```bash
docker-compose down
# Ou mude a porta em docker-compose.yml
```

### Banco de dados não conecta
```bash
docker-compose logs postgres
# Aguarde postgres estar healthy
```

### Frontend não conecta com backend
```bash
# Verificar URLs em environment.ts
cat hexagonal-ecommerce-frontend/src/environments/environment.ts

# Checklist:
# - Backend rodando?
# - CORS configurado?
# - URLs corretas?
```

### Limpar tudo e começar do zero
```bash
docker-compose down -v
docker system prune -a
docker-compose up -d
```

---

## 📖 Leitura Recomendada

1. [Frontend README](./hexagonal-ecommerce-frontend/README.md) - Setup e desenvolvimento local
2. [Product Service README](./services/product-service/README.md) - API de produtos
3. [Customer Service README](./services/customer-service/README.md) - API de clientes
4. [Arquitetura Hexagonal](https://herbertograca.com/2017/09/28/the-software-architecture-chronicles/) - Conceitos

---

## 👨‍💻 Desenvolvedor

**Leonardo De Souza Macedo**

Projeto educacional com foco em:
- ✅ Arquitetura Hexagonal
- ✅ Microserviços
- ✅ Angular Moderno
- ✅ Spring Boot
- ✅ Boas Práticas

---

## 📜 Licença

MIT

---

## 🤝 Contribuindo

Encontrou um bug? Tem uma sugestão?

1. Fork o projeto
2. Crie uma branch (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

---

## ✨ Roadmap

- [ ] Order Service (Serviço de Pedidos)
- [ ] Payment Service (Serviço de Pagamentos)
- [ ] Email Service (Notificações)
- [ ] Admin Dashboard
- [ ] PWA (Progressive Web App)
- [ ] Testes E2E
- [ ] CI/CD Pipeline (GitHub Actions)

---

## 📞 Suporte

💬 Dúvidas? Abra uma issue ou entre em contato!

**Happy Coding! 🚀**
