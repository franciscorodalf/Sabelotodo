package es.franciscorodalf.sabelotodo.frontend.controller;

import es.franciscorodalf.sabelotodo.backend.dao.UsuarioDAO;
import es.franciscorodalf.sabelotodo.backend.model.Usuario;
import es.franciscorodalf.sabelotodo.backend.util.ValidadorDatosUtil;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.util.Duration;

import java.io.IOException;

public class EditarPerfilController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private Label lblMensaje;

    private Usuario usuario;

    // Método para configurar el usuario
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        // Cargar los datos del usuario en los campos
        txtNombre.setText(usuario.getUsername());
        txtCorreo.setText(usuario.getEmail());
    }

    // Método que maneja el botón de guardar cambios
    @FXML
    private void handleGuardar(ActionEvent event) {
        // Obtener los datos del formulario
        String nuevoNombre = txtNombre.getText().trim();
        String nuevoCorreo = txtCorreo.getText().trim();

        // Validar que no estén vacíos y que el correo tenga el formato correcto
        if (ValidadorDatosUtil.esCampoVacio(nuevoNombre) || ValidadorDatosUtil.esCampoVacio(nuevoCorreo)) {
            lblMensaje.setText("Por favor, completa todos los campos.");
            lblMensaje.setStyle("-fx-text-fill: red;");
            lblMensaje.setVisible(true);
            return;
        }

        if (!ValidadorDatosUtil.esEmailValido(nuevoCorreo)) {
            lblMensaje.setText("El correo no tiene un formato válido.");
            lblMensaje.setStyle("-fx-text-fill: red;");
            lblMensaje.setVisible(true);
            return;
        }

        // Actualizar el usuario en la base de datos
        usuario.setUsername(nuevoNombre);
        usuario.setEmail(nuevoCorreo);

        // Usar el ID del usuario para asegurarnos de que se actualiza correctamente en la base de datos
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean exito = usuarioDAO.actualizar(usuario); // Método para actualizar el usuario en la base de datos

        if (exito) {
            lblMensaje.setText("¡Perfil actualizado con éxito!");
            lblMensaje.setStyle("-fx-text-fill: green;");
            lblMensaje.setVisible(true);

            // Esperar unos segundos antes de volver al menú
            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> volverAlMenu(event));
            delay.play();
        } else {
            lblMensaje.setText("Error al actualizar perfil.");
            lblMensaje.setStyle("-fx-text-fill: red;");
            lblMensaje.setVisible(true);
        }
    }

    // Método para volver al menú
    private void volverAlMenu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/menu.fxml"));
            Scene scene = new Scene(loader.load());

            MenuController controller = loader.getController();
            controller.setUsuario(usuario); // Pasar el usuario al menú

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
