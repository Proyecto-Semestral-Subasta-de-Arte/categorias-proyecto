package cl.sda1085.categorias.controller;

import cl.sda1085.categorias.assembler.CategoriaModelAssembler;
import cl.sda1085.categorias.dto.CategoriaRequestDTO;
import cl.sda1085.categorias.dto.CategoriaResponseDTO;
import cl.sda1085.categorias.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    //Incorporación del ensamblador premium
    private final CategoriaModelAssembler assembler;


    //------------------------------
    //CRUD estándar
    //------------------------------

    //Obtener todas las categorías
    @Operation(summary = "Listar todas las categorías.", description = "Devuelve el catálogo completo de tipos de artículos disponibles.")
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodasLasCategorias() {
        // MODIFICACIÓN: Se unifica la salida aplicando el mapeo HATEOAS estructurado en colecciones
        List<CategoriaResponseDTO> resultados = categoriaService.obtenerTodas().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultados);
    }

    //Obtener categoría por ID
    @Operation(summary = "Obtener categoría por ID.", description = "Retorna el nombre y descripción de una categoría con enlaces de navegación.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada."),
            @ApiResponse(responseCode = "404", description = "La categoría no existe.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtenerCategoriaPorId(@PathVariable Long id) {

        //Buscar el DTO
        CategoriaResponseDTO dto = categoriaService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la categoría con el ID: " + id + "."));

        // MODIFICACIÓN: Delegación del aprovisionamiento de enlaces al componente especializado
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    //Crear (guardar) nueva categoría
    @PostMapping
    @Operation(summary = "Crear nueva clasificación.", description = "Inserta una categoría validando que su nombre no se encuentre duplicado en el catálogo.")
    @ApiResponse(responseCode = "201", description = "Categoría creada de forma exitosa.")
    public ResponseEntity<CategoriaResponseDTO> guardar(@Valid @RequestBody CategoriaRequestDTO dto) {
        CategoriaResponseDTO creado = categoriaService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(creado));
    }

    //Actualizar categoría existente
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar clasificación existente.", description = "Modifica los campos descriptivos de una categoría localizándola por su ID.")
    public ResponseEntity<CategoriaResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO dto) {
        return categoriaService.actualizar(id, dto)
                .map(actualizado -> ResponseEntity.ok(assembler.toModel(actualizado)))
                .orElse(ResponseEntity.notFound().build());
    }

    //Eliminar categoría
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover categoría de la base de datos.")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


    //------------------------------
    //CRUD personalizado
    //------------------------------

    //Buscar por nombre exacto
    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Buscar clasificación por su nombre exacto.")
    public ResponseEntity<CategoriaResponseDTO> obtenerPorNombre(@PathVariable String nombre) {
        return categoriaService.obtenerPorNombre(nombre)
                .map(resultado -> ResponseEntity.ok(assembler.toModel(resultado)))
                .orElse(ResponseEntity.notFound().build());
    }

    //Verificar existencia por nombre
    @GetMapping("/existe/{nombre}")
    @Operation(summary = "Validar disponibilidad de un nombre.")
    public ResponseEntity<Boolean> existePorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(categoriaService.existePorNombre(nombre));
    }

    //Listar categorías ordenadas alfabéticamente
    @GetMapping("/ordenadas")
    @Operation(summary = "Listar clasificaciones ordenadas alfabéticamente (A-Z).")
    public ResponseEntity<List<CategoriaResponseDTO>> obtenerOrdenadas() {
        List<CategoriaResponseDTO> resultados = categoriaService.obtenerOrdenadasPorNombre().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultados);
    }

    //Búsqueda parcial por nombre (buscador dinámico)
    @GetMapping("/buscar/nombre/{nombre}")
    @Operation(summary = "Buscador dinámico parcial para Frontend.")
    public ResponseEntity<List<CategoriaResponseDTO>> buscarPorNombre(@PathVariable String nombre) {
        List<CategoriaResponseDTO> resultados = categoriaService.buscarPorNombreParcial(nombre).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultados);
    }

    //Búsqueda parcial por descripción
    @GetMapping("/buscar/descripcion/{descripcion}")
    @Operation(summary = "Filtro avanzado por coincidencia de palabras clave")
    public ResponseEntity<List<CategoriaResponseDTO>> buscarPorDescripcion(@PathVariable String descripcion) {
        List<CategoriaResponseDTO> resultados = categoriaService.buscarPorDescripcionParcial(descripcion).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultados);
    }
}
