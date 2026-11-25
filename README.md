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

- 🌍 Listagem de países (até 100 por consulta).
- 🏳️ Exibição das bandeiras dos países.
- 🔎 Pesquisa de países pelo nome.
- 📋 Visualização de informações detalhadas.
- 🔄 Atualização dos dados diretamente pela API.
- 🌐 Consulta de informações geográficas.
- ⚡ Carregamento assíncrono dos dados, evitando travamentos na interface.

<!-- CONFIRMAR: se o contador "Total: N" mostrar 100, a lista está limitada pelo parâmetro limit=100. Se mostrar outro valor, ajustar o texto "até 100 por consulta". -->

## 📋 Informações disponíveis

Ao selecionar **Ver detalhes** em um país, é possível consultar:

| Informação | Exemplo |
|---|---|
| Nome do país e bandeira (emoji) | 🇧🇷 Brazil |
| Código identificador | `BR` |
| Capital | Brasília |
| Continente | Americas |
| Sub-região | South America |
| População | 212.559.409 |
| Área territorial | 8.515.767 km² |
| Moeda | Brazilian real (BRL) |
| Idiomas | Portuguese |

> Os valores acima são ilustrativos; os dados reais são obtidos da API em tempo de execução.

Observações sobre a exibição:

- Quando um país tem mais de uma capital ou moeda, apenas a **primeira** é exibida. Os **idiomas** são exibidos todos, separados por vírgula.
- Campos ausentes na resposta aparecem como **"Não informado(a)"**. Para a área, a ausência do dado aparece como `0 km²`.

## 🛠️ Tecnologias utilizadas

| Tecnologia | Uso |
|---|---|
| **Java** | Linguagem de programação |
| **JavaFX** | Desenvolvimento da interface gráfica |
| **Maven** | Gerenciamento de dependências e construção do projeto |
| **REST Countries API** | Obtenção das informações dos países |
| **`java.net.http.HttpClient`** | Realização das requisições HTTP |
| **Gson** | Conversão do JSON da resposta em objetos Java |
| **JavaFX `Task`** | Execução de operações assíncronas |

## 🗂️ Estrutura do projeto

| Classe | Pacote | Responsabilidade |
|---|---|---|
| `ExploradorPaisesApp` | `com.mycompany.exploradorpaises` | Interface gráfica (JavaFX) e ponto de entrada da aplicação |
| `CountryService` | `com.ExploradorPaises.service` | Cliente do serviço web: consulta a API e converte a resposta em objetos |
| `Country` | `com.ExploradorPaises.model` | Modelo de dados de um país |
| `CountryResponse` | `com.ExploradorPaises.model` | Modelo do envelope da resposta (`data.objects`) |

## 🌐 API REST utilizada

O projeto consome a **REST Countries API** (versão v5), um serviço web que fornece dados sobre países no formato JSON.

| Característica | Descrição |
|---|---|
| **URL base** | `https://api.restcountries.com/countries/v5` |
| **Estilo** | REST, orientado a recursos |
| **Método HTTP** | `GET` (somente leitura) |
| **Formato de resposta** | `application/json` |
| **Autenticação** | **Obrigatória.** Chave de API enviada no header `Authorization: Bearer <chave>` |
| **Requisito de rede** | Acesso à internet (HTTPS) |

### Parâmetros de consulta utilizados

| Parâmetro | Valor | Descrição |
|---|---|---|
| `limit` | `100` | Quantidade máxima de países retornados |
| `response_fields` | `names.common`, `codes.alpha_2`, `flag.emoji`, `flag.url_png`, `region`, `subregion`, `capitals.name`, `population`, `area.kilometers`, `currencies.code`, `currencies.name`, `languages.name` | Limita os campos retornados (notação com ponto), reduzindo o tamanho da resposta |

### Códigos de resposta

| Código | Tratamento no cliente |
|---|---|
| `200 OK` | Requisição bem-sucedida; o corpo é convertido em objetos `Country` |
| Qualquer outro | O cliente lança um erro com o código HTTP e o corpo da resposta (por exemplo, chave ausente ou inválida), exibido no alerta **"Erro de conexão"** |

## 📡 Chamadas do cliente à API

O cliente realiza **uma única chamada** ao serviço web, feita pelo método `CountryService.obterPaises()` (que usa `listarPaises()` para a requisição HTTP). Ela é executada em segundo plano por meio de uma `Task` do JavaFX, para não bloquear a interface.

### Listar países

| Item | Valor |
|---|---|
| **Método** | `GET` |
| **URL do recurso** | `https://api.restcountries.com/countries/v5?limit=100&response_fields=names.common,codes.alpha_2,flag.emoji,flag.url_png,region,subregion,capitals.name,population,area.kilometers,currencies.code,currencies.name,languages.name` |
| **Autenticação** | Header `Authorization: Bearer <chave>` (chave lida da variável de ambiente `RESTCOUNTRIES_API_KEY`) |
| **Resposta** | Objeto JSON com a lista de países em `data.objects` |
| **Quando é chamada** | Ao iniciar a aplicação e ao clicar no botão **Atualizar** |

**Requisição de exemplo**

```bash
curl -H "Authorization: Bearer $RESTCOUNTRIES_API_KEY" \
  "https://api.restcountries.com/countries/v5?limit=2&response_fields=names.common,codes.alpha_2,flag.emoji,flag.url_png,region,subregion,capitals.name,population,area.kilometers,currencies.code,currencies.name,languages.name"
```

**Representação do recurso (resposta de exemplo, truncada)**

