package com.example.registrocontacto;

public class Contacto {
    private String nombre;
    private String numero;

    public Contacto(String nombre, String numero) {
        this.nombre = nombre;
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return nombre + " - " + numero;
    }
}