package es.franciscorodalf.sabelotodo.frontend.controller;

import es.franciscorodalf.sabelotodo.backend.dao.UsuarioDAO;
import es.franciscorodalf.sabelotodo.backend.model.Usuario;
import es.franciscorodalf.sabelotodo.backend.util.ValidadorDatosUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import es.franciscorodalf.sabelotodo.frontend.util.AnimacionUtil;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMensaje;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegistro;

    @FXML
    public void initialize() {
        if (txtEmail != null) {
            AnimacionUtil.aplicarAnimacionEntrada(txtEmail);
        }
        if (txtPassword != null) {
            AnimacionUtil.aplicarAnimacionEntrada(txtPassword);
        }
        if (btnLogin != null) {
            AnimacionUtil.aplicarAnimacionBoton(btnLogin);
        }
        if (btnRegistro != null) {
            AnimacionUtil.aplicarAnimacionBoton(btnRegistro);
        }
    }

    /**
     * Maneja la acción de inicio de sesión.
     * Valida los campos ingresados y verifica las credenciales del usuario en la
     * base de datos.
     * Si las credenciales son correctas, se carga la vista del menú.
     *
     * @param event El evento generado al hacer clic en el botón "Iniciar sesión".
     */
    @FXML
    private void handleLogin(ActionEvent event) {
        // Obtener los valores de los campos de entrada
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        // Validar campos vacíos
        if (ValidadorDatosUtil.esCampoVacio(email) || ValidadorDatosUtil.esCampoVacio(password)) {
            lblMensaje.setText("Por favor completa todos los campos.");
            lblMensaje.setVisible(true);
            AnimacionUtil.aplicarAnimacionError(lblMensaje);
            return;
        } 
        // Validar formato de email
        else if (!ValidadorDatosUtil.esEmailValido(email)) {
            lblMensaje.setText("El correo no tiene un formato válido.");
            lblMensaje.setVisible(true);
            AnimacionUtil.aplicarAnimacionError(lblMensaje);
            return;
        } else {
            // Intentar autenticar al usuario
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario = usuarioDAO.login(email, password);

            // Si la autenticación es exitosa
            if (usuario != null) {
                try {
                    // Cargar la vista del menú principal
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/menu.fxml"));
                    Scene scene = new Scene(loader.load());

                    // Configurar el controlador del menú con el usuario autenticado
                    MenuController controller = loader.getController();
                    controller.setUsuario(usuario);

                    // Cambiar a la escena del menú
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    lblMensaje.setText("No se pudo cargar el menú.");
                    lblMensaje.setVisible(true);
                    AnimacionUtil.aplicarAnimacionError(lblMensaje);
                    e.printStackTrace();
                }
            } else {
                lblMensaje.setText("Correo o contraseña incorrectos.");
                lblMensaje.setVisible(true);
                AnimacionUtil.aplicarAnimacionError(lblMensaje);
            }
        }
    }

    /**
     * Maneja la acción de ir a la pantalla de registro.
     * Este método es llamado cuando el usuario hace clic en el enlace de
     * "Registrarse".
     *
     * @param event El evento generado al hacer clic en el enlace de "Registrarse".
     */
    @FXML
    private void handleIrARegistro(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/registro.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            lblMensaje.setText("No se pudo cargar el registro.");
            lblMensaje.setVisible(true);
            e.printStackTrace();
        }
    }

    /**
     * Maneja la acción de ir a la pantalla de recuperación de contraseña.
     * Este método es llamado cuando el usuario hace clic en el enlace de
     * "¿Olvidaste tu contraseña?".
     *
     * @param event El evento generado al hacer clic en el enlace de "¿Olvidaste tu
     *              contraseña?".
     */
    @FXML
    private void handleIrARecuperar(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/recuperar-contrasenia.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            lblMensaje.setText("No se pudo cargar la recuperación.");
            lblMensaje.setVisible(true);
            e.printStackTrace();
        }
    }
}
