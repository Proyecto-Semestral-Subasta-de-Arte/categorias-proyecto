package cl.sda1085.categorias.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo requerido (JSON) para registrar o actualizar una categoría de catalogación.")

public class CategoriaRequestDTO {

    @Schema(description = "Nombre único de la clasificación de arte.", example = "Pinturas", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "El nombre no puede estar vacío.")
    @Size(max = 100, message = "El nombre de la categoría no puede superar los 100 caracteres.")
    private String nombre;

    @Schema(description = "Detalle o reseña del tipo de ítems que agrupa.", example = "Óleos, lienzos y acuarelas de diversas épocas.", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "La descripción no puede estar vacía.")
    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres.")
    private String descripcion;
}
