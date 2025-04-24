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
import es.franciscorodalf.sabelotodo.frontend.util.AnimacionUtil;

import java.io.IOException;

public class EditarPerfilController {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCorreo;
    @FXML
    private Label lblMensaje;

    private Usuario usuario;

    @FXML
    public void initialize() {
        if (txtNombre != null) AnimacionUtil.aplicarAnimacionEntrada(txtNombre);
        if (txtCorreo != null) AnimacionUtil.aplicarAnimacionEntrada(txtCorreo);
    }

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
            mostrarMensaje("Por favor, completa todos los campos.", true);
            return;
        }

        if (!ValidadorDatosUtil.esEmailValido(nuevoCorreo)) {
            mostrarMensaje("El correo no tiene un formato válido.", true);
            return;
        }

        usuario.setUsername(nuevoNombre);
        usuario.setEmail(nuevoCorreo);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean exito = usuarioDAO.actualizar(usuario);

        if (exito) {
            mostrarMensaje("¡Perfil actualizado con éxito!", false);

            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            delay.setOnFinished(e -> volverAlMenu(event));
            delay.play();
        } else {
            mostrarMensaje("Error al actualizar perfil.", true);
        }
    }

    @FXML
    private void handleCancelar(ActionEvent event) {
        volverAlMenu(event);
    }

    private void mostrarMensaje(String mensaje, boolean esError) {
        lblMensaje.setText(mensaje);
        lblMensaje.setStyle(esError ? "-fx-text-fill: red;" : "-fx-text-fill: green;");
        lblMensaje.setVisible(true);
        AnimacionUtil.aplicarAnimacionError(lblMensaje);
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
