package es.franciscorodalf.sabelotodo.frontend.controller;

import es.franciscorodalf.sabelotodo.backend.dao.PartidaDAO;
import es.franciscorodalf.sabelotodo.backend.model.Partida;
import es.franciscorodalf.sabelotodo.backend.model.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;
import java.util.List;

public class MenuController {

    @FXML private Label lblNombreUsuario;
    @FXML private Label lblEmail;
    @FXML private Label lblPartidasJugadas;
    @FXML private Label lblPuntosTotales;

    private Usuario usuario;

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        cargarDatos();
    }

    private void cargarDatos() {
        if (usuario != null) {
            lblNombreUsuario.setText("Usuario: " + usuario.getUsername());
            lblEmail.setText("Correo: " + usuario.getEmail());

            PartidaDAO partidaDAO = new PartidaDAO();
            List<Partida> partidas = partidaDAO.obtenerPorUsuario(usuario.getId());

            lblPartidasJugadas.setText("Partidas jugadas: " + partidas.size());

            int totalPuntos = 0;
            for (Partida partida : partidas) {
                totalPuntos += partida.getPuntaje();
            }

            lblPuntosTotales.setText("Puntos totales: " + totalPuntos);
        }
    }

    @FXML
    private void handleEditarPerfil(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/editar-usuario.fxml"));
            Scene scene = new Scene(loader.load());
    
            EditarPerfilController controller = loader.getController();
            controller.setUsuario(usuario); // si usas paso de objeto
    
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    @FXML
    private void handleJugar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ruleta.fxml"));
            Scene scene = new Scene(loader.load());

            // Pasar usuario
            RuletaController controller = loader.getController();
            controller.setUsuario(usuario);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCerrarSesion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
