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

/**
 * Controlador para la vista de recuperación de contraseña.
 * Maneja la lógica de validación y envío de datos para recuperar la contraseña.
 */
public class RecuperarContraseniaController {

    // Campos de entrada FXML
    @FXML
    private TextField usernameField;  // Campo para el nombre de usuario
    @FXML
    private TextField emailField;     // Campo para el correo electrónico
    @FXML
    private Label infoLabel;         // Etiqueta para mostrar mensajes al usuario

    /**
     * Maneja la acción de enviar el correo para recuperar la contraseña.
     * Valida los campos y busca al usuario en la base de datos.
     * Si encuentra al usuario, redirige a la vista de cambio de contraseña.
     * 
     * @param event El evento generado al hacer clic en el botón de "Enviar"
     */
    @FXML
    private void handleEnviarCorreo(ActionEvent event) {
        // Obtener valores de los campos
        String username = usernameField.getText();
        String email = emailField.getText();

        // Validar campos vacíos
        if (ValidadorDatosUtil.esCampoVacio(username) || ValidadorDatosUtil.esCampoVacio(email)) {
            AlertaUtil.mensajeEnLabel(infoLabel, "Completa todos los campos.", true);
            return;
        }

        // Validar formato de email
        if (!ValidadorDatosUtil.esEmailValido(email)) {
            AlertaUtil.mensajeEnLabel(infoLabel, "El correo no es válido.", true);
            return;
        }

        // Buscar usuario en la base de datos
        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = dao.buscarPorUsernameYCorreo(username, email);

        if (usuario != null) {
            try {
                // Cargar vista de cambio de contraseña
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/cambiar-contrasenia.fxml"));
                Scene scene = new Scene(loader.load());

                // Configurar el controlador con el usuario encontrado
                CambiarContraseniaController controller = loader.getController();
                controller.setUsuario(usuario);

                // Cambiar a la nueva escena
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
