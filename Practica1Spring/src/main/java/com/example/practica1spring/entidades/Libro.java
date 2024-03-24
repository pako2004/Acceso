package com.example.practica1spring.entidades;

import jakarta.persistence.*;
@Entity
public class Libro {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLibro;
    private String nombre;
    @ManyToOne/*Con esta anotación indicamos que esta variable va a ser la clave foránea que referencia a la tabla
    alumno, indicando además que es una relación de MUCHOS a UNO*/
    @JoinColumn(name="idAlumno")/*Aquí indicamos el campo de la tabla alumno al que hace referencia nuestra clave
    foránea*/
    private Alumno alumno;

    public Libro() {

    }

    public Libro(Long idLibro, String nombre, Alumno alumno) {
        this.idLibro = idLibro;
        this.nombre = nombre;
        this.alumno = alumno;
    }

    public Long getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

}
