package es.franciscorodalf.sabelotodo.frontend.util;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.scene.layout.Pane;

/**
 * Utilidad para aplicar animaciones a elementos de la interfaz gráfica.
 * Proporciona métodos estáticos para diferentes tipos de animaciones.
 */
public class AnimacionUtil {
    
    /**
     * Aplica una animación de entrada con fade y desplazamiento vertical.
     * El elemento aparece gradualmente mientras se desplaza hacia arriba.
     * 
     * @param node Elemento al que se aplicará la animación
     */
    public static void aplicarAnimacionEntrada(Node node) {
        // Animación de aparición gradual
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), node);
        fadeIn.setFromValue(0);  // Comienza invisible
        fadeIn.setToValue(1);    // Termina completamente visible
        
        // Animación de desplazamiento vertical
        TranslateTransition translate = new TranslateTransition(Duration.seconds(0.5), node);
        translate.setFromY(50);   // Comienza 50 píxeles abajo
        translate.setToY(0);      // Termina en su posición original
        
        // Combinar ambas animaciones y reproducir
        ParallelTransition paralelo = new ParallelTransition(fadeIn, translate);
        paralelo.play();
    }
    
    /**
     * Aplica una animación de escala al pasar el ratón sobre un botón.
     * El botón se agranda al pasar el cursor y vuelve a su tamaño original al salir.
     * 
     * @param button Botón al que se aplicará la animación
     */
    public static void aplicarAnimacionBoton(Button button) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(100), button);
        
        button.setOnMouseEntered(e -> {
            scale.setToX(1.1);
            scale.setToY(1.1);
            scale.play();
        });
        
        button.setOnMouseExited(e -> {
            scale.setToX(1.0);
            scale.setToY(1.0);
            scale.play();
        });
    }
    
    /**
     * Aplica una animación de error con fade y sacudida horizontal.
     * Útil para mostrar mensajes de error o advertencia.
     * 
     * @param label Etiqueta a la que se aplicará la animación
     */
    public static void aplicarAnimacionError(Label label) {
        // Animación de aparición gradual
        FadeTransition fade = new FadeTransition(Duration.seconds(0.3), label);
        fade.setFromValue(0);
        fade.setToValue(1);
        
        // Animación de sacudida horizontal
        TranslateTransition shake = new TranslateTransition(Duration.millis(100), label);
        shake.setFromX(0);
        shake.setByX(10);
        shake.setCycleCount(6);
        shake.setAutoReverse(true);
        
        // Combinar ambas animaciones y reproducir
        ParallelTransition paralelo = new ParallelTransition(fade, shake);
        paralelo.play();
    }
    
    /**
     * Aplica una animación de transición suave entre escenas.
     * La nueva escena aparece gradualmente con un efecto fade.
     * 
     * @param contenedor Panel contenedor de la escena
     */
    public static void animarCambioEscena(Pane contenedor) {
        // Animación de transición suave
        FadeTransition fade = new FadeTransition(Duration.seconds(0.4), contenedor);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }
}
