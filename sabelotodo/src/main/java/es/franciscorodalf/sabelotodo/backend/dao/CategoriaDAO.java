package es.franciscorodalf.sabelotodo.backend.dao;

import es.franciscorodalf.sabelotodo.backend.model.Categoria;
import es.franciscorodalf.sabelotodo.backend.model.abstractas.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO extends Conexion {

    public CategoriaDAO() {
        super();
    }

    /**
     * Inserta una nueva categoría en la base de datos.
     * @param categoria La categoría a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public List<Categoria> obtenerTodas() {
        List<Categoria> categorias = new ArrayList<Categoria>();
        String sql = "SELECT * FROM categorias";

        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Categoria categoria = new Categoria(
                        rs.getInt("id"),
                        rs.getString("nombre"));
                categorias.add(categoria);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener categorías");
            e.printStackTrace();
        } finally {
            try {
                cerrar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return categorias;
    }

    /**
     * Inserta una nueva categoría en la base de datos.
     * @param categoria La categoría a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     */
    public Categoria buscarPorNombre(String nombre) {
        Categoria categoria = null;
        String sql = "SELECT * FROM categorias WHERE nombre = ?";

        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, nombre);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                categoria = new Categoria(
                        rs.getInt("id"),
                        rs.getString("nombre"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar categoría por nombre");
            e.printStackTrace();
        } finally {
            try {
                cerrar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return categoria;
    }
}
