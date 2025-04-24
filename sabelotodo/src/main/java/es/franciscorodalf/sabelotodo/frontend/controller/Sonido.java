package es.franciscorodalf.sabelotodo.frontend.controller;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.net.URL;

public class Sonido {
    private static MediaPlayer ruletaPlayer;
    private static MediaPlayer correctoPlayer;
    private static MediaPlayer falloPlayer;
    private static boolean isPlaying = false;
    
    public static void reproducirSonidoRuleta() {
        try {
            if (isPlaying) {
                return;
            }
            
            URL resource = Sonido.class.getResource("/sonido/GiroRuletaSonido.mp3");
            if (resource == null) {
                System.err.println("No se encontró el archivo GiroRuletaSonido.mp3");
                return;
            }

            detenerSonidoRuleta(); // Detener reproductor anterior si existe
            
            Media sound = new Media(resource.toExternalForm());
            ruletaPlayer = new MediaPlayer(sound);
            ruletaPlayer.setOnEndOfMedia(() -> {
                isPlaying = false;
                ruletaPlayer.dispose();
                ruletaPlayer = null;
            });
            
            ruletaPlayer.setVolume(1.0);
            isPlaying = true;
            ruletaPlayer.play();
            
        } catch (Exception e) {
            System.err.println("Error al cargar sonido ruleta: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void reproducirSonidoCorrecto() {
        try {
            URL resource = Sonido.class.getResource("/sonido/correctoSonido.mp3");
            if (resource == null) {
                System.err.println("No se encontró el archivo correctoSonido.mp3");
                return;
            }
            
            if (correctoPlayer != null) {
                correctoPlayer.stop();
                correctoPlayer.dispose();
            }
            
            Media sound = new Media(resource.toExternalForm());
            correctoPlayer = new MediaPlayer(sound);
            correctoPlayer.setOnEndOfMedia(() -> {
                correctoPlayer.dispose();
                correctoPlayer = null;
            });
            
            correctoPlayer.setVolume(1.0);
            correctoPlayer.play();
            
        } catch (Exception e) {
            System.err.println("Error al cargar sonido correcto: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void reproducirSonidoFallo() {
        try {
            URL resource = Sonido.class.getResource("/sonido/falloSonido.mp3");
            if (resource == null) {
                System.err.println("No se encontró el archivo falloSonido.mp3");
                return;
            }

            if (falloPlayer != null) {
                falloPlayer.stop();
                falloPlayer.dispose();
            }
            
            Media sound = new Media(resource.toExternalForm());
            falloPlayer = new MediaPlayer(sound);
            falloPlayer.setOnEndOfMedia(() -> {
                falloPlayer.dispose();
                falloPlayer = null;
            });
            
            falloPlayer.setVolume(1.0);
            falloPlayer.play();
            
        } catch (Exception e) {
            System.err.println("Error al cargar sonido de fallo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void detenerSonidoRuleta() {
        if (ruletaPlayer != null) {
            try {
                ruletaPlayer.stop();
                ruletaPlayer.dispose();
                ruletaPlayer = null;
                isPlaying = false;
            } catch (Exception e) {
                System.err.println("Error al detener sonido ruleta: " + e.getMessage());
            }
        }
    }
}
