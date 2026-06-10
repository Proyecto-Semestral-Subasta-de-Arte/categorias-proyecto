package cl.sda1085.categorias.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor

@EqualsAndHashCode(callSuper = false)
@Builder  //Permite el uso de patrones de construcción en 'service'.
public class CategoriaResponseDTO extends RepresentationModel<CategoriaResponseDTO> {

    private Long id;
    private String nombre;
    private String descripcion;
}
