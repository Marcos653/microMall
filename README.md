# MicroMall E-commerce Platform 🛍️

## Visão Geral 🌐

MicroMall é uma plataforma de e-commerce projetada para ser robusta, escalável e eficiente. Utilizando uma arquitetura baseada em microserviços, este projeto abrange diversos serviços individuais que trabalham em conjunto para gerenciar operações de e-commerce, desde autenticação de usuários até gerenciamento de produtos e pedidos.

## Características 🌟

- **Arquitetura de Microserviços**: Cada serviço é independente, garantindo escalabilidade e manutenção facilitada.
- **Eureka Integration**: Registro e descoberta de serviços otimizados com o Eureka Server.
- **Gateway API com Zuul**: Roteamento eficiente de solicitações para os microserviços correspondentes.
- **Integração RabbitMQ**: Comunicação entre serviços facilitada e otimizada com RabbitMQ.
- **Documentação Swagger**: Interface amigável para explorar e testar a API.
- **Contenerização com Docker**: Cada serviço é empacotado em contêineres para implantação simplificada.
- **Automatização com Jenkins**: Implantação automatizada e CI/CD integrado.

## Começando 🚀

### Pré-requisitos

- Java 11 ou superior
- MySQL
- Docker (para contenerização)
- RabbitMQ, Eureka Server, e Zuul (para serviços e roteamento)
