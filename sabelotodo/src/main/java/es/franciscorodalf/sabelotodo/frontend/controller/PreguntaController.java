package es.franciscorodalf.sabelotodo.frontend.controller;

import es.franciscorodalf.sabelotodo.backend.dao.CategoriaDAO;
import es.franciscorodalf.sabelotodo.backend.dao.PartidaDAO;
import es.franciscorodalf.sabelotodo.backend.dao.PreguntaDAO;
import es.franciscorodalf.sabelotodo.backend.model.Categoria;
import es.franciscorodalf.sabelotodo.backend.model.Pregunta;
import es.franciscorodalf.sabelotodo.backend.model.Usuario;
import javafx.animation.PauseTransition;
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

public class PreguntaController {

    @FXML
    private Label lblCategoria;
    @FXML
    private Label lblPregunta;
    @FXML
    private Label lblResultado;

    @FXML
    private Button btnA;
    @FXML
    private Button btnB;
    @FXML
    private Button btnC;
    @FXML
    private Button btnD;

    private Usuario usuario;
    private Categoria categoria;
    private List<Categoria> categoriasRestantes;
    private Pregunta pregunta;

    public void setUsuarioYCategoria(Usuario usuario, Categoria categoria, List<Categoria> categoriasRestantes) {
        this.usuario = usuario;
        this.categoria = categoria;
        this.categoriasRestantes = categoriasRestantes;
        cargarPregunta();
    }

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

    @FXML
    private void handleRespuesta(ActionEvent event) {
        Button boton = (Button) event.getSource();
        String respuesta = boton.getText();

        desactivarBotones();

        String respuestaCorrecta = "";

        // Obtener la respuesta correcta de la base de datos
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

        // Comparar respuesta del usuario con la correcta
        if (respuesta.trim().equalsIgnoreCase(respuestaCorrecta.trim())) {
            lblResultado.setText("✅ ¡Correcto!");
            lblResultado.setStyle("-fx-text-fill: green;");
            lblResultado.setVisible(true);

            // Eliminar la categoría respondida
            categoriasRestantes.removeIf(cat -> cat.getId() == categoria.getId());

            // Si no quedan categorías, mostrar victoria y registrar partida
            if (categoriasRestantes.isEmpty()) {
                lblResultado.setText("🎉 ¡Felicidades, has completado todas las categorías!");
                int totalCategorias = new CategoriaDAO().obtenerTodas().size();

                // Registrar la partida con puntos y categorías acertadas
                new PartidaDAO().registrarPartida(usuario.getId(), 100, totalCategorias);

                // Esperar unos segundos antes de volver al menú
                PauseTransition delay = new PauseTransition(Duration.seconds(3));
                delay.setOnFinished(e -> volverAlMenu());
                delay.play();
                return;
            }

            // Si acertó, vuelve a girar la ruleta
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> volverARuleta());
            delay.play();

        } else {
            lblResultado.setText("❌ Respuesta incorrecta. La correcta era: " + respuestaCorrecta);
            lblResultado.setStyle("-fx-text-fill: red;");
            lblResultado.setVisible(true);

            // Registrar la partida fallida
            new PartidaDAO().registrarPartida(usuario.getId(), 0, 0);

            // Espera un poco y luego volver al menú
            PauseTransition delay = new PauseTransition(Duration.seconds(2.5));
            delay.setOnFinished(e -> volverAlMenu());
            delay.play();
        }
    }

    private void desactivarBotones() {
        btnA.setDisable(true);
        btnB.setDisable(true);
        btnC.setDisable(true);
        btnD.setDisable(true);
    }

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
