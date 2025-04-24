package es.franciscorodalf.sabelotodo.frontend.controller;

import es.franciscorodalf.sabelotodo.backend.model.Usuario;
import es.franciscorodalf.sabelotodo.backend.dao.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class EditarUsuarioController implements Initializable {
    
    @FXML
    private TextField nombreTextField;
    
    @FXML
    private TextField emailTextField;
    
    private Usuario usuarioActual;
    private UsuarioDAO usuarioDAO;
    
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización de componentes si es necesario
        usuarioDAO = new UsuarioDAO();
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        cargarDatosUsuario();
    }
    
    private void cargarDatosUsuario() {
        if (usuarioActual != null) {
            nombreTextField.setText(usuarioActual.getUsername());
            emailTextField.setText(usuarioActual.getEmail());
        }
    }
    
    @FXML
    private void handleGuardar(ActionEvent event) {
        if (validarCampos()) {
            try {
                usuarioActual.setUsername(nombreTextField.getText().trim());
                usuarioActual.setEmail(emailTextField.getText().trim());
                
                usuarioDAO.actualizar(usuarioActual);
                
                mostrarMensaje("Éxito", "Usuario actualizado correctamente", Alert.AlertType.INFORMATION);
                cerrarVentana(event);
            } catch (Exception e) {
                mostrarMensaje("Error", "Error al actualizar usuario: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
    
    @FXML
    private void handleCancelar(ActionEvent event) {
        cerrarVentana(event);
    }
    
    private boolean validarCampos() {
        String nombre = nombreTextField.getText().trim();
        String email = emailTextField.getText().trim();
        
        if (nombre.isEmpty() || email.isEmpty()) {
            mostrarMensaje("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return false;
        }
        
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            mostrarMensaje("Error", "El formato del email no es válido", Alert.AlertType.ERROR);
            return false;
        }
        
        return true;
    }
    
    private void mostrarMensaje(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
    
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
