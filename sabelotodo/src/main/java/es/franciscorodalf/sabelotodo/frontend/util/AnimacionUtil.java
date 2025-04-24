package es.franciscorodalf.sabelotodo.frontend.util;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.scene.layout.Pane;

public class AnimacionUtil {
    
    public static void aplicarAnimacionEntrada(Node node) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), node);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        
        TranslateTransition translate = new TranslateTransition(Duration.seconds(0.5), node);
        translate.setFromY(50);
        translate.setToY(0);
        
        ParallelTransition paralelo = new ParallelTransition(fadeIn, translate);
        paralelo.play();
    }
    
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
    
    public static void aplicarAnimacionError(Label label) {
        FadeTransition fade = new FadeTransition(Duration.seconds(0.3), label);
        fade.setFromValue(0);
        fade.setToValue(1);
        
        TranslateTransition shake = new TranslateTransition(Duration.millis(100), label);
        shake.setFromX(0);
        shake.setByX(10);
        shake.setCycleCount(6);
        shake.setAutoReverse(true);
        
        ParallelTransition paralelo = new ParallelTransition(fade, shake);
        paralelo.play();
    }
    
    public static void animarCambioEscena(Pane contenedor) {
        FadeTransition fade = new FadeTransition(Duration.seconds(0.4), contenedor);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }
}
