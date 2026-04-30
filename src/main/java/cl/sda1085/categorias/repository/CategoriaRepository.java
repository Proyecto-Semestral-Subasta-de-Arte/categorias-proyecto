package cl.sda1085.categorias.repository;

import cl.sda1085.categorias.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
}
