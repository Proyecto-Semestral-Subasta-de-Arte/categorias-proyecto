package cl.sda1085.categorias.controller;

import cl.sda1085.categorias.dto.CategoriaRequestDTO;
import cl.sda1085.categorias.dto.CategoriaResponseDTO;
import cl.sda1085.categorias.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    //Conexión con 'service'
    private final CategoriaService categoriaService;


    //------------------------------
    //CRUD estándar
    //------------------------------

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(categoriaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return categoriaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> guardar(@Valid @RequestBody CategoriaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO dto) {
        return categoriaService.actualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    //------------------------------
    //CRUD estándar
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

    //Búsqueda parcial por nombre (Buscador dinámico)
    @GetMapping("/buscar/nombre")
    public ResponseEntity<List<CategoriaResponseDTO>> buscarPorNombre(@RequestParam String q) {
        return ResponseEntity.ok(categoriaService.buscarPorNombreParcial(q));
    }

    //Búsqueda parcial por descripción
    @GetMapping("/buscar/descripcion")
    public ResponseEntity<List<CategoriaResponseDTO>> buscarPorDescripcion(@RequestParam String q) {
        return ResponseEntity.ok(categoriaService.buscarPorDescripcionParcial(q));
    }
}
