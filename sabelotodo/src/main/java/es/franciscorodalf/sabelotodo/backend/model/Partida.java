package es.franciscorodalf.sabelotodo.backend.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Partida {
    private int id;
    private Usuario usuario;
    private int puntaje;
    private String dificultad;
    private LocalDateTime fecha;

    public Partida() {
    }

    public Partida(int id, Usuario usuario, int puntaje, String dificultad, LocalDateTime fecha) {
        this.id = id;
        this.usuario = usuario;
        this.puntaje = puntaje;
        this.dificultad = dificultad;
        this.fecha = fecha;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int getPuntaje() {
        return this.puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public String getDificultad() {
        return this.dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public LocalDateTime getFecha() {
        return this.fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Partida id(int id) {
        setId(id);
        return this;
    }

    public Partida usuario(Usuario usuario) {
        setUsuario(usuario);
        return this;
    }

    public Partida puntaje(int puntaje) {
        setPuntaje(puntaje);
        return this;
    }

    public Partida dificultad(String dificultad) {
        setDificultad(dificultad);
        return this;
    }

    public Partida fecha(LocalDateTime fecha) {
        setFecha(fecha);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Partida)) {
            return false;
        }
        Partida partida = (Partida) o;
        return id == partida.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", usuario='" + getUsuario() + "'" +
                ", puntaje='" + getPuntaje() + "'" +
                ", dificultad='" + getDificultad() + "'" +
                ", fecha='" + getFecha() + "'" +
                "}";
    }

}
