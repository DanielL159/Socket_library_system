# Projeto de Livraria com Modelo Sockets

## Descrição do Projeto

Este projeto consiste em uma aplicação de livraria implementada no modelo cliente-servidor utilizando sockets. A aplicação permite que os clientes se conectem ao servidor e realizem operações como listar livros, cadastrar novos livros, devolver livros e alugar livros. O projeto foi desenvolvido com Spring Boot e está estruturado no formato Maven para facilitar a gestão de dependências e o ciclo de vida do projeto. Além disso, a biblioteca `json-simple` foi utilizada para manipulação de dados em formato JSON.

## Funcionalidades

### Cliente
O cliente pode realizar as seguintes operações ao se conectar ao servidor:
- **Listar livros**: Visualizar a lista de todos os livros disponíveis na livraria.
- **Cadastrar livros**: Adicionar novos livros ao acervo da livraria.
- **Devolver livros**: Devolver livros previamente alugados.
- **Alugar livros**: Alugar livros disponíveis na livraria.

### Servidor
O servidor é responsável por gerenciar as requisições dos clientes, realizar as operações solicitadas e manter a integridade do acervo de livros.

## Tecnologias Utilizadas

- **Java**: Linguagem de programação principal utilizada no desenvolvimento.
- **Spring Boot**: Framework utilizado para facilitar a criação e configuração da aplicação.
- **Maven**: Ferramenta de gerenciamento de dependências e construção do projeto.
- **Sockets**: Tecnologia utilizada para comunicação entre cliente e servidor.
- **json-simple**: Biblioteca para manipulação de dados JSON.

## Pré-requisitos

Antes de executar a aplicação, certifique-se de ter as seguintes ferramentas instaladas em seu ambiente:

- **JDK 17** ou superior
- **Maven 3.6** ou superior
- **Spring Boot 2.5** ou superior

## Como Executar

1.Clone o repositório do projeto: Certifique-se de obter o código-fonte do repositório do projeto para seu ambiente local.

2.Importe o projeto para a sua IDE favorita: Utilize uma IDE compatível com Spring Boot e Maven, como Eclipse, IntelliJ IDEA ou Visual Studio Code (de preferência, use o Eclipse).

3.Compile o projeto: Utilize o Maven para compilar o projeto e resolver todas as dependências necessárias.

4.Execute o servidor: Inicie a aplicação do servidor. O servidor ficará em execução, aguardando conexões dos clientes.

5.Execute o cliente: Inicie a aplicação do cliente e siga as instruções fornecidas para listar, cadastrar, devolver ou alugar livros.



## Estrutura do Código

O projeto está organizado em módulos separados para o servidor e o cliente, cada um contendo suas próprias dependências e configurações.

- **Server**: Contém o código do servidor, incluindo a lógica de negócios para gerenciar livros.
- **Client**: Contém o código do cliente, responsável por enviar solicitações ao servidor e exibir os resultados.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests. Por favor, siga as boas práticas de desenvolvimento e mantenha a consistência do código.

---

Obrigado por utilizar a aplicação de Livraria! Esperamos que ela atenda às suas necessidades. Se você tiver alguma dúvida ou sugestão, não hesite em nos contatar.
