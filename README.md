# 🚀 E-Commerce Platform - Backend

Sistema de E-commerce desenvolvido utilizando **Java 21**, **Spring Boot**, **Arquitetura Hexagonal (Ports & Adapters)**, **DDD (Domain Driven Design)**, **Microserviços** e **Apache Kafka** para comunicação assíncrona entre serviços.

O objetivo do projeto é servir como referência para aplicações escaláveis, desacopladas e orientadas a eventos, seguindo boas práticas adotadas por grandes empresas de tecnologia.

---

# 📋 Sumário

- [Quick Start](#-quick-start)
- [Pré-requisitos](#pré-requisitos)
- [Como Rodar o Projeto](#-como-rodar-o-projeto)
- [Visão Geral](#-visão-geral)
- [Arquitetura](#-arquitetura-geral)
- [Arquitetura Hexagonal](#-arquitetura-hexagonal)
- [Microserviços](#-microserviços)
- [Fluxo de Negócio](#-fluxo-completo-de-compra)
- [Comunicação via Kafka](#-comunicação-assíncrona-com-kafka)
- [Stack Tecnológica](#-stack-principal)
- [Estrutura dos Projetos](#-estrutura-padrão-dos-serviços)
- [Banco de Dados](#-estratégia-de-banco-de-dados)
- [Observabilidade](#-observabilidade)
- [Segurança](#-segurança)
- [Infraestrutura](#-infraestrutura)
- [Roadmap](#-roadmap)

---

# ⚡ Quick Start

Execute tudo com um único comando:

```bash
docker-compose up -d
```

Após alguns segundos, verifique os serviços:

- **Frontend**: http://localhost:4200
- **Kafka UI**: http://localhost:8080
- **Product Service**: http://localhost:8081/swagger-ui.html
- **PostgreSQL**: localhost:5432
- **Redis**: localhost:6379

Parar os serviços:

```bash
docker-compose down
```

---

# 🔧 Pré-requisitos

## Opção 1: Com Docker (Recomendado ⭐)

- **Docker** 20.10+ ([Download](https://www.docker.com/products/docker-desktop))
- **Docker Compose** 2.0+ (incluído no Docker Desktop)

### Verificar instalação:

```bash
docker --version
docker-compose --version
```

## Opção 2: Sem Docker (Local)

- **Java 21 JDK** ([Download](https://www.oracle.com/br/java/technologies/downloads/#java21))
- **Maven 3.8+** ([Download](https://maven.apache.org/download.cgi))
- **PostgreSQL 17** ([Download](https://www.postgresql.org/download/))
- **Redis 7+** ([Download](https://redis.io/download))
- **Apache Kafka** ([Download](https://kafka.apache.org/quickstart))
- **Node.js 18+** (para o frontend)

### Verificar instalações:

```bash
java -version
mvn --version
psql --version
redis-cli --version
```

---

# 🚀 Como Rodar o Projeto

## Opção 1: Com Docker Compose (Recomendado)

### Passo 1: Clone o Repositório

```bash
git clone <seu-repositorio>
cd hexagonal-ecommerce-platform
```

### Passo 2: Inicie os Serviços

```bash
docker-compose up -d
```

**Logs em tempo real:**

```bash
docker-compose logs -f
```

**Logs de um serviço específico:**

```bash
docker-compose logs -f product-service
docker-compose logs -f kafka
docker-compose logs -f postgres
```

### Passo 3: Aguarde a Inicialização

Todos os serviços devem estar saudáveis em ~30 segundos.

### Passo 4: Acesse os Serviços

| Serviço | URL |
|---------|-----|
| Frontend | http://localhost:4200 |
| Kafka UI | http://localhost:8080 |
| Product Service (Swagger) | http://localhost:8081/swagger-ui.html |
| PostgreSQL | localhost:5432 |
| Redis CLI | localhost:6379 |

### Parar os Serviços

```bash
docker-compose down
```

**Remover volumes (limpar dados):**

```bash
docker-compose down -v
```

---

## Opção 2: Execução Local (Sem Docker)

### Passo 1: Configure Banco de Dados

#### PostgreSQL

```bash
# Criar database
createdb product_db

# Conectar ao banco
psql -U postgres -d product_db
```

#### Redis

```bash
# Iniciar Redis
redis-server

# Em outro terminal, testar
redis-cli ping
# Deve retornar: PONG
```

#### Apache Kafka

```bash
# Extrair e navegar para a pasta
cd kafka_2.13-3.7.0

# Iniciar Zookeeper (Terminal 1)
bin/zookeeper-server-start.sh config/zookeeper.properties

# Iniciar Kafka (Terminal 2)
bin/kafka-server-start.sh config/server.properties

# Verificar tópicos
bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

### Passo 2: Clone e Configure o Projeto

```bash
git clone <seu-repositorio>
cd hexagonal-ecommerce-platform/services/product-service
```

### Passo 3: Configure Environment Variables

Crie arquivo `.env` na raiz do serviço:

```env
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/product_db
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=sua_senha
KAFKA_BOOTSTRAP_SERVERS=localhost:9092
```

### Passo 4: Compile e Rode

```bash
# Instale dependências e execute
mvn clean install
mvn spring-boot:run
```

**Ou com IDE (IntelliJ/Eclipse):**
- Abra a pasta como projeto Maven
- Clique em Run → Run

### Passo 5: Frontend

Em outro terminal:

```bash
cd hexagonal-ecommerce-frontend
npm install
npm start
```

---

## 🛑 Troubleshooting

### Porta já em uso

```bash
# Encontrar processo na porta 8080
netstat -ano | findstr :8080  # Windows
lsof -i :8080                 # Linux/Mac

# Matar processo
taskkill /PID <PID> /F        # Windows
kill -9 <PID>                 # Linux/Mac
```

### Containers não iniciam

```bash
# Verificar logs
docker-compose logs

# Remover containers e volumes
docker-compose down -v

# Reconstruir
docker-compose up --build
```

### Kafka connection refused

Aguarde 10-15 segundos após iniciar o docker-compose. O Kafka demora um pouco para estar pronto.

```bash
# Verificar status do Kafka
docker-compose exec kafka kafka-topics.sh --bootstrap-server localhost:9092 --list
```

---



A plataforma é composta por múltiplos microserviços independentes responsáveis por domínios específicos do negócio.

## Principais Características

✅ Arquitetura Hexagonal

✅ Domain Driven Design (DDD)

✅ Event Driven Architecture (EDA)

✅ Comunicação síncrona e assíncrona

✅ Apache Kafka

✅ Banco de dados independente por serviço

✅ API Gateway

✅ JWT Authentication

✅ Observabilidade

✅ Escalabilidade horizontal

✅ Resiliência e tolerância a falhas

---

# 🏛 Arquitetura Geral

```text
                    ┌────────────────────┐
                    │      Front-End      │
                    └──────────┬──────────┘
                               │
                               ▼

                    ┌────────────────────┐
                    │    API Gateway      │
                    └──────────┬──────────┘
                               │

 ┌────────────┬────────────┬────────────┬────────────┬────────────┐
 ▼            ▼            ▼            ▼            ▼

Customer    Product      Cart        Order      Payment
Service     Service      Service     Service    Service

 └────────────┴────────────┴────────────┴────────────┘
                              │
                              ▼

                        Apache Kafka

                              │

        ┌─────────────────────┼─────────────────────┐
        ▼                     ▼                     ▼

Inventory Service    Notification Service    Analytics Service
```

---

# 📦 Microserviços

## Customer Service

Responsável pelo gerenciamento de clientes.

### Funcionalidades

- Cadastro
- Login
- Atualização de perfil
- Endereços
- Recuperação de senha

### Banco

```text
PostgreSQL
```

### Eventos

```text
customer-created
customer-updated
customer-deleted
```

---

## Product Service

Responsável pelo catálogo de produtos.

### Funcionalidades

- Cadastro de produtos
- Categorias
- Marcas
- Imagens
- Preços

### Banco

```text
PostgreSQL
```

### Eventos

```text
product-created
product-updated
product-price-changed
```

---

## Cart Service

Responsável pelo carrinho de compras.

### Funcionalidades

- Adicionar item
- Atualizar quantidade
- Remover item
- Aplicação de cupons

### Banco

```text
Redis
```

### Eventos

```text
cart-created
cart-item-added
cart-item-removed
```

---

## Order Service

Responsável pela gestão dos pedidos.

### Funcionalidades

- Criar pedido
- Cancelar pedido
- Consultar pedido
- Alteração de status

### Banco

```text
PostgreSQL
```

### Eventos

```text
order-created
order-confirmed
order-cancelled
order-finished
```

---

## Inventory Service

Responsável pelo controle de estoque.

### Funcionalidades

- Reserva de estoque
- Baixa de estoque
- Reposição

### Banco

```text
PostgreSQL
```

### Eventos

```text
stock-reserved
stock-updated
stock-released
```

---

## Payment Service

Responsável pelo processamento financeiro.

### Funcionalidades

- PIX
- Cartão
- Boleto
- Estorno

### Banco

```text
PostgreSQL
```

### Eventos

```text
payment-created
payment-approved
payment-refused
payment-refunded
```

---

## Notification Service

Responsável pelas notificações.

### Funcionalidades

- E-mail
- SMS
- Push Notification

### Banco

```text
PostgreSQL
```

### Eventos Consumidos

```text
order-confirmed
payment-approved
customer-created
```

---

## Analytics Service

Responsável pelo processamento de métricas e indicadores.

### Funcionalidades

- Dashboard
- KPIs
- Relatórios
- Indicadores de vendas

---

# 🧩 Arquitetura Hexagonal

Todos os microserviços seguem o padrão Ports and Adapters.

```text
                ┌────────────────────┐
                │      REST API      │
                └─────────┬──────────┘

                          ▼

                ┌────────────────────┐
                │      Use Cases      │
                └─────────┬──────────┘

                          ▼

                ┌────────────────────┐
                │      Domain         │
                └─────────┬──────────┘

                          ▼

      ┌───────────────────┼───────────────────┐

      ▼                   ▼                   ▼

 Database            Kafka              External APIs
 Adapter             Adapter              Adapter
```

---

# 📁 Estrutura Padrão dos Serviços

```text
src/main/java
└── br.com.ecommerce.order
    │
    ├── config
    │   ├── kafka
    │   ├── database
    │   ├── security
    │   └── swagger
    │
    ├── application
    │   ├── dto
    │   ├── usecase
    │   └── service
    │
    ├── domain
    │   ├── entity
    │   ├── event
    │   ├── valueobject
    │   ├── repository
    │   └── exception
    │
    ├── adapters
    │   ├── inbound
    │   │   ├── rest
    │   │   └── kafka
    │   │
    │   └── outbound
    │       ├── persistence
    │       ├── kafka
    │       └── integration
    │
    ├── mapper
    │
    ├── exception
    │
    ├── util
    │
    └── OrderApplication
```

---

# 📨 Comunicação Assíncrona com Kafka

## Fluxo de Criação de Pedido

```text
Cliente

  │
  ▼

Order Service

  │
  ▼

order-created

  │
  ├────────► Inventory Service
  │
  ├────────► Payment Service
  │
  └────────► Analytics Service
```

---

## Fluxo de Aprovação de Pagamento

```text
Payment Service

  │
  ▼

payment-approved

  │
  ▼

Order Service

  │
  ▼

order-confirmed

  │
  ├────────► Notification Service
  │
  ├────────► Analytics Service
  │
  └────────► Inventory Service
```

---

# 📚 Kafka Topics

```text
customer-created
customer-updated

product-created
product-updated

cart-created

order-created
order-confirmed
order-cancelled

payment-created
payment-approved
payment-refused
payment-refunded

stock-reserved
stock-released
stock-updated

notification-sent
```

---

# 🔐 Segurança

Tecnologias adotadas:

```text
Spring Security
JWT
OAuth2
API Gateway Authentication
Role-Based Access Control (RBAC)
```

Perfis de acesso:

```text
ROLE_CUSTOMER
ROLE_ADMIN
ROLE_SELLER
ROLE_SUPPORT
```

---

# 🗄 Estratégia de Banco de Dados

O projeto adota inicialmente uma abordagem simplificada utilizando apenas PostgreSQL e Redis.

## Motivação

- Menor complexidade operacional
- Maior consistência transacional
- Facilidade de manutenção
- Menor custo de infraestrutura

## Bancos Utilizados

| Serviço | Banco |
|----------|----------|
| Customer | PostgreSQL |
| Product | PostgreSQL |
| Order | PostgreSQL |
| Payment | PostgreSQL |
| Inventory | PostgreSQL |
| Notification | PostgreSQL |
| Cart | Redis |

### Evolução Futura

Caso o catálogo de produtos se torne muito dinâmico e possua atributos altamente variáveis, o Product Service poderá migrar para MongoDB seguindo a estratégia de Polyglot Persistence.

---

# 📊 Observabilidade

## Logs

```text
ELK Stack
```

## Métricas

```text
Prometheus
Grafana
```

## Distributed Tracing

```text
OpenTelemetry
Zipkin
```

---

# 🐳 Infraestrutura

Containers executados via Docker Compose.

```text
PostgreSQL
Redis
Apache Kafka
Kafka UI
Prometheus
Grafana
ELK Stack
Zipkin
```

---

# 🔄 Fluxo Completo de Compra

```text
1. Cliente realiza login

2. Cliente adiciona itens ao carrinho

3. Cart Service salva informações no Redis

4. Cliente finaliza compra

5. Order Service cria pedido

6. Kafka publica order-created

7. Inventory Service reserva estoque

8. Payment Service processa pagamento

9. Kafka publica payment-approved

10. Order Service confirma pedido

11. Kafka publica order-confirmed

12. Notification Service envia e-mail

13. Analytics Service registra a venda

14. Pedido concluído
```

---

# 🏗 Padrões Arquiteturais Utilizados

## Arquitetura

- Hexagonal Architecture
- Clean Architecture
- Domain Driven Design
- Event Driven Architecture
- Microservices Architecture

## Design Patterns

- Factory Pattern
- Strategy Pattern
- Builder Pattern
- Adapter Pattern
- Observer Pattern
- Circuit Breaker Pattern

## Padrões Distribuídos

- Saga Pattern
- Outbox Pattern
- Retry Pattern
- Dead Letter Queue (DLQ)
- Idempotent Consumer

---

# 🚀 Roadmap

## Fase 1

- [x] Customer Service
- [x] Product Service
- [x] Cart Service
- [x] Order Service
- [x] Payment Service
- [x] Kafka Integration

## Fase 2

- [ ] Inventory Service
- [ ] Notification Service
- [ ] Prometheus
- [ ] Grafana
- [ ] Zipkin

## Fase 3

- [ ] Elasticsearch
- [ ] Recommendation Engine
- [ ] Marketplace
- [ ] Cashback
- [ ] Loyalty Program

---

# 👨‍💻 Autor

**Leonardo De Souza Macedo**

Projeto criado com foco em estudo de arquiteturas modernas, microsserviços, sistemas distribuídos e boas práticas de engenharia de software.

## Stack Principal

```text
Java 21
Spring Boot 3
Spring Security
Spring Data JPA
PostgreSQL
Redis
Apache Kafka
Docker
OpenAPI
MapStruct
JUnit 5
Testcontainers
```

---

## 📖 Referências

- Hexagonal Architecture (Alistair Cockburn)
- Domain Driven Design (Eric Evans)
- Clean Architecture (Robert C. Martin)
- Building Event-Driven Microservices (Adam Bellemare)
- Microservices Patterns (Chris Richardson)

---

## 🤝 Contribuindo

Contribuições são bem-vindas! Sinta-se à vontade para:

1. Fazer fork do projeto
2. Criar uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abrir um Pull Request

---

## 📧 Suporte

Para dúvidas, sugestões ou issues, abra uma issue no repositório ou entre em contato.

---

## 📄 Licença

MIT

**Desenvolvido com ❤️ por Leonardo De Souza Macedo**