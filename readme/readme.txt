Aplicativo de Sistema de Vendas

Este projeto foi desenvolvido em Java e utiliza Java JDBC para integrar com bancos de dados. A aplicação trabalha com MongoDB e MySQL. Siga as instruções abaixo para configurá-la.

Tecnologias Utilizadas:
- Java: Linguagem principal do projeto.
- Java JDBC: Biblioteca para conexão e manipulação de bancos de dados.
- MongoDB: Banco de dados NoSQL.
- MySQL: Banco de dados relacional.

Requisitos:
- MongoDB JDBC Driver: Para conectar ao MongoDB via JDBC. Faça o download em: MongoDB JDBC Driver
- MySQL JDBC Driver: Para conectar ao MySQL. Disponível no Maven Central ou em: MySQL JDBC Driver

Configuração do Ambiente:
1. Baixar e Configurar os Drivers JDBC:
    - Faça o download dos drivers mencionados.
    - Inclua os arquivos .jar dos drivers no classpath do seu projeto.

2. Configuração do Banco de Dados MySQL:
    - Crie as tabelas necessárias no banco de dados MySQL.
    - Certifique-se de que o esquema está compatível com a aplicação.

3. Configuração do MongoDB:
    - Certifique-se de que o MongoDB está rodando e acessível pela aplicação.

4. Configuração do Aplicativo:
    - Atualize os arquivos de configuração ou as classes do projeto para incluir os detalhes de conexão do MySQL e MongoDB (URL, usuário e senha).

Login de Administrador:
- Usuário: "admin"
- Senha: "1234"

No painel de administração, você pode:
- Adicionar produtos e clientes
- Editar produtos e clientes
- Remover produtos e clientes
- Consultar produtos e clientes
