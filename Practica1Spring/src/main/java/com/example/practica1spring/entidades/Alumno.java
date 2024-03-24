package com.example.practica1spring.entidades;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlumno;

    private String nombre;
    private String apellidos;

    @Column (nullable = true, columnDefinition = "date")
    private Date fechaNacimiento;

    @Column(unique = true)/*Con esta anotación indicamos que el campo correspondiente en la tabla es único*/
    private String dni;

    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL)/*Con esta anotación indicamos que la tabla alumno
    tendrá una relación UNO a MUCHOS con la tabla libro. "mappedBy" referencia a la variable que funciona como clave
    foránea en la tabla secundaria y "cascade" indica la estrategia que seguimos cuando actualizamos o eliminamos
    registros en la tabla principal*/
    private Set<Libro> libros= new HashSet<>();


    public Alumno()
    {

    }
    public Alumno(Long idAlumno, String nombre, String apellidos, Date fechaNacimiento, String dni) {
        this.idAlumno = idAlumno;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.dni = dni;
    }

    public Long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    public void setLibros(Set<Libro> libros) {
        this.libros = libros;
    }



}
