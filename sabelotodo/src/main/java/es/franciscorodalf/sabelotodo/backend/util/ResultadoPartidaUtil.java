package es.franciscorodalf.sabelotodo.backend.util;

import es.franciscorodalf.sabelotodo.backend.model.Usuario;

import java.util.List;

public class ResultadoPartidaUtil {
    private Usuario usuario;
    private List<String> categoriasAcertadas;
    private int puntaje;
    private String tiempoTotal;


    public ResultadoPartidaUtil(Usuario usuario, List<String> categoriasAcertadas, int puntaje, String tiempoTotal) {
        this.usuario = usuario;
        this.categoriasAcertadas = categoriasAcertadas;
        this.puntaje = puntaje;
        this.tiempoTotal = tiempoTotal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<String> getCategoriasAcertadas() {
        return categoriasAcertadas;
    }

    public void setCategoriasAcertadas(List<String> categoriasAcertadas) {
        this.categoriasAcertadas = categoriasAcertadas;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public String getTiempoTotal() {
        return tiempoTotal;
    }

    public void setTiempoTotal(String tiempoTotal) {
        this.tiempoTotal = tiempoTotal;
    }
}
