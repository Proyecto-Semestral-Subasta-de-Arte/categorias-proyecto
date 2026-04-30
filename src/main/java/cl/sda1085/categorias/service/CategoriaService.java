package cl.sda1085.categorias.service;

import cl.sda1085.categorias.model.Categoria;
import cl.sda1085.categorias.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public List<Categoria> obtenerTodas(){ return categoriaRepository.findAll();}

    public Optional<Categoria> obtenerPorId(Long id){
    return categoriaRepository.findById(id);
}


    public Categoria guardar(Categoria categoria){
    return categoriaRepository.save(categoria);
}


    public void eliminar(Long id){
    categoriaRepository.deleteById(id);
}
}