```json
{
  "data": {
    "objects": [
      {
        "names": { "common": "Brazil" },
        "codes": { "alpha_2": "BR" },
        "flag": {
          "emoji": "🇧🇷",
          "url_png": "https://..."
        },
        "region": "Americas",
        "subregion": "South America",
        "capitals": [ { "name": "Brasília" } ],
        "population": 212559409,
        "area": { "kilometers": 8515767 },
        "currencies": [ { "code": "BRL", "name": "Brazilian real" } ],
        "languages": [ { "name": "Portuguese" } ]
      }
    ]
  }
}
```

<!-- CONFIRMAR: este JSON foi montado a partir das classes Country e CountryResponse. Substituir por uma resposta real da API (curl acima, com limit=1) e conferir nomes e tipos dos campos. -->

> Os valores são ilustrativos e podem variar conforme a atualização dos dados da API.

### Comportamento em caso de falha

Se a chamada falhar (por exemplo, sem conexão com a internet, ou chave de API ausente ou inválida), a aplicação exibe a mensagem **"Erro ao carregar os países."** na barra de status e abre um alerta com o título **"Erro de conexão"** e a descrição do erro. A interface continua respondendo.

### Operações realizadas localmente (sem nova chamada à API)

Depois que a lista é carregada, estas funcionalidades usam apenas os dados já em memória:

- **Pesquisa por nome:** filtra a lista carregada pelos países cujo nome *contém* o texto digitado, sem diferenciar maiúsculas de minúsculas. É acionada pelo botão **Pesquisar** ou pela tecla **Enter**. Com o campo vazio, todos os países voltam a ser exibidos.
- **Ver detalhes:** abre uma janela com os dados do país selecionado, a partir do objeto `Country` já carregado.

### Correspondência entre os campos da API e a interface

| Informação exibida | Campo no JSON | Observação |
|---|---|---|
| Nome do país | `names.common` | Vazio se ausente |
| Código identificador | `codes.alpha_2` | Vazio se ausente |
| Bandeira (imagem no cartão) | `flag.url_png` | URL do PNG |
| Bandeira (emoji na janela de detalhes) | `flag.emoji` | Fornecido pela API |
| Capital | `capitals[0].name` | Apenas a primeira; "Não informada" se ausente |
| Continente | `region` | "Não informado" se ausente |
| Sub-região | `subregion` | "Não informado" se ausente |
| População | `population` | |
| Área territorial | `area.kilometers` | Exibe `0` se ausente |
| Moeda | `currencies[0].name` e `currencies[0].code` | Apenas a primeira, no formato `Nome (CÓDIGO)`; "Não informada" se ausente |
| Idiomas | `languages[].name` | Todos, separados por vírgula; "Não informado" se ausente |

## 🚀 Como executar o projeto

### Pré-requisitos

Antes de executar, certifique-se de possuir:

- **JDK 16 ou superior** (o código usa `Stream.toList()`); use a versão configurada no `pom.xml`.
- **Apache Maven**.
- **NetBeans** ou outra IDE com suporte a Maven.
- **Conexão com a internet**, necessária para consultar a API.
- **Chave de API da REST Countries**, configurada na variável de ambiente `RESTCOUNTRIES_API_KEY` (veja abaixo).

### Configurando a chave de API

A chave **não deve ser colocada no código nem enviada ao repositório**. Defina-a como variável de ambiente antes de executar:

```bash
# Linux/macOS
export RESTCOUNTRIES_API_KEY="sua_chave"
```

```powershell
# Windows (PowerShell)
$env:RESTCOUNTRIES_API_KEY="sua_chave"
```

Se você executa pelo NetBeans, defina a variável no sistema operacional antes de abrir a IDE, ou nas propriedades de execução do projeto. Sem a chave, a aplicação exibe o alerta **"Erro de conexão"**.

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

Pelo terminal, na pasta do projeto (com a variável `RESTCOUNTRIES_API_KEY` já definida):

```bash
mvn clean javafx:run
```

Ou execute diretamente pela IDE NetBeans (**Run → Run Project**).

A aplicação é iniciada pela classe `com.mycompany.exploradorpaises.ExploradorPaisesApp`.

## ✅ Teste operacional

Este roteiro verifica que o serviço web está acessível e que o cliente funciona de ponta a ponta.

### Passo 1: verificar o serviço web (sem a aplicação)

Confirme que a API responde com sucesso (com a variável `RESTCOUNTRIES_API_KEY` definida):

```bash
curl -s -o /dev/null -w "%{http_code}\n" \
  -H "Authorization: Bearer $RESTCOUNTRIES_API_KEY" \
  "https://api.restcountries.com/countries/v5?limit=100&response_fields=names.common,codes.alpha_2,flag.emoji,flag.url_png,region,subregion,capitals.name,population,area.kilometers,currencies.code,currencies.name,languages.name"
```

Resultado esperado: `200`.

> No PowerShell do Windows, use `curl.exe` no lugar de `curl` e `$env:RESTCOUNTRIES_API_KEY` no lugar de `$RESTCOUNTRIES_API_KEY`.

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

Faça um dos testes abaixo:

- Desative a conexão com a internet e clique em **Atualizar** (ou abra a aplicação).
- Execute a aplicação com a variável `RESTCOUNTRIES_API_KEY` ausente ou com um valor inválido.

Resultado esperado: a barra de status mostra **"Erro ao carregar os países."** e abre um alerta **"Erro de conexão"**. A interface continua respondendo.

## 👨‍💻 Autores

Desenvolvido por **Ana Letícia Sandy**, **Daniely Poscidonio** e **Gabriel Alencar**.
