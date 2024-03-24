package com.example.practica1spring;

import com.example.practica1spring.entidades.Alumno;
import com.example.practica1spring.repositorios.AlumnoRepositorio;
import com.example.practica1spring.repositorios.LibroRepositorio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Practica1SpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(Practica1SpringApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(AlumnoRepositorio alumnoRepositorio, LibroRepositorio libroRepositorio){

        return  (args) -> {
            Controller controller = new Controller();

            controller.pruebaInsercion(alumnoRepositorio,libroRepositorio);


        };
    }

}
