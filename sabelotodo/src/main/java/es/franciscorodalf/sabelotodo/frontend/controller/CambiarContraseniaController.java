package es.franciscorodalf.sabelotodo.frontend.controller;

import es.franciscorodalf.sabelotodo.backend.dao.UsuarioDAO;
import es.franciscorodalf.sabelotodo.backend.model.Usuario;
import es.franciscorodalf.sabelotodo.backend.util.AlertaUtil;
import es.franciscorodalf.sabelotodo.backend.util.ValidadorDatosUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class CambiarContraseniaController {

    @FXML
    private PasswordField nuevaContraseniaField;
    @FXML
    private PasswordField confirmarContraseniaField;
    @FXML
    private Label infoLabel;

    private Usuario usuario;

    /**
     * Establece el usuario que realizará el cambio de contraseña.
     *
     * @param usuario El usuario que realizará la operación de cambio de contraseña.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Maneja la acción de guardar el cambio de contraseña.
     * Valida los campos y realiza la actualización de la contraseña en la base de
     * datos.
     *
     * @param event El evento generado al hacer clic en el botón de "Guardar"
     */
    @FXML
    private void handleGuardar(ActionEvent event) {
        String nueva = nuevaContraseniaField.getText();
        String confirmar = confirmarContraseniaField.getText();

        if (ValidadorDatosUtil.esCampoVacio(nueva) || ValidadorDatosUtil.esCampoVacio(confirmar)) {
            AlertaUtil.mensajeEnLabel(infoLabel, "Completa ambos campos.", true);
            return;
        }

        if (!nueva.equals(confirmar)) {
            AlertaUtil.mensajeEnLabel(infoLabel, "Las contraseñas no coinciden.", true);
            return;
        }

        usuario.setPassword(nueva);
        UsuarioDAO dao = new UsuarioDAO();
        boolean actualizado = dao.actualizar(usuario);

        if (actualizado) {
            AlertaUtil.mensajeEnLabel(infoLabel, "Contraseña cambiada correctamente.", false);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
                Scene scene = new Scene(loader.load());

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                AlertaUtil.mensajeEnLabel(infoLabel, "No se pudo volver al login.", true);
                e.printStackTrace();
            }
        } else {
            AlertaUtil.mensajeEnLabel(infoLabel, "No se pudo actualizar la contraseña.", true);
        }
    }

    /**
     * Maneja la acción de volver a la pantalla de login.
     * Este método es llamado cuando el usuario hace clic en el botón de "Volver al
     * login".
     *
     * @param event El evento generado al hacer clic en el botón de "Volver al
     *              login"
     */
    @FXML
    private void handleVolverLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            AlertaUtil.mensajeEnLabel(infoLabel, "Error al volver al login.", true);
            e.printStackTrace();
        }
    }
}
