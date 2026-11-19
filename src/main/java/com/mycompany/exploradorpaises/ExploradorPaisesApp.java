
package com.mycompany.exploradorpaises;

import com.ExploradorPaises.model.Country;
import com.ExploradorPaises.service.CountryService;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ExploradorPaisesApp extends Application {

    private CountryService service;

    private ObservableList<Country> paises;

    private FlowPane gradePaises;

    private TextField campoPesquisa;

    private Label mensagem;

    private Label contador;

    private Stage janela;

    @Override
    public void start(Stage stage) {

        janela = stage;

        service = new CountryService();

        paises = FXCollections.observableArrayList();

        // Título principal
        Label titulo = new Label("🌎 EXPLORE OS PAÍSES");

        titulo.setStyle(
                "-fx-font-size: 30px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: #1e3a5f;"
        );

        Label descricao = new Label(
                "Explore o mundo e descubra informações sobre os países!"
        );

        descricao.setStyle(
                "-fx-font-size: 14px;"
                + "-fx-text-fill: #64748b;"
        );

        // Campo de pesquisa
        campoPesquisa = new TextField();

        campoPesquisa.setPromptText("Pesquisar país...");

        campoPesquisa.setPrefWidth(350);

        campoPesquisa.setStyle(
                "-fx-background-radius: 8;"
                + "-fx-padding: 10;"
        );

        campoPesquisa.setOnAction(e -> pesquisarPais());

        // Botão pesquisar
        Button botaoPesquisar = new Button("Pesquisar");

        botaoPesquisar.setStyle(estiloBotao());

        botaoPesquisar.setOnAction(e -> pesquisarPais());

        // Botão atualizar
        Button botaoAtualizar = new Button("Atualizar");

        botaoAtualizar.setStyle(
                "-fx-background-color: #e2e8f0;"
                + "-fx-text-fill: #1e3a5f;"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 8;"
        );

        botaoAtualizar.setOnAction(e -> carregarPaises());

        // Barra de pesquisa
        HBox barraPesquisa = new HBox(
                10,
                campoPesquisa,
                botaoPesquisar,
                botaoAtualizar
        );

        barraPesquisa.setAlignment(Pos.CENTER);

        // Cabeçalho
        VBox cabecalho = new VBox(
                10,
                titulo,
                descricao,
                barraPesquisa
        );

        cabecalho.setAlignment(Pos.CENTER);

        cabecalho.setPadding(new Insets(25));

        // Mensagem e contador
        mensagem = new Label("Preparando aplicação...");

        mensagem.setStyle(
                "-fx-text-fill: #64748b;"
                + "-fx-font-size: 13px;"
        );

        contador = new Label("");

        contador.setStyle(
                "-fx-text-fill: #64748b;"
                + "-fx-font-weight: bold;"
        );

        HBox informacoes = new HBox(
                mensagem,
                new Region(),
                contador
        );

        HBox.setHgrow(
                informacoes.getChildren().get(1),
                Priority.ALWAYS
        );

        informacoes.setAlignment(Pos.CENTER_LEFT);

        // Grade de países
        gradePaises = new FlowPane();

        gradePaises.setHgap(15);

        gradePaises.setVgap(15);

        gradePaises.setPadding(new Insets(15));

        gradePaises.setAlignment(Pos.TOP_CENTER);

        // Área com rolagem
        ScrollPane rolagem = new ScrollPane(gradePaises);

        rolagem.setFitToWidth(true);

        rolagem.setStyle(
                "-fx-background: #f3f6fa;"
                + "-fx-background-color: #f3f6fa;"
        );

        // Conteúdo principal
        VBox conteudo = new VBox(
                10,
                informacoes,
                rolagem
        );

        conteudo.setPadding(new Insets(15));

        VBox.setVgrow(rolagem, Priority.ALWAYS);

        // Layout principal
        BorderPane layout = new BorderPane();

        layout.setTop(cabecalho);

        layout.setCenter(conteudo);

        layout.setStyle(
                "-fx-background-color: #f3f6fa;"
        );

        // Rodapé
        Label rodape = new Label(
                "World Explorer | Desenvolvido com JavaFX e REST Countries API"
        );

        rodape.setStyle(
                "-fx-text-fill: #94a3b8;"
                + "-fx-font-size: 11px;"
        );

        HBox rodapeBox = new HBox(rodape);

        rodapeBox.setAlignment(Pos.CENTER);

        rodapeBox.setPadding(new Insets(12));

        layout.setBottom(rodapeBox);

        // Configuração da janela
        Scene cena = new Scene(layout, 1050, 750);

        stage.setTitle("World Explorer");

        stage.setMinWidth(750);

        stage.setMinHeight(550);

        stage.setScene(cena);

        stage.show();

        // Carregar países automaticamente
        carregarPaises();
    }

    // Estilo dos botões
    private String estiloBotao() {

        return "-fx-background-color: #2563eb;"
                + "-fx-text-fill: white;"
                + "-fx-font-weight: bold;"
                + "-fx-background-radius: 8;"
                + "-fx-padding: 10 18 10 18;";
    }

    // Criar card de cada país
    private VBox criarCard(Country pais) {

        ImageView bandeira = new ImageView();

        bandeira.setFitWidth(115);

        bandeira.setFitHeight(75);

        bandeira.setPreserveRatio(true);

        if (!pais.getFlagUrl().isEmpty()) {

            Image imagem = new Image(
                    pais.getFlagUrl(),
                    115,
                    75,
                    true,
                    true,
                    true
            );

            bandeira.setImage(imagem);
        }

        Label nome = new Label(pais.getName());

        nome.setWrapText(true);

        nome.setAlignment(Pos.CENTER);

        nome.setStyle(
                "-fx-font-size: 15px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: #1e3a5f;"
        );

        Label codigo = new Label(
                "Código: " + pais.getCode()
        );

        codigo.setStyle(
                "-fx-font-size: 12px;"
                + "-fx-text-fill: #64748b;"
        );

        Button detalhes = new Button("Ver detalhes");

        detalhes.setStyle(estiloBotao());

        detalhes.setOnAction(e -> mostrarDetalhes(pais));

        VBox card = new VBox(
                10,
                bandeira,
                nome,
                codigo,
                detalhes
        );

        card.setAlignment(Pos.CENTER);

        card.setPadding(new Insets(15));

        card.setPrefWidth(195);

        card.setMinHeight(205);

        card.setStyle(
                "-fx-background-color: white;"
                + "-fx-background-radius: 12;"
                + "-fx-border-color: #e2e8f0;"
                + "-fx-border-radius: 12;"
        );

        card.setCursor(Cursor.HAND);

        card.setOnMouseEntered(e ->
                card.setStyle(
                        "-fx-background-color: #eff6ff;"
                        + "-fx-background-radius: 12;"
                        + "-fx-border-color: #2563eb;"
                        + "-fx-border-radius: 12;"
                )
        );

        card.setOnMouseExited(e ->
                card.setStyle(
                        "-fx-background-color: white;"
                        + "-fx-background-radius: 12;"
                        + "-fx-border-color: #e2e8f0;"
                        + "-fx-border-radius: 12;"
                )
        );

        return card;
    }

    // Carregar países da API
    private void carregarPaises() {

        mensagem.setText("Carregando países da API...");

        contador.setText("");

        gradePaises.getChildren().clear();

        campoPesquisa.clear();

        Task<List<Country>> tarefa = new Task<>() {

            @Override
            protected List<Country> call() throws Exception {

                return service.obterPaises();
            }
        };

        tarefa.setOnSucceeded(e -> {

            List<Country> resultado = tarefa.getValue();

            paises.setAll(resultado);

            exibirPaises(paises);

            mensagem.setText(
                    paises.size() + " países carregados com sucesso!"
            );

            contador.setText(
                    "Total: " + paises.size()
            );
        });

        tarefa.setOnFailed(e -> {

            Throwable erro = tarefa.getException();

            mensagem.setText("Erro ao carregar os países.");

            exibirAlerta(
                    "Erro de conexão",
                    erro != null
                            ? erro.getMessage()
                            : "Não foi possível acessar a API."
            );
        });

        Thread thread = new Thread(tarefa);

        thread.setDaemon(true);

        thread.start();
    }

    // Exibir os cards na grade
    private void exibirPaises(List<Country> lista) {

        gradePaises.getChildren().clear();

        for (Country pais : lista) {

            gradePaises.getChildren().add(
                    criarCard(pais)
            );
        }

        contador.setText(
                "Total: " + lista.size()
        );
    }

    // Pesquisar países pelo nome
    private void pesquisarPais() {

        String pesquisa = campoPesquisa.getText()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (pesquisa.isEmpty()) {

            exibirPaises(paises);

            mensagem.setText("Exibindo todos os países.");

            return;
        }

        List<Country> filtrados = paises.stream()
                .filter(pais ->
                        pais.getName()
                                .toLowerCase(Locale.ROOT)
                                .contains(pesquisa)
                )
                .toList();

        exibirPaises(filtrados);

        mensagem.setText(
                "Resultados encontrados: " + filtrados.size()
        );
    }

    // Mostrar detalhes do país selecionado
    private void mostrarDetalhes(Country pais) {

        NumberFormat formato = NumberFormat.getInstance(
                new Locale("pt", "BR")
        );

        Alert detalhes = new Alert(Alert.AlertType.INFORMATION);

        detalhes.setTitle("World Explorer");

        detalhes.setHeaderText(
                pais.getFlagEmoji() + " " + pais.getName()
        );

        String informacoes =
                "Código: " + pais.getCode() + "\n\n"
                + "Capital: " + pais.getCapital() + "\n\n"
                + "Continente: " + pais.getRegion() + "\n\n"
                + "Sub-região: " + pais.getSubregion() + "\n\n"
                + "População: "
                + formato.format(pais.getPopulation()) + "\n\n"
                + "Área: "
                + formato.format(pais.getArea()) + " km²\n\n"
                + "Moeda: " + pais.getCurrency() + "\n\n"
                + "Idiomas: " + pais.getLanguages();

        detalhes.setContentText(informacoes);

        detalhes.initOwner(janela);

        detalhes.showAndWait();
    }

    // Exibir mensagens de erro
    private void exibirAlerta(String titulo, String mensagem) {

        Alert alerta = new Alert(Alert.AlertType.ERROR);

        alerta.setTitle("World Explorer");

        alerta.setHeaderText(titulo);

        alerta.setContentText(mensagem);

        alerta.initOwner(janela);

        alerta.showAndWait();
    }

    public static void main(String[] args) {

        launch(args);
    }
}
