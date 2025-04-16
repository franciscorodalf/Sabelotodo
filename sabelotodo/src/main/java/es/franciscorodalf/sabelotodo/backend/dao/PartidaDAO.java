package es.franciscorodalf.sabelotodo.backend.dao;

import es.franciscorodalf.sabelotodo.backend.model.Partida;
import es.franciscorodalf.sabelotodo.backend.model.Usuario;
import es.franciscorodalf.sabelotodo.backend.model.abstractas.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidaDAO extends Conexion {

    public PartidaDAO() {
        super();
    }

    /**
     * Obtiene todas las partidas de un usuario específico.
     * @param usuarioId ID del usuario.
     * @return Lista de partidas del usuario.
     */
    public List<Partida> obtenerPorUsuario(int usuarioId) {
        List<Partida> partidas = new ArrayList<Partida>();
        String sql = "SELECT * FROM partidas WHERE usuario_id = ? ORDER BY fecha DESC";

        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setInt(1, usuarioId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Partida partida = new Partida();
                partida.setId(rs.getInt("id"));

                Usuario usuario = new Usuario();
                usuario.setId(usuarioId);
                partida.setUsuario(usuario);

                partida.setPuntaje(rs.getInt("puntuacion"));

                Timestamp timestamp = rs.getTimestamp("fecha");
                if (timestamp != null) {
                    partida.setFecha(timestamp.toLocalDateTime());
                }

                partidas.add(partida);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener partidas del usuario");
            e.printStackTrace();
        } finally {
            try {
                cerrar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return partidas;
    }

    /**
     * Registra una partida en la base de datos.
     * @param usuarioId ID del usuario que jugó.
     * @param puntuacion Puntos obtenidos.
     * @param categoriasAcertadas Número de categorías acertadas.
     */
    public void registrarPartida(int usuarioId, int puntuacion, int categoriasAcertadas) {
        String sql = "INSERT INTO partidas (usuario_id, puntuacion, categorias_acertadas) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, puntuacion);
            stmt.setInt(3, categoriasAcertadas);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Error al registrar partida");
            e.printStackTrace();
        } finally {
            try {
                cerrar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
