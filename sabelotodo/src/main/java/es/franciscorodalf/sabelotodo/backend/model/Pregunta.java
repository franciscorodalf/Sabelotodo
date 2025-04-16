package es.franciscorodalf.sabelotodo.backend.model;

import java.util.Objects;

public class Pregunta {
    private int id;
    private String pregunta;
    private String opcionA;
    private String opcionB;
    private String opcionC;
    private String opcionD;
    private String respuestaCorrecta;
    private Categoria categoria;

    public Pregunta() {
    }

    public Pregunta(int id, String pregunta, String opcionA, String opcionB, String opcionC, String opcionD,
            String respuestaCorrecta, Categoria categoria) {
        this.id = id;
        this.pregunta = pregunta;
        this.opcionA = opcionA;
        this.opcionB = opcionB;
        this.opcionC = opcionC;
        this.opcionD = opcionD;
        this.respuestaCorrecta = respuestaCorrecta;
        this.categoria = categoria;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPregunta() {
        return this.pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getOpcionA() {
        return this.opcionA;
    }

    public void setOpcionA(String opcionA) {
        this.opcionA = opcionA;
    }

    public String getOpcionB() {
        return this.opcionB;
    }

    public void setOpcionB(String opcionB) {
        this.opcionB = opcionB;
    }

    public String getOpcionC() {
        return this.opcionC;
    }

    public void setOpcionC(String opcionC) {
        this.opcionC = opcionC;
    }

    public String getOpcionD() {
        return this.opcionD;
    }

    public void setOpcionD(String opcionD) {
        this.opcionD = opcionD;
    }

    public String getRespuestaCorrecta() {
        return this.respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public Categoria getCategoria() {
        return this.categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Pregunta id(int id) {
        setId(id);
        return this;
    }

    public Pregunta pregunta(String pregunta) {
        setPregunta(pregunta);
        return this;
    }

    public Pregunta opcionA(String opcionA) {
        setOpcionA(opcionA);
        return this;
    }

    public Pregunta opcionB(String opcionB) {
        setOpcionB(opcionB);
        return this;
    }

    public Pregunta opcionC(String opcionC) {
        setOpcionC(opcionC);
        return this;
    }

    public Pregunta opcionD(String opcionD) {
        setOpcionD(opcionD);
        return this;
    }

    public Pregunta respuestaCorrecta(String respuestaCorrecta) {
        setRespuestaCorrecta(respuestaCorrecta);
        return this;
    }

    public Pregunta categoria(Categoria categoria) {
        setCategoria(categoria);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Pregunta)) {
            return false;
        }
        Pregunta pregunta = (Pregunta) o;
        return id == pregunta.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", pregunta='" + getPregunta() + "'" +
                ", opcionA='" + getOpcionA() + "'" +
                ", opcionB='" + getOpcionB() + "'" +
                ", opcionC='" + getOpcionC() + "'" +
                ", opcionD='" + getOpcionD() + "'" +
                ", respuestaCorrecta='" + getRespuestaCorrecta() + "'" +
                ", categoria='" + getCategoria();
    }

}
