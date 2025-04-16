package es.franciscorodalf.sabelotodo.backend.util;

import java.util.regex.Pattern;

public class ValidadorDatosUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);

    public static boolean esEmailValido(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).find();
    }

    public static boolean esCampoVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    public static boolean contrasenaCoincide(String contrasena, String confirmacion) {
        return contrasena != null && contrasena.equals(confirmacion);
    }

    public static boolean esContrasenaSegura(String contrasena) {
        return contrasena != null && contrasena.length() >= 6;
    }
}