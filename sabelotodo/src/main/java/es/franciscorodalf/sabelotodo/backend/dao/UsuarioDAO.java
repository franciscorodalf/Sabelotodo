package es.franciscorodalf.sabelotodo.backend.dao;

import es.franciscorodalf.sabelotodo.backend.model.Usuario;
import es.franciscorodalf.sabelotodo.backend.model.abstractas.Conexion;

import java.sql.*;
import java.util.ArrayList;

public class UsuarioDAO extends Conexion {

    public UsuarioDAO() {
        super();
    }

    public Usuario login(String identificador, String contrasenia) {
        Usuario usuario = null;

        String sql = "SELECT * FROM usuarios WHERE (correo = ? OR nombre = ?) AND contrasenia = ?";

        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, identificador);
            stmt.setString(2, identificador);
            stmt.setString(3, contrasenia);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("contrasenia"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("❌ Error al iniciar sesión con identificador flexible");
            e.printStackTrace();
        } finally {
            try {
                cerrar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return usuario;
    }

    public boolean registrar(Usuario usuario) {
        boolean exito = false;
        String sql = "INSERT INTO usuarios (nombre, correo, contrasenia) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getPassword());

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                exito = true;
            }

            stmt.close();
        } catch (SQLException e) {
            System.err.println("❌ Error al registrar usuario");
            e.printStackTrace();
        } finally {
            try {
                cerrar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return exito;
    }

    public Usuario buscarPorCorreo(String email) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE correo = ?";

        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("contrasenia"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar usuario por correo");
            e.printStackTrace();
        } finally {
            try {
                cerrar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return usuario;
    }

    public boolean actualizar(Usuario usuario) {
        boolean exito = false;
        String sql = "UPDATE usuarios SET nombre = ?, correo = ?, contrasenia = ? WHERE id = ?";

        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getPassword());
            stmt.setInt(4, usuario.getId());

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                exito = true;
            }

            stmt.close();
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar usuario");
            e.printStackTrace();
        } finally {
            try {
                cerrar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return exito;
    }

    public ArrayList<Usuario> obtenerTodos() {
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        String sql = "SELECT * FROM usuarios";

        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("contrasenia"));
                usuarios.add(usuario);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener usuarios");
            e.printStackTrace();
        } finally {
            try {
                cerrar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return usuarios;
    }

    public Usuario buscarPorUsernameYCorreo(String username, String correo) {
        Usuario usuario = null;
        String sql = "SELECT * FROM usuarios WHERE nombre = ? AND correo = ?";

        try {
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, correo);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        rs.getString("contrasenia"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("❌ Error al buscar usuario por nombre y correo");
            e.printStackTrace();
        } finally {
            try {
                cerrar();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return usuario;
    }

}
