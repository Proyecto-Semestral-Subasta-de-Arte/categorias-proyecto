package cl.sda1085.categorias.controller;

import cl.sda1085.categorias.dto.CategoriaRequestDTO;
import cl.sda1085.categorias.dto.CategoriaResponseDTO;
import cl.sda1085.categorias.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
@Tag(name = "Categorías", description = "Controlador para la clasificación de obras de arte y antigüedades.")
public class CategoriaController {

    //Conexión con 'service'
    private final CategoriaService categoriaService;

    @Operation(summary = "Obtener categoría por ID", description = "Retorna el nombre y descripción de una categoría con enlaces de navegación.")
    @ApiResponse(responseCode = "200", description = "Categoría encontrada.")
    @ApiResponse(responseCode = "404", description = "La categoría no existe.")
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtenerCategoriaPorId(@PathVariable Long id) {

        //Buscar el DTO
        CategoriaResponseDTO dto = categoriaService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la categoría con el ID: " + id + "."));

        //HATEOAS
        dto.add(linkTo(methodOn(CategoriaController.class).obtenerCategoriaPorId(id)).withSelfRel());
        dto.add(linkTo(methodOn(CategoriaController.class).listarTodasLasCategorias()).withRel("todas-las-categorias"));

        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Listar todas las categorías.", description = "Devuelve el catálogo completo de tipos de artículos disponibles.")
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodasLasCategorias() {
        return ResponseEntity.ok(categoriaService.obtenerTodas());
    }

    //------------------------------
    //CRUD estándar
    //------------------------------

    //Obtener todas las categorías
    /*@GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(categoriaService.obtenerTodas());
    }*/

    //Obtener categoría por ID
    /*@GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return categoriaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }*/

    //Crear (guardar) nueva categoría
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> guardar(@Valid @RequestBody CategoriaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.guardar(dto));
    }

    //Actualizar categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO dto) {
        return categoriaService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Eliminar categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


    //------------------------------
    //CRUD personalizado
    //------------------------------

    //Buscar por nombre exacto
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<CategoriaResponseDTO> obtenerPorNombre(@PathVariable String nombre) {
        return categoriaService.obtenerPorNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Verificar existencia por nombre
    @GetMapping("/existe/{nombre}")
    public ResponseEntity<Boolean> existePorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(categoriaService.existePorNombre(nombre));
    }

    //Listar categorías ordenadas alfabéticamente
    @GetMapping("/ordenadas")
    public ResponseEntity<List<CategoriaResponseDTO>> obtenerOrdenadas() {
        return ResponseEntity.ok(categoriaService.obtenerOrdenadasPorNombre());
    }

    //Búsqueda parcial por nombre (buscador dinámico)
    @GetMapping("/buscar/nombre/{nombre}")
    public ResponseEntity<List<CategoriaResponseDTO>> buscarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(categoriaService.buscarPorNombreParcial(nombre));
    }

    //Búsqueda parcial por descripción
    @GetMapping("/buscar/descripcion/{descripcion}")
    public ResponseEntity<List<CategoriaResponseDTO>> buscarPorDescripcion(@PathVariable String descripcion) {
        return ResponseEntity.ok(categoriaService.buscarPorDescripcionParcial(descripcion));
    }
}
