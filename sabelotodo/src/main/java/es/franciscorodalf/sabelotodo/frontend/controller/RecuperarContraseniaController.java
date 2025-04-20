package es.franciscorodalf.sabelotodo.frontend.controller;

import es.franciscorodalf.sabelotodo.backend.dao.UsuarioDAO;
import es.franciscorodalf.sabelotodo.backend.model.Usuario;
import es.franciscorodalf.sabelotodo.backend.util.AlertaUtil;
import es.franciscorodalf.sabelotodo.backend.util.ValidadorDatosUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class RecuperarContraseniaController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private Label infoLabel;

    /**
     * Maneja la acción de enviar el correo para recuperar la contraseña.
     * Valida los campos y busca al usuario en la base de datos.
     *
     * @param event El evento generado al hacer clic en el botón de "Enviar"
     */
    @FXML
    private void handleEnviarCorreo(ActionEvent event) {
        String username = usernameField.getText();
        String email = emailField.getText();

        if (ValidadorDatosUtil.esCampoVacio(username) || ValidadorDatosUtil.esCampoVacio(email)) {
            AlertaUtil.mensajeEnLabel(infoLabel, "Completa todos los campos.", true);
            return;
        }

        if (!ValidadorDatosUtil.esEmailValido(email)) {
            AlertaUtil.mensajeEnLabel(infoLabel, "El correo no es válido.", true);
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = dao.buscarPorUsernameYCorreo(username, email);

        if (usuario != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/cambiar-contrasenia.fxml"));
                Scene scene = new Scene(loader.load());

                CambiarContraseniaController controller = loader.getController();
                controller.setUsuario(usuario);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                AlertaUtil.mensajeEnLabel(infoLabel, "No se pudo abrir el formulario.", true);
                e.printStackTrace();
            }
        } else {
            AlertaUtil.mensajeEnLabel(infoLabel, "No se encontró un usuario con esos datos.", true);
        }
    }

    /**
     * Maneja la acción de volver a la pantalla de login.
     * Carga la vista de login.
     *
     * @param event El evento generado al hacer clic en el botón de "Volver"
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
