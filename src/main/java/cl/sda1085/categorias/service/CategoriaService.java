package cl.sda1085.categorias.service;

import cl.sda1085.categorias.dto.CategoriaRequestDTO;
import cl.sda1085.categorias.dto.CategoriaResponseDTO;
import cl.sda1085.categorias.model.Categoria;
import cl.sda1085.categorias.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    private CategoriaResponseDTO mapToDTO(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion()
        );
    }


    public List<CategoriaResponseDTO> obtenerTodas(){
        return categoriaRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

    }


    public Optional<CategoriaResponseDTO> obtenerPorId(Long id){
        return categoriaRepository.findById(id)
                .map(this::mapToDTO);
}


    public CategoriaResponseDTO guardar(CategoriaRequestDTO dto){

        Categoria categoria = new Categoria(
                null,
                dto.getNombre(),
                dto.getDescripcion()
        );

        return mapToDTO(categoriaRepository.save(categoria));
}

    public Optional<CategoriaResponseDTO> actualizar(Long id,CategoriaRequestDTO dto){
        return categoriaRepository.findById(id).map(existente -> {
            existente.setNombre(dto.getNombre());
            existente.setDescripcion(dto.getDescripcion());

            return mapToDTO(categoriaRepository.save(existente));

        });
    }

    public void eliminar(Long id){
    categoriaRepository.deleteById(id);
}
}
