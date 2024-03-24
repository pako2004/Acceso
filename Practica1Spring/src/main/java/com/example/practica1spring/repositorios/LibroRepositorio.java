package com.example.practica1spring.repositorios;

import com.example.practica1spring.entidades.Libro;
import org.springframework.data.repository.CrudRepository;

public interface LibroRepositorio extends CrudRepository<Libro,Long> {
}
