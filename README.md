# API RESTFul WebService - Gestão Contas Pagar (Cálculo Multa/Juros)
###### Copyright © 2021 DELIVER IT.
Projeto em Spring Boot de uma construção API RESTFul voltado a atender o desafio da [DELIVER IT](http://deliverit.com.br/).

Uma solução criada, utilizando a tecnologia Java em formato de API REST. Voltada para atender as demandas de sessões de pagamento de contas, desde o processo de criação da conta a pagar e a visualização destas. Onde todos os serviços devem trabalhar com JSON nas suas chamadas e retornos.

#### Visão Geral

A aplicaçao tem como objetivo disponibilizar endpoints para consulta de informações e operações a respeito de:
- Inclusão de conta a pagar, onde podem ser fornecidas dados, tais como: ```nome, valor original, data de vencimento, data de pagamento```. De acordo com os requisitos solicitados, todos os campos são obrigatórios.

    - ```Inclusão de uma nova conta a pagar;```
    - ```Listagem das contas cadastradas.```

#### Stack do projeto e decisões
- Escrito em Java 8;
    - ```Uma das linguagens mais utilizadas no mundo, conhecida por ser robusta e facil de escalar;```

- Utilizando as facilidades e recursos framework Spring;
    - ```Framework com uma comunidade muito grande, facilita a integração com diversos outros serviços, tornando o desenvolvimento muito mais rápido;```
    - ```A programação web baseada em API Rest, permite usufruir melhor do poder de processamento disponivel, alem de ter por principios ser Resiliente, Elástica, Responsiva```

- Lombok e MapStruct na classe para evitar o boilerplate do Java;
- Framework Spring Boot integrado com PostgreSQL para garantir a persistência dos dados. Facilitando as operações CRUD (aumentando o nivel de desempenho e escalabilidade);
- Boas práticas de programação, utilizando Design Patterns (Builder, Factory, Strategy);
- Testes unitários (junit, mockito, webclient test);
- Maven como gerenciador de dependências

- Banco de dados PostgreSQL;
    - ```Banco de dados relacional.```

- Docker utilizando o compose;<br><br>

#### Instruções inicialização e execução - (aplicação e database)
###### Utilizando docker-compose com MongoDB:
Executar os comandos: <br><br>
```mvn clean install```<br>
```docker-compose build```<br>
```docker-compose up```<br><br>
Logo após, inicializará a aplicação ```instant-account-interest-calculator```, com uma instância do PostgreSQL dockerizada (nesse momento será criado um schema denominado ```account_manager``` no banco de dados e às respectivas tabelas).
Com a finalidade de gerir, registrar e efetuar as operações relacionadas as contas a pagar.
<br>
###### Utilizando diretamente o jar:
```mvn clean install```<br>
```java -jar target/instant-account-interest-calculator-1.0.jar```<br><br>

#### Endpoints:

Utilizando a ferramenta de documentação de API ```Swagger```, pode-se visualizar todos os endpoints disponíveis. Basta acessar a documentação da API via [Swagger](http://localhost:8095/swagger-ui.html#/).
<br><br> Logo após inicialização da aplicação. De sorte que, segue a lista de alguns endpoints para conhecimento:

- Retornar uma lista completa de contas a pagar registradas:
    - `http://localhost:8080/v1/accounts`

- Retornar uma única conta a pagar, a partir de um identificador único:
    - `http://localhost:8080/v1/accounts/{id}`

Entre outros, aos quais podem ser identificados no endereço fornecido pelo [Swagger](http://localhost:8095/swagger-ui.html#/).
<br><br>


#### Autor e mantenedor do projeto
Daniel Santos Gonçalves - Bachelor in Information Systems, Federal Institute of Maranhão - IFMA / Software Developer Fullstack.
- [GitHub](https://github.com/NecoDan)

- [Linkedin](https://www.linkedin.com/in/daniel-santos-bb072321)

- [Twiter](https://twitter.com/necodaniel)
