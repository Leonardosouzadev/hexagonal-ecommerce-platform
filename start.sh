#!/bin/bash

# 🚀 Hexagonal E-Commerce - Quick Start

echo "======================================"
echo "🚀 Iniciando E-Commerce Platform"
echo "======================================"
echo ""

# Verificar Docker
if ! command -v docker &> /dev/null; then
    echo "❌ Docker não encontrado! Instale em: https://www.docker.com"
    exit 1
fi

echo "✅ Docker encontrado"

# Parar containers anteriores
echo "🛑 Parando containers anteriores..."
if docker compose version >/dev/null 2>&1; then
    docker compose down 2>/dev/null
else
    docker-compose down 2>/dev/null
fi

# Build e iniciar
echo "🔨 Compilando e iniciando serviços..."
if docker compose version >/dev/null 2>&1; then
    docker compose up -d --build
else
    docker-compose up -d --build
fi

# Aguardar inicialização
echo "⏳ Aguardando serviços iniciarem (1-2 minutos)..."
sleep 10

# Mostrar status
echo ""
echo "======================================"
echo "✅ Plataforma iniciada!"
echo "======================================"
echo ""
echo "📱 Aplicações disponíveis:"
echo ""
echo "  🌐 Frontend:     http://localhost"
echo "  📊 Kafka UI:     http://localhost:8080"
echo "  📦 Produtos:     http://localhost:8081"
echo "  👤 Clientes:     http://localhost:8082"
echo "  🗄️  Banco:       localhost:5432"
echo ""
echo "======================================"
echo ""
echo "📚 Para ver logs:"
echo "  docker compose logs -f"
echo ""
echo "🛑 Para parar:"
echo "  docker compose down"
echo ""
echo "📖 Mais informações: cat README.md"
echo ""
