package cl.sda1085.categorias.assembler;

import cl.sda1085.categorias.controller.CategoriaController;
import cl.sda1085.categorias.dto.CategoriaResponseDTO;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CategoriaModelAssembler implements RepresentationModelAssembler<CategoriaResponseDTO, CategoriaResponseDTO> {

    @Override
    public CategoriaResponseDTO toModel(CategoriaResponseDTO dto) {

        //Enlace autorreferencial al recurso atómico -> GET /api/categorias/{id}
        dto.add(linkTo(methodOn(CategoriaController.class).obtenerCategoriaPorId(dto.getId())).withSelfRel());

        //Enlace alternativo al catálogo completo -> GET /api/categorias
        dto.add(linkTo(methodOn(CategoriaController.class).listarTodasLasCategorias()).withRel("todas-las-categorias"));

        return dto;
    }
}
