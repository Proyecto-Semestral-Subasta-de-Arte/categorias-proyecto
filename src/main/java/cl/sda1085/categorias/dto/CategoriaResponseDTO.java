package cl.sda1085.categorias.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor

@EqualsAndHashCode(callSuper = false)
@Builder  //Permite el uso de patrones de construcción en 'service'.
@Schema(description = "Estructura de salida que representa una categoría enriquecida.")

public class CategoriaResponseDTO extends RepresentationModel<CategoriaResponseDTO> {

    @Schema(description = "Identificador único incremental autogenerado en la tabla de categorías.", example = "1")
    private Long id;

    @Schema(description = "Nombre oficial de la clasificación almacenado.", example = "Pinturas")
    private String nombre;

    @Schema(description = "Reseña descriptiva de las obras asociadas.", example = "Óleos, lienzos y acuarelas de diversas épocas y maestros.")
    private String descripcion;
}
