# 🚀 E-Commerce Platform

Sistema de E-commerce desenvolvido utilizando **Java 21**, **Spring Boot**, **Arquitetura Hexagonal (Ports & Adapters)**, **DDD (Domain Driven Design)**, **Microserviços** e **Apache Kafka** para comunicação assíncrona entre serviços.

O objetivo do projeto é servir como referência para aplicações escaláveis, desacopladas e orientadas a eventos, seguindo boas práticas adotadas por grandes empresas de tecnologia.

---

# 📋 Sumário

- Visão Geral
- Arquitetura
- Arquitetura Hexagonal
- Microserviços
- Fluxo de Negócio
- Comunicação via Kafka
- Stack Tecnológica
- Estrutura dos Projetos
- Banco de Dados
- Observabilidade
- Segurança
- Infraestrutura
- Roadmap

---

# 🎯 Visão Geral

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