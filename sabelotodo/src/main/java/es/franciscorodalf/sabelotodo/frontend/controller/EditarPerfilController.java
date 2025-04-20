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

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCorreo;
    @FXML
    private Label lblMensaje;

    private Usuario usuario;

    /**
     * Establece el usuario cuyo perfil será editado.
     * Carga el nombre y correo del usuario en los campos de texto.
     *
     * @param usuario El usuario cuya información será cargada y modificada.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
        txtNombre.setText(usuario.getUsername());
        txtCorreo.setText(usuario.getEmail());
    }

    /**
     * Maneja el evento de guardar los cambios en el perfil del usuario.
     * Valida los datos introducidos, actualiza la base de datos y muestra un
     * mensaje de éxito o error.
     * Si la actualización es exitosa, vuelve al menú después de unos segundos.
     *
     * @param event El evento generado al hacer clic en el botón de "Guardar"
     */
    @FXML
    private void handleGuardar(ActionEvent event) {
        String nuevoNombre = txtNombre.getText().trim();
        String nuevoCorreo = txtCorreo.getText().trim();

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

        usuario.setUsername(nuevoNombre);
        usuario.setEmail(nuevoCorreo);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean exito = usuarioDAO.actualizar(usuario);

        if (exito) {
            lblMensaje.setText("¡Perfil actualizado con éxito!");
            lblMensaje.setStyle("-fx-text-fill: green;");
            lblMensaje.setVisible(true);

            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> volverAlMenu(event));
            delay.play();
        } else {
            lblMensaje.setText("Error al actualizar perfil.");
            lblMensaje.setStyle("-fx-text-fill: red;");
            lblMensaje.setVisible(true);
        }
    }

    /**
     * Vuelve al menú principal después de actualizar el perfil.
     * Carga la pantalla del menú y establece el usuario actual.
     *
     * @param event El evento generado al hacer clic en el botón de "Volver al menú"
     */
    private void volverAlMenu(ActionEvent event) {
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
