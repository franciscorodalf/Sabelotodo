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

/**
 * Controlador para la vista de edición de usuario.
 * Permite modificar el nombre de usuario y el email.
 */
public class EditarUsuarioController implements Initializable {
    
    // Elementos de la interfaz FXML
    @FXML
    private TextField nombreTextField;    // Campo para el nombre de usuario
    @FXML
    private TextField emailTextField;     // Campo para el email
    
    // Variables de estado
    private Usuario usuarioActual;        // Usuario que se está editando
    private UsuarioDAO usuarioDAO;        // DAO para operaciones con la base de datos
    
    /**
     * Inicializa el controlador.
     * Crea una nueva instancia del DAO de usuarios.
     */
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        usuarioDAO = new UsuarioDAO();
    }
    
    /**
     * Establece el usuario a editar y carga sus datos en el formulario.
     * @param usuario Usuario que se va a editar
     */
    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
        cargarDatosUsuario();
    }
    
    /**
     * Carga los datos del usuario en los campos del formulario.
     */
    private void cargarDatosUsuario() {
        if (usuarioActual != null) {
            nombreTextField.setText(usuarioActual.getUsername());
            emailTextField.setText(usuarioActual.getEmail());
        }
    }
    
    /**
     * Maneja el evento de guardar los cambios del usuario.
     * Valida los campos y actualiza los datos en la base de datos.
     * @param event Evento del botón guardar
     */
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
    
    /**
     * Maneja el evento de cancelar la edición.
     * Cierra la ventana sin guardar cambios.
     * @param event Evento del botón cancelar
     */
    @FXML
    private void handleCancelar(ActionEvent event) {
        cerrarVentana(event);
    }
    
    /**
     * Valida que los campos del formulario sean correctos.
     * Verifica que no estén vacíos y que el email tenga formato válido.
     * @return true si los campos son válidos, false en caso contrario
     */
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
    
    /**
     * Muestra un mensaje al usuario usando un diálogo de alerta.
     * @param titulo Título de la alerta
     * @param contenido Mensaje a mostrar
     * @param tipo Tipo de alerta (ERROR, INFORMATION, etc)
     */
    private void mostrarMensaje(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
    
    /**
     * Cierra la ventana actual.
     * @param event Evento que disparó el cierre
     */
    private void cerrarVentana(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
