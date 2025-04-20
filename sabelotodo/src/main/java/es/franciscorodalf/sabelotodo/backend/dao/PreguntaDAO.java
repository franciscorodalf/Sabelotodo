package es.franciscorodalf.sabelotodo.backend.dao;

import es.franciscorodalf.sabelotodo.backend.model.Categoria;
import es.franciscorodalf.sabelotodo.backend.model.Pregunta;
import es.franciscorodalf.sabelotodo.backend.model.abstractas.Conexion;

import java.sql.*;

public class PreguntaDAO extends Conexion {

    public PreguntaDAO() {
        super();
    }
    /**
     * Inserta una nueva pregunta en la base de datos.
     * @param pregunta La pregunta a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public Pregunta obtenerPreguntaAleatoriaPorCategoria(String nombreCategoria) {
        Pregunta pregunta = null;

        String sql = "SELECT p.*, c.id AS cat_id, c.nombre AS cat_nombre " +
                "FROM preguntas p " +
                "JOIN categorias c ON p.categoria_id = c.id " +
                "WHERE c.nombre = ? " +
                "ORDER BY RANDOM() LIMIT 1";

        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, nombreCategoria);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Categoria categoria = new Categoria(
                        rs.getInt("cat_id"),
                        rs.getString("cat_nombre"));

                pregunta = new Pregunta(
                        rs.getInt("id"),
                        rs.getString("enunciado"), // ✅ CORREGIDO
                        rs.getString("opcion_a"),
                        rs.getString("opcion_b"),
                        rs.getString("opcion_c"),
                        rs.getString("opcion_d"),
                        rs.getString("respuesta_correcta"),
                        categoria);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener pregunta por categoría");
            e.printStackTrace();
        } finally {
            try {
                cerrar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return pregunta;
    }
}
