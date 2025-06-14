package es.franciscorodalf.sabelotodo.frontend.controller;

import es.franciscorodalf.sabelotodo.backend.dao.CategoriaDAO;
import es.franciscorodalf.sabelotodo.backend.model.Categoria;
import es.franciscorodalf.sabelotodo.backend.model.Usuario;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;
import es.franciscorodalf.sabelotodo.frontend.util.AnimacionUtil;

import java.io.IOException;
import java.util.*;

/**
 * Controlador para la vista de la ruleta del juego.
 * Maneja la lógica de girar la ruleta y seleccionar categorías.
 */
public class RuletaController {

    // Elementos de la interfaz FXML
    @FXML
    private Label labelCategoria;
    @FXML
    private Button btnGirar;
    @FXML
    private Button btnJugar;
    @FXML
    private Button btnVolver;
    @FXML
    private ImageView ruletaImage;

    // Variables de estado y configuración
    private Usuario usuario;
    private List<Categoria> categoriasRestantes;
    private Categoria categoriaSeleccionada;
    private boolean ruletaGirada = false;
    private static final int TOTAL_GIROS = 40;  // Número total de giros antes de detenerse
    private static final Duration DURACION_GIRO = Duration.millis(80);  // Duración de cada giro

    /**
     * Inicializa el controlador.
     * Configura la rotación inicial de la ruleta y aplica animaciones a los botones.
     */
    @FXML
    public void initialize() {
        if (ruletaImage != null) {
            ruletaImage.setRotate(0);
            ruletaImage.setRotationAxis(Rotate.Z_AXIS);
        }
        AnimacionUtil.aplicarAnimacionBoton(btnGirar);
        AnimacionUtil.aplicarAnimacionBoton(btnJugar);
        AnimacionUtil.aplicarAnimacionBoton(btnVolver);
        AnimacionUtil.aplicarAnimacionEntrada(ruletaImage);
    }

    /**
     * Establece el usuario actual y carga las categorías disponibles.
     * @param usuario Usuario actual del juego
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        CategoriaDAO dao = new CategoriaDAO();
        this.categoriasRestantes = new ArrayList<>(dao.obtenerTodas());
    }

    /**
     * Actualiza la lista de categorías restantes.
     * @param categorias Lista de categorías disponibles
     */
    public void setCategoriasRestantes(List<Categoria> categorias) {
        this.categoriasRestantes = categorias;
    }

    /**
     * Maneja el evento de girar la ruleta.
     * Valida si se puede girar y comienza la animación.
     */
    @FXML
    private void handleGirarCategoria() {
        if (!validarGiro())
            return;

        prepararGiro();
        iniciarAnimacion();
    }

    /**
     * Valida si la ruleta puede girar.
     * @return true si la ruleta puede girar, false en caso contrario
     */
    private boolean validarGiro() {
        if (ruletaGirada || categoriasRestantes == null || categoriasRestantes.isEmpty()) {
            if (categoriasRestantes.isEmpty()) {
                labelCategoria.setText("¡Todas completadas! ¡Felicidades!");
                btnGirar.setDisable(true);
            }
            return false;
        }
        return true;
    }

    /**
     * Prepara la interfaz para el giro de la ruleta.
     */
    private void prepararGiro() {
        ruletaGirada = true;
        btnGirar.setDisable(true);
        btnJugar.setVisible(false);
    }

    /**
     * Inicia la animación de la ruleta.
     * Incluye la rotación visual, el sonido y la selección aleatoria de categorías.
     */
    private void iniciarAnimacion() {
        Random random = new Random();
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Reproducir sonido de la ruleta
        Sonido.reproducirSonidoRuleta();

        // Seleccionamos la categoría al inicio para asegurarnos de que no sea null
        categoriaSeleccionada = categoriasRestantes.get(random.nextInt(categoriasRestantes.size()));

        // Configurar rotación de la ruleta
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3), ruletaImage);
        rotateTransition.setByAngle((360 * 8) + (random.nextDouble() * 360));
        rotateTransition.setCycleCount(1);
        rotateTransition.setInterpolator(Interpolator.EASE_OUT);

        rotateTransition.setOnFinished(event -> {
            timeline.stop();
            Sonido.detenerSonidoRuleta(); // Detener el sonido cuando la ruleta pare
            labelCategoria.setText(categoriaSeleccionada.getNombre()); // Aseguramos que muestre la categoría final
            btnJugar.setVisible(true);
            btnGirar.setVisible(false);
        });

        final int[] ciclos = { 0 };
        double duracionTotal = 3000; // 3 segundos en milisegundos
        double intervalo = duracionTotal / TOTAL_GIROS;

        timeline.getKeyFrames().add(
                new KeyFrame(Duration.millis(intervalo), event -> {
                    if (ciclos[0] < TOTAL_GIROS) {
                        Categoria randomCategoria = categoriasRestantes.get(
                                random.nextInt(categoriasRestantes.size()));
                        labelCategoria.setText(randomCategoria.getNombre());

                        if (ciclos[0] == TOTAL_GIROS - 1) {
                            categoriaSeleccionada = randomCategoria;
                        }
                    }
                    ciclos[0]++;
                }));

        // Iniciamos ambas animaciones
        rotateTransition.play();
        timeline.play();
    }

    /**
     * Maneja el evento de comenzar el juego.
     * Carga la vista de preguntas con la categoría seleccionada.
     * @param event Evento que disparó la acción
     */
    @FXML
    private void handleJugar(ActionEvent event) {
        try {
            // Validación antes de cambiar de pantalla
            if (categoriaSeleccionada == null) {
                System.err.println("Error: No se ha seleccionado ninguna categoría");
                return;
            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/pregunta.fxml"));
            Scene scene = new Scene(loader.load());

            PreguntaController controller = loader.getController();
            controller.setUsuarioYCategoria(usuario, categoriaSeleccionada, categoriasRestantes);

            cambiarEscena(event, scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Maneja el evento de volver al menú principal.
     * @param event Evento que disparó la acción
     */
    @FXML
    private void handleVolverMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/menu.fxml"));
            Scene scene = new Scene(loader.load());

            MenuController controller = loader.getController();
            controller.setUsuario(usuario);

            cambiarEscena(event, scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Utilidad para cambiar entre diferentes escenas.
     * @param event Evento que disparó el cambio de escena
     * @param scene Nueva escena a mostrar
     */
    private void cambiarEscena(ActionEvent event, Scene scene) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
