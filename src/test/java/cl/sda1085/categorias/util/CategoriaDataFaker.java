package cl.sda1085.categorias.util;

import cl.sda1085.categorias.dto.CategoriaRequestDTO;
import cl.sda1085.categorias.dto.CategoriaResponseDTO;
import cl.sda1085.categorias.model.Categoria;
import net.datafaker.Faker;

public class CategoriaDataFaker {
    private static final Faker faker = new Faker();

    public static Categoria createFakeEntity() {
        return Categoria.builder()
                .id(faker.number().randomNumber(4, false))
                .nombre(faker.commerce().department() + faker.number().randomNumber(2, false))
                .descripcion(faker.lorem().sentence(5))
                .build();
    }

    public static CategoriaRequestDTO createFakeRequestDTO() {
        return new CategoriaRequestDTO(
                faker.commerce().department() + faker.number().randomNumber(3, false),
                faker.lorem().sentence(5)
        );
    }

    public static CategoriaResponseDTO createFakeResponseDTO(Categoria entity) {
        return CategoriaResponseDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .build();
    }
}
