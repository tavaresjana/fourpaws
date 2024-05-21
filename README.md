# Projeto Integrador - Senac

Projeto criado para a disciplina de Projeto Integrador do Senac. O objetivo é o desenvolvimento de sistemas orientados a dispositivos móveis e baseados na web.

## Integrantes

- Carlos Henrique de Castro Caamano
- Janaina Conceicao Tavares da Silva
- Juliano Lopes dos Santos
- Matheus Mauricio
- Ricardo Pereira Rodrigues
- Sebastião Barbosa Pereira Junior

## Tecnologias Utilizadas
- Kotlin
- SQLite
- Figma

## Link para o Projeto no Figma
Você pode visualizar o design do projeto no Figma através deste [link](https://encr.pw/4pawssenac).

## Arquitetura do Projeto

Este projeto segue a arquitetura **MVC (Model-View-Controller)**. Abaixo está uma descrição das principais camadas do projeto:

### Model (Modelo)
Gerencia os dados e a lógica de negócios da aplicação. Esta camada inclui classes que definem a estrutura dos dados (por exemplo, `AgendamentoModel`).

### View (Visão)
Responsável pela interface do usuário e pela apresentação dos dados. Inclui layouts XML e recursos gráficos, localizados na pasta `res` (como `layout` e `drawable`).

### Controller (Controlador)
Atua como intermediário entre a View e o Model. Processa a entrada do usuário, atualiza o Model e informa a View sobre as mudanças. Em aplicações Android, atividades e fragmentos muitas vezes desempenham o papel de controladores.

### Adapter
Facilita a ligação entre o modelo de dados e a View, especialmente em componentes como `RecyclerView`. Os adapters ajudam a conectar os dados do Model à View de maneira eficiente.

### Database (Banco de Dados)
Responsável pelo armazenamento e recuperação dos dados persistentes. Inclui classes e componentes que lidam com operações de banco de dados.


