package es.franciscorodalf.sabelotodo.frontend.controller;

import es.franciscorodalf.sabelotodo.backend.util.ResultadoPartidaUtil;
import es.franciscorodalf.sabelotodo.backend.model.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.util.List;

public class ResultadoController {

    @FXML
    private Label lblTituloResultado;
    @FXML
    private Label lblCategoriasAcertadas;
    @FXML
    private Label lblPuntos;
    @FXML
    private Label lblTiempo;

    private ResultadoPartidaUtil resultado;

    public void setResultado(ResultadoPartidaUtil resultado) {
        this.resultado = resultado;
        cargarDatos();
    }

    private void cargarDatos() {
        if (resultado != null) {
            Usuario usuario = resultado.getUsuario();
            List<String> categorias = resultado.getCategoriasAcertadas();
            int puntos = resultado.getPuntaje();
            String tiempo = resultado.getTiempoTotal();

            lblTituloResultado.setText("😢 ¡Has perdido!");
            lblCategoriasAcertadas
                    .setText("Categorías acertadas: " + categorias.size() + " → " + String.join(", ", categorias));
            lblPuntos.setText("Puntos totales: " + puntos);
            lblTiempo.setText("Tiempo total: " + tiempo);
        }
    }

    @FXML
    private void handleVolverMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/menu.fxml"));
            Scene scene = new Scene(loader.load());

            MenuController controller = loader.getController();
            controller.setUsuario(resultado.getUsuario());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleReiniciarPartida(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ruleta.fxml"));
            Scene scene = new Scene(loader.load());

            RuletaController controller = loader.getController();
            controller.setUsuario(resultado.getUsuario());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
