package es.franciscorodalf.sabelotodo.frontend.controller;

import es.franciscorodalf.sabelotodo.backend.dao.UsuarioDAO;
import es.franciscorodalf.sabelotodo.backend.model.Usuario;
import es.franciscorodalf.sabelotodo.backend.util.ValidadorDatosUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Node;

import java.io.IOException;

public class RegistroController {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label infoLabel;

    @FXML
    private void handleRegistrar(ActionEvent event) {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (ValidadorDatosUtil.esCampoVacio(username) || ValidadorDatosUtil.esCampoVacio(email)
                || ValidadorDatosUtil.esCampoVacio(password) || ValidadorDatosUtil.esCampoVacio(confirmPassword)) {
            infoLabel.setText("Por favor completa todos los campos.");
            return;
        }

        if (!ValidadorDatosUtil.esEmailValido(email)) {
            infoLabel.setText("Correo no válido.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            infoLabel.setText("Las contraseñas no coinciden.");
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();

        if (dao.buscarPorCorreo(email) != null) {
            infoLabel.setText("El correo ya está registrado.");
            return;
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(username);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setPassword(password);

        boolean exito = dao.registrar(nuevoUsuario);

        if (exito) {
            infoLabel.setText("✅ Usuario registrado correctamente.");
            infoLabel.setTextFill(javafx.scene.paint.Color.GREEN);
            infoLabel.setVisible(true);

            new Thread(() -> {
                try {
                    Thread.sleep(2000); 
                    javafx.application.Platform.runLater(() -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
                            Scene scene = new Scene(loader.load());

                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException e) {
                            infoLabel.setText("Error al volver al login.");
                            infoLabel.setTextFill(javafx.scene.paint.Color.RED);
                            infoLabel.setVisible(true);
                            e.printStackTrace();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @FXML
    private void handleVolverLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            infoLabel.setText("Error al volver al login.");
            e.printStackTrace();
        }
    }
}
