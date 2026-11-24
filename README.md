# 🌎 Explorador de Países

![Java](https://img.shields.io/badge/Java-ED8B00?logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-GUI-blue)
![Maven](https://img.shields.io/badge/Maven-build-C71A36?logo=apachemaven&logoColor=white)
![API](https://img.shields.io/badge/API-REST%20Countries-success)

Aplicação desktop desenvolvida em **Java** com **JavaFX** que permite explorar países do mundo, consultar informações geográficas e visualizar suas bandeiras por meio de integração com a **REST Countries API**.

O projeto oferece uma interface gráfica intuitiva, com cartões individuais para cada país, pesquisa por nome e visualização detalhada de informações.

## 📑 Sumário

- [Sobre o projeto](#-sobre-o-projeto)
- [Funcionalidades](#-funcionalidades)
- [Informações disponíveis](#-informações-disponíveis)
- [Tecnologias utilizadas](#️-tecnologias-utilizadas)
- [Estrutura do projeto](#-estrutura-do-projeto)
- [API REST utilizada](#-api-rest-utilizada)
- [Chamadas do cliente à API](#-chamadas-do-cliente-à-api)
- [Como executar o projeto](#-como-executar-o-projeto)
- [Teste operacional](#-teste-operacional)
- [Autores](#-autores)

## 📸 Sobre o projeto

O **Explorador de Países** foi desenvolvido com o objetivo de facilitar a exploração e a consulta de informações sobre países de diferentes regiões do mundo.

Ao iniciar a aplicação, os países são carregados automaticamente por meio de uma API externa e apresentados em uma grade de cartões interativos.

## ✨ Funcionalidades

- 🌍 Listagem de países do mundo.
- 🏳️ Exibição das bandeiras dos países.
- 🔎 Pesquisa de países pelo nome.
- 📋 Visualização de informações detalhadas.
- 🔄 Atualização dos dados diretamente pela API.
- 🌐 Consulta de informações geográficas.
- ⚡ Carregamento assíncrono dos dados, evitando travamentos na interface.

## 📋 Informações disponíveis

Ao selecionar **Ver detalhes** em um país, é possível consultar:

| Informação | Exemplo |
|---|---|
| Nome do país | Brazil |
| Bandeira e código identificador | 🇧🇷 · `BR` |
| Capital | Brasília |
| Continente | Americas |
| Sub-região | South America |
| População | 212.559.409 |
| Área territorial | 8.515.767 km² |
| Moeda | Brazilian real (BRL, R$) |
| Idiomas | Portuguese |

> Os valores acima são ilustrativos; os dados reais são obtidos da API em tempo de execução.

## 🛠️ Tecnologias utilizadas

| Tecnologia | Uso |
|---|---|
| **Java** | Linguagem de programação |
| **JavaFX** | Desenvolvimento da interface gráfica |
| **Maven** | Gerenciamento de dependências e construção do projeto |
| **REST Countries API** | Obtenção das informações dos países |
| **JavaFX `Task`** | Execução de operações assíncronas |

## 🗂️ Estrutura do projeto

| Classe | Pacote | Responsabilidade |
|---|---|---|
| `ExploradorPaisesApp` | `com.mycompany.exploradorpaises` | Interface gráfica (JavaFX) e ponto de entrada da aplicação |
| `CountryService` | `com.ExploradorPaises.service` | Cliente do serviço web: consulta a API e converte a resposta em objetos |
| `Country` | `com.ExploradorPaises.model` | Modelo de dados de um país |

## 🌐 API REST utilizada

O projeto consome a [**REST Countries API**](https://restcountries.com), um serviço web público que fornece dados sobre países no formato JSON.

| Característica | Descrição |
|---|---|
| **URL base** | `https://restcountries.com/v3.1` |
| **Estilo** | REST, orientado a recursos (`/all`, `/name/{nome}`, `/alpha/{código}`, ...) |
| **Método HTTP** | `GET` (API somente leitura) |
| **Formato de resposta** | `application/json` |
| **Autenticação** | **Nenhuma.** Não é necessária chave de API, token ou cadastro |
| **Requisito de rede** | Acesso à internet (HTTPS) |

### Principais endpoints da API (referência)

| Recurso | URL | Descrição |
|---|---|---|
| Todos os países | `/all?fields=...` | Lista todos os países |
| Busca por nome | `/name/{nome}` | Busca por nome comum ou oficial (parcial por padrão) |
| Busca por nome exato | `/name/{nome}?fullText=true` | Exige correspondência exata do nome |
| Busca por código | `/alpha/{código}` | Busca por código ISO 3166-1 (2 ou 3 letras) |
| Busca por região | `/region/{região}` | Filtra por continente/região |
| Busca por capital | `/capital/{capital}` | Filtra pela cidade capital |

> O cliente deste projeto utiliza apenas a listagem completa; veja [Chamadas do cliente à API](#-chamadas-do-cliente-à-api).

### Parâmetro `fields`

O parâmetro de consulta `fields` limita os campos retornados, o que reduz o tamanho da resposta. Na versão 3.1 ele é **obrigatório no endpoint `/all`** (até 10 campos); sem ele, a API pode responder `400 Bad Request`.

```
GET https://restcountries.com/v3.1/all?fields=name,capital,currencies
```

### Códigos de resposta

| Código | Significado |
|---|---|
| `200 OK` | Requisição bem-sucedida |
| `400 Bad Request` | Requisição inválida (por exemplo, `/all` sem `fields`) |
| `404 Not Found` | Nenhum país encontrado para o termo pesquisado |

## 📡 Chamadas do cliente à API

O cliente realiza **uma única chamada** ao serviço web, feita pelo método `CountryService.obterPaises()`. Ela é executada em segundo plano por meio de uma `Task` do JavaFX, para não bloquear a interface.

### Listar todos os países

| Item | Valor |
|---|---|
| **Método** | `GET` |
| **URL do recurso** | `https://restcountries.com/v3.1/all?fields=name,cca2,flags,capital,region,subregion,population,area,currencies,languages` |
| **Autenticação** | Nenhuma |
| **Resposta** | Array JSON com um objeto por país |
| **Quando é chamada** | Ao iniciar a aplicação e ao clicar no botão **Atualizar** |

<!-- CONFIRMAR: ajustar a URL e a lista de campos (fields) conforme CountryService.obterPaises(). -->

**Requisição de exemplo**

```bash
curl "https://restcountries.com/v3.1/all?fields=name,cca2,flags,capital,region,subregion,population,area,currencies,languages"
```

**Representação do recurso (resposta de exemplo, truncada)**

```json
[
  {
    "name": {
      "common": "Brazil",
      "official": "Federative Republic of Brazil"
    },
    "cca2": "BR",
    "capital": ["Brasília"],
    "region": "Americas",
    "subregion": "South America",
    "population": 212559409,
    "area": 8515767,
    "flags": {
      "png": "https://flagcdn.com/w320/br.png",
      "svg": "https://flagcdn.com/br.svg"
    },
    "currencies": {
      "BRL": { "name": "Brazilian real", "symbol": "R$" }
    },
    "languages": { "por": "Portuguese" }
  }
]
```

> Os valores são ilustrativos e podem variar conforme a atualização dos dados da API.

### Comportamento em caso de falha

Se a chamada falhar (por exemplo, sem conexão com a internet), a aplicação exibe a mensagem **"Erro ao carregar os países."** na barra de status e abre um alerta com o título **"Erro de conexão"** e a descrição do erro. A interface continua respondendo.

### Operações realizadas localmente (sem nova chamada à API)

Depois que a lista é carregada, estas funcionalidades usam apenas os dados já em memória:

- **Pesquisa por nome:** filtra a lista carregada pelos países cujo nome *contém* o texto digitado, sem diferenciar maiúsculas de minúsculas. É acionada pelo botão **Pesquisar** ou pela tecla **Enter**. Com o campo vazio, todos os países voltam a ser exibidos.
- **Ver detalhes:** abre uma janela com os dados do país selecionado, a partir do objeto `Country` já carregado.

### Correspondência entre os campos da API e a interface

| Informação exibida | Campo no JSON |
|---|---|
| Nome do país | `name.common` |
| Código identificador | `cca2` |
| Bandeira (imagem e emoji) | `flags.png` / `flags.svg` (URLs) e código do país |
| Capital | `capital[0]` |
| Continente | `region` |
| Sub-região | `subregion` |
| População | `population` |
| Área territorial | `area` (em km²) |
| Moeda | `currencies.{código}.name` e `.symbol` |
| Idiomas | valores de `languages` |

<!-- CONFIRMAR: conferir esta tabela com o parsing feito em CountryService e Country (por exemplo, nome em português ou cca3). -->

## 🚀 Como executar o projeto

### Pré-requisitos

Antes de executar, certifique-se de possuir:

- **JDK 16 ou superior** (o código usa `Stream.toList()`); use a versão configurada no `pom.xml`.
- **Apache Maven**.
- **NetBeans** ou outra IDE com suporte a Maven.
- **Conexão com a internet**, necessária para consultar a API.

### Clonando o repositório

```bash
git clone https://github.com/danyposcidonio/ExploradorPaises.git
cd ExploradorPaises
```

### Abrindo o projeto

1. Abra o NetBeans.
2. Selecione **File → Open Project**.
3. Escolha a pasta do projeto clonado.
4. Aguarde o Maven baixar as dependências.

### Executando

Pelo terminal, na pasta do projeto:

```bash
mvn clean javafx:run
```

Ou execute diretamente pela IDE NetBeans (**Run → Run Project**).

A aplicação é iniciada pela classe `com.mycompany.exploradorpaises.ExploradorPaisesApp`.

## ✅ Teste operacional

Este roteiro verifica que o serviço web está acessível e que o cliente funciona de ponta a ponta.

### Passo 1: verificar o serviço web (sem a aplicação)

Confirme que a API responde com sucesso:

```bash
curl -s -o /dev/null -w "%{http_code}\n" "https://restcountries.com/v3.1/all?fields=name,cca2,flags,capital,region,subregion,population,area,currencies,languages"
```

Resultado esperado: `200`.

> No PowerShell do Windows, use `curl.exe` no lugar de `curl`.

### Passo 2: executar o cliente

Na pasta do projeto:

```bash
mvn clean javafx:run
```

### Passo 3: conferir o comportamento

| # | Ação | Resultado esperado |
|---|---|---|
| 1 | Abrir a aplicação | A janela "World Explorer" abre e mostra **"Carregando países da API..."**; a interface continua responsiva |
| 2 | Aguardar o carregamento | A barra de status exibe **"N países carregados com sucesso!"**, o contador mostra **"Total: N"** e a grade exibe um cartão por país, com bandeira, nome e código |
| 3 | Digitar `bra` no campo de pesquisa e clicar em **Pesquisar** (ou pressionar Enter) | A grade é filtrada e a barra de status mostra **"Resultados encontrados: N"** |
| 4 | Clicar em **Ver detalhes** em um cartão | Abre uma janela com código, capital, continente, sub-região, população, área, moeda e idiomas |
| 5 | Limpar o campo e pesquisar novamente | Todos os países voltam a ser exibidos (**"Exibindo todos os países."**) |
| 6 | Pesquisar um termo inexistente (ex.: `xyzabc`) | Nenhum cartão é exibido e a barra mostra **"Resultados encontrados: 0"** |
| 7 | Clicar em **Atualizar** | O campo de pesquisa é limpo e a lista é recarregada a partir da API |

### Passo 4: teste de falha (opcional)

Desative a conexão com a internet e clique em **Atualizar** (ou abra a aplicação). Resultado esperado: a barra de status mostra **"Erro ao carregar os países."** e abre um alerta **"Erro de conexão"**. A interface continua respondendo.

## 👨‍💻 Autores

Desenvolvido por **Ana Letícia Sandy**, **Daniely Poscidoni** e **Gabriel Alencar**.
