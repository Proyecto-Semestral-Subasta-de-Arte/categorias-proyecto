package cl.sda1085.categorias.repository;

import cl.sda1085.categorias.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria,Long> {


    // Buscar por nombre exacto
    Optional<Categoria> findByNombre (String nombre);

    // Verificar si existe una categoría por nombre
    boolean existsByNombre (String nombre);

    //Ordenar categorias por orden alfabeticos
    List <Categoria> findAllByOrderByNombreAsc();
}
