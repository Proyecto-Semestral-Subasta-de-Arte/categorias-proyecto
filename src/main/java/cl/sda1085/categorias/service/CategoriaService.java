package cl.sda1085.categorias.service;

import cl.sda1085.categorias.dto.CategoriaRequestDTO;
import cl.sda1085.categorias.dto.CategoriaResponseDTO;
import cl.sda1085.categorias.model.Categoria;
import cl.sda1085.categorias.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoriaService {

    //Conexión con 'repository'
    private final CategoriaRepository categoriaRepository;

    //Método de apoyo para convertir entidad a DTO
    private CategoriaResponseDTO mapToDTO(Categoria categoria) {
        return CategoriaResponseDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .build();
    }


    //CRUD estándar

    //Obtener todas las categorias
    public List<CategoriaResponseDTO> obtenerTodas(){
        log.info("Consultando todas las categorías");
        return categoriaRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    //Obtener categoría por ID
    public Optional<CategoriaResponseDTO> obtenerPorId(Long id){
        log.info("Buscando categoría con ID: {}", id);
        return categoriaRepository.findById(id)
                .map(this::mapToDTO);
    }

    //Crear (guardar) nueva categoría
    @Transactional
    public CategoriaResponseDTO guardar(CategoriaRequestDTO dto){
        if(categoriaRepository.existsByNombre(dto.getNombre())) {
            log.error("Error: La categoría '{}' ya existe", dto.getNombre());
            throw new RuntimeException("Ya existe una categoría con ese nombre.");
        }

        Categoria categoria = Categoria.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .build();

        log.info("Guardando nueva categoría: {}", dto.getNombre());
        return mapToDTO(categoriaRepository.save(categoria));
    }

    //Actualizar categoría existente
    @Transactional
    public Optional<CategoriaResponseDTO> actualizar(Long id, CategoriaRequestDTO dto){
        log.info("Actualizando categoría ID: {}", id);
        return categoriaRepository.findById(id).map(existente -> {
            existente.setNombre(dto.getNombre());
            existente.setDescripcion(dto.getDescripcion());

            return mapToDTO(categoriaRepository.save(existente));
        });
    }

    //Eliminar categoría
    @Transactional
    public void eliminar(Long id){
        log.warn("Eliminando categoría ID: {}", id);
        categoriaRepository.deleteById(id);
}


    //CRUD personalizado

    //Buscar por nombre de categoría
    public  Optional <CategoriaResponseDTO> obtenerPorNombre (String nombre) {
        return categoriaRepository.findByNombre(nombre).map(this::mapToDTO);
    }

    //Verificar si existe una categoría por nombre
    public boolean existePorNombre (String nombre) {
        return categoriaRepository.existsByNombre(nombre);
    }

    //Listar categorias ordenadas alfabéticamente
    public List <CategoriaResponseDTO> obtenerOrdenadasPorNombre() {
        return categoriaRepository.findAllByOrderByNombreAsc()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    //Búsqueda parcial por nombre (ignora mayúsculas / minúsculas)
    public List<CategoriaResponseDTO> buscarPorNombreParcial(String nombre) {
        log.info("Buscando categorías que contengan: {}", nombre);
        return categoriaRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    //Búsqueda parcial por descripción**
    public List<CategoriaResponseDTO> buscarPorDescripcionParcial(String descripcion) {
        log.info("Filtrando categorías por descripción: {}", descripcion);
        return categoriaRepository.findByDescripcionContainingIgnoreCase(descripcion)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
