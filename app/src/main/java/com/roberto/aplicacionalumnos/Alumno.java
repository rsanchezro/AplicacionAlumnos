package com.roberto.aplicacionalumnos;

class Alumno {

    private int nummatricula;
    private String nombre;
    private String foto;

    public Alumno(int nummatricula, String nombre, String foto) {
        this.nummatricula = nummatricula;
        this.nombre = nombre;
        this.foto = foto;
    }

    public int getNummatricula() {
        return nummatricula;
    }

    public void setNummatricula(int nummatricula) {
        this.nummatricula = nummatricula;
    }



    public String getNombre() {
        return nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }




}
