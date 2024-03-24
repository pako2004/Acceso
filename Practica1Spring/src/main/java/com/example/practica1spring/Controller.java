package com.example.practica1spring;

import com.example.practica1spring.entidades.Alumno;
import com.example.practica1spring.entidades.Libro;
import com.example.practica1spring.repositorios.AlumnoRepositorio;
import com.example.practica1spring.repositorios.LibroRepositorio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Controller {


    public Controller() {

    }

    public void pruebaInsercion(AlumnoRepositorio alumnoRepositorio, LibroRepositorio libroRepositorio)
    {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date fechaN1= null;
        try {
            fechaN1 = sdf.parse("2004-12-24");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Alumno alumno=new Alumno(null,"Juan","Moreno Márquez",fechaN1,"34189400K");
        alumno=alumnoRepositorio.save(alumno);

        Libro libro=new Libro(null,"El Silmarillion",alumno);
        libroRepositorio.save(libro);
    }


}
