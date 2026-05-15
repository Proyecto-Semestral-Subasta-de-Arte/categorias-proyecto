package cl.sda1085.categorias.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder  //Permite el uso de patrones de construcción en 'service'
public class CategoriaResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
}
