package es.franciscorodalf.sabelotodo.frontend.controller;

import es.franciscorodalf.sabelotodo.backend.dao.CategoriaDAO;
import es.franciscorodalf.sabelotodo.backend.model.Categoria;
import es.franciscorodalf.sabelotodo.backend.model.Usuario;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;

import java.io.IOException;
import java.util.*;

public class RuletaController {

    @FXML
    private Label labelCategoria;
    @FXML
    private Button btnGirar;
    @FXML
    private Button btnJugar;

    private Usuario usuario;
    private List<Categoria> categoriasRestantes;
    private Categoria categoriaSeleccionada;

    private boolean ruletaGirada = false;

    /**
     * Establece el usuario y carga las categorías restantes.
     *
     * @param usuario El usuario actual.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        CategoriaDAO dao = new CategoriaDAO();
        this.categoriasRestantes = new ArrayList<>(dao.obtenerTodas());
    }

    /**
     * Establece las categorías restantes para el juego.
     *
     * @param categorias La lista de categorías restantes.
     */
    public void setCategoriasRestantes(List<Categoria> categorias) {
        this.categoriasRestantes = categorias;
    }

    /**
     * Maneja la acción de girar la ruleta.
     * Selecciona una categoría aleatoria y muestra su nombre en la interfaz.
     * Desactiva el botón "Girar" y muestra el botón "Jugar".
     */
    @FXML
    private void handleGirarCategoria() {
        if (ruletaGirada || categoriasRestantes == null || categoriasRestantes.isEmpty()) {
            // Si ya se ha girado la ruleta o no quedan categorías
            if (categoriasRestantes.isEmpty()) {
                labelCategoria.setText("¡Todas completadas! ¡Felicidades!");
                btnGirar.setDisable(true); // Desactiva el botón "Girar"
            }
            return; // No deja volver a girar
        }

        ruletaGirada = true; // Marcar que ya se ha girado la ruleta
        btnGirar.setDisable(true); // Desactivar botón "Girar"
        btnJugar.setVisible(false); // Ocultar botón "Jugar" mientras gira

        Random random = new Random();
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        final int[] ciclos = { 0 };
        final int ciclosTotales = 40;

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(80), ev -> {
            Categoria randomCategoria = categoriasRestantes.get(random.nextInt(categoriasRestantes.size()));
            labelCategoria.setText(randomCategoria.getNombre());

            ciclos[0]++;
            if (ciclos[0] >= ciclosTotales) {
                categoriaSeleccionada = randomCategoria;
                btnJugar.setVisible(true); // Mostrar el botón "Jugar" una vez finalice la ruleta
                btnGirar.setDisable(false); // Rehabilitar el botón de girar para futuras rondas
                timeline.stop(); // Detener la animación de la ruleta
            }
        }));

        timeline.play(); // Iniciar la animación de la ruleta
    }

    /**
     * Maneja la acción de jugar con la categoría seleccionada.
     * Carga la vista de preguntas y pasa el usuario, la categoría seleccionada y las
     * categorías restantes.
     *
     * @param event El evento generado al hacer clic en el botón "Jugar".
     */
    @FXML
    private void handleJugar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/pregunta.fxml"));
            Scene scene = new Scene(loader.load());

            // Pasar usuario, categoría y las restantes
            PreguntaController controller = loader.getController();
            controller.setUsuarioYCategoria(usuario, categoriaSeleccionada, categoriasRestantes);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Maneja la acción de volver al menú principal.
     * Carga la vista del menú y pasa el usuario al controlador del menú.
     *
     * @param event El evento generado al hacer clic en el botón "Volver al menú".
     */
    @FXML
    private void handleVolverMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/menu.fxml"));
            Scene scene = new Scene(loader.load());

            MenuController controller = loader.getController();
            controller.setUsuario(usuario);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
