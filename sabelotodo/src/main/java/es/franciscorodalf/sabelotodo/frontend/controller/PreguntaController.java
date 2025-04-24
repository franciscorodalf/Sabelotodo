package es.franciscorodalf.sabelotodo.frontend.controller;

import es.franciscorodalf.sabelotodo.backend.dao.CategoriaDAO;
import es.franciscorodalf.sabelotodo.backend.dao.PartidaDAO;
import es.franciscorodalf.sabelotodo.backend.dao.PreguntaDAO;
import es.franciscorodalf.sabelotodo.backend.model.Categoria;
import es.franciscorodalf.sabelotodo.backend.model.Pregunta;
import es.franciscorodalf.sabelotodo.backend.model.Usuario;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PreguntaController {

    @FXML
    private Label lblCategoria;
    @FXML
    private Label lblPregunta;
    @FXML
    private Label lblResultado;
    @FXML
    private Label lblTiempo;

    @FXML
    private Button btnA;
    @FXML
    private Button btnB;
    @FXML
    private Button btnC;
    @FXML
    private Button btnD;
    @FXML
    private Button btn5050;
    @FXML
    private Button btnTiempoExtra;

    private Usuario usuario;
    private Categoria categoria;
    private List<Categoria> categoriasRestantes;
    private Pregunta pregunta;

    private Timeline timeline;
    private int tiempoRestante = 15;
    private boolean comodin5050Usado = false;
    private boolean comodinTiempoUsado = false;

    @FXML
    public void initialize() {
        iniciarTemporizador();
    }

    /**
     * Establece el usuario y la categoría seleccionada.
     * Carga una pregunta aleatoria de la categoría seleccionada.
     *
     * @param usuario             El usuario actual.
     * @param categoria           La categoría seleccionada.
     * @param categoriasRestantes Las categorías restantes para el juego.
     */
    public void setUsuarioYCategoria(Usuario usuario, Categoria categoria, List<Categoria> categoriasRestantes) {
        this.usuario = usuario;
        this.categoria = categoria;
        this.categoriasRestantes = categoriasRestantes;
        cargarPregunta();
    }

    /**
     * Carga una pregunta aleatoria de la categoría seleccionada.
     * Si no se puede cargar la pregunta, desactiva los botones de respuesta.
     */
    private void cargarPregunta() {
        lblResultado.setVisible(false);

        PreguntaDAO dao = new PreguntaDAO();
        this.pregunta = dao.obtenerPreguntaAleatoriaPorCategoria(categoria.getNombre());

        if (pregunta == null) {
            lblPregunta.setText("No se pudo cargar la pregunta.");
            desactivarBotones();
            return;
        }

        lblCategoria.setText("Categoría: " + categoria.getNombre());
        lblPregunta.setText(pregunta.getPregunta());

        List<String> opciones = Arrays.asList(
                pregunta.getOpcionA(),
                pregunta.getOpcionB(),
                pregunta.getOpcionC(),
                pregunta.getOpcionD());

        Collections.shuffle(opciones);

        btnA.setText(opciones.get(0));
        btnB.setText(opciones.get(1));
        btnC.setText(opciones.get(2));
        btnD.setText(opciones.get(3));
    }

    /**
     * Maneja la acción de respuesta a la pregunta.
     * Desactiva los botones de respuesta y verifica si la respuesta es correcta.
     * Si es correcta, muestra un mensaje de éxito y avanza a la siguiente
     * categoría.
     * Si es incorrecta, muestra un mensaje de error y vuelve al menú principal.
     *
     * @param event El evento generado al hacer clic en uno de los botones de
     *              respuesta.
     */
    @FXML
    private void handleRespuesta(ActionEvent event) {
        timeline.stop(); // Detener el temporizador cuando responda
        Button boton = (Button) event.getSource();
        String respuesta = boton.getText();

        desactivarBotones();

        String respuestaCorrecta = "";

        switch (pregunta.getRespuestaCorrecta()) {
            case "A":
                respuestaCorrecta = pregunta.getOpcionA();
                break;
            case "B":
                respuestaCorrecta = pregunta.getOpcionB();
                break;
            case "C":
                respuestaCorrecta = pregunta.getOpcionC();
                break;
            case "D":
                respuestaCorrecta = pregunta.getOpcionD();
                break;
        }

        if (respuesta.trim().equalsIgnoreCase(respuestaCorrecta.trim())) {
            Sonido.reproducirSonidoCorrecto();
            lblResultado.setText("✅ ¡Correcto!");
            lblResultado.setStyle("-fx-text-fill: green;");
            lblResultado.setVisible(true);

            categoriasRestantes.removeIf(cat -> cat.getId() == categoria.getId());

            if (categoriasRestantes.isEmpty()) {
                lblResultado.setText("🎉 ¡Felicidades, has completado todas las categorías!");
                int totalCategorias = new CategoriaDAO().obtenerTodas().size();

                new PartidaDAO().registrarPartida(usuario.getId(), 100, totalCategorias);

                PauseTransition delay = new PauseTransition(Duration.seconds(3));
                delay.setOnFinished(e -> volverAlMenu());
                delay.play();
                return;
            }

            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> volverARuleta());
            delay.play();

        } else {
            handleRespuestaIncorrecta();
        }
    }

    private void handleRespuestaIncorrecta() {
        Sonido.reproducirSonidoFallo();
        lblResultado.setText("❌ Respuesta incorrecta. La correcta era: " + obtenerRespuestaCorrecta());
        lblResultado.setStyle("-fx-text-fill: red;");
        lblResultado.setVisible(true);

        new PartidaDAO().registrarPartida(usuario.getId(), 0, 0);

        PauseTransition delay = new PauseTransition(Duration.seconds(2.5));
        delay.setOnFinished(e -> volverAlMenu());
        delay.play();
    }

    private void iniciarTemporizador() {
        timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), event -> {
                tiempoRestante--;
                lblTiempo.setText(String.format("Tiempo: %ds", tiempoRestante));
                
                if (tiempoRestante <= 0) {
                    timeline.stop();
                    handleTiempoAgotado();
                }
            })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void handleTiempoAgotado() {
        desactivarBotones();
        lblResultado.setText("❌ ¡Se acabó el tiempo!");
        lblResultado.setVisible(true);
        
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> volverAlMenu());
        delay.play();
    }

    @FXML
    private void handle5050() {
        if (comodin5050Usado) return;
        
        comodin5050Usado = true;
        btn5050.setDisable(true);
        
        List<Button> botonesIncorrectos = Arrays.asList(btnA, btnB, btnC, btnD)
            .stream()
            .filter(btn -> !btn.getText().equals(obtenerRespuestaCorrecta()))
            .collect(Collectors.toList());
        
        Collections.shuffle(botonesIncorrectos);
        botonesIncorrectos.subList(0, 2).forEach(btn -> btn.setVisible(false));
    }

    @FXML
    private void handleTiempoExtra() {
        if (comodinTiempoUsado) return;
        
        comodinTiempoUsado = true;
        btnTiempoExtra.setDisable(true);
        tiempoRestante += 30;
        lblTiempo.setText(String.format("Tiempo: %ds", tiempoRestante));
    }

    private String obtenerRespuestaCorrecta() {
        switch (pregunta.getRespuestaCorrecta()) {
            case "A": return pregunta.getOpcionA();
            case "B": return pregunta.getOpcionB();
            case "C": return pregunta.getOpcionC();
            case "D": return pregunta.getOpcionD();
            default: return "";
        }
    }

    /**
     * Desactiva los botones de respuesta.
     */
    private void desactivarBotones() {
        btnA.setDisable(true);
        btnB.setDisable(true);
        btnC.setDisable(true);
        btnD.setDisable(true);
    }

    /**
     * Vuelve a la pantalla de la ruleta después de responder la pregunta.
     * Carga la pantalla de la ruleta y establece el usuario y las categorías
     * restantes.
     */
    private void volverARuleta() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ruleta.fxml"));
            Scene scene = new Scene(loader.load());

            RuletaController controller = loader.getController();
            controller.setUsuario(usuario);
            controller.setCategoriasRestantes(categoriasRestantes);

            Stage stage = (Stage) lblResultado.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Vuelve al menú principal después de responder la pregunta.
     * Carga la pantalla del menú y establece el usuario actual.
     */
    private void volverAlMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/menu.fxml"));
            Scene scene = new Scene(loader.load());

            MenuController controller = loader.getController();
            controller.setUsuario(usuario);

            Stage stage = (Stage) lblResultado.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
