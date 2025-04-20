package es.franciscorodalf.sabelotodo.backend.util;

import java.util.regex.Pattern;

public class ValidadorDatosUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);

    /**
     * Valida si el email es correcto.
     *
     * @param email el email a validar
     * @return true si el email es válido, false en caso contrario
     */
    public static boolean esEmailValido(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).find();
    }

    /**
     * Valida si el texto está vacío o es nulo.
     *
     * @param texto el texto a validar
     * @return true si el texto está vacío o es nulo, false en caso contrario
     */
    public static boolean esCampoVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    /**
     * Valida si la contraseña coincide con la confirmación.
     *
     * @param contrasena   la contraseña
     * @param confirmacion la confirmación de la contraseña
     * @return true si coinciden, false en caso contrario
     */
    public static boolean contrasenaCoincide(String contrasena, String confirmacion) {
        return contrasena != null && contrasena.equals(confirmacion);
    }

    /**
     * Valida si la contraseña es segura.
     *
     * @param contrasena la contraseña a validar
     * @return true si es segura, false en caso contrario
     */
    public static boolean esContrasenaSegura(String contrasena) {
        return contrasena != null && contrasena.length() >= 6;
    }
}