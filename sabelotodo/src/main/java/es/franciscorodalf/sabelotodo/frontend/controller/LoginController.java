package es.franciscorodalf.sabelotodo.frontend.controller;

import es.franciscorodalf.sabelotodo.backend.dao.UsuarioDAO;
import es.franciscorodalf.sabelotodo.backend.model.Usuario;
import es.franciscorodalf.sabelotodo.backend.util.ValidadorDatosUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMensaje;

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        if (ValidadorDatosUtil.esCampoVacio(email) || ValidadorDatosUtil.esCampoVacio(password)) {
            lblMensaje.setText("Por favor completa todos los campos.");
            lblMensaje.setVisible(true);
        } else if (!ValidadorDatosUtil.esEmailValido(email)) {
            lblMensaje.setText("El correo no tiene un formato válido.");
            lblMensaje.setVisible(true);
        } else {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario = usuarioDAO.login(email, password);

            if (usuario != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/menu.fxml"));
                    Scene scene = new Scene(loader.load());

                    MenuController controller = loader.getController();
                    controller.setUsuario(usuario);

                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    lblMensaje.setText("No se pudo cargar el menú.");
                    lblMensaje.setVisible(true);
                    e.printStackTrace();
                }
            } else {
                lblMensaje.setText("Correo o contraseña incorrectos.");
                lblMensaje.setVisible(true);
            }
        }
    }

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
