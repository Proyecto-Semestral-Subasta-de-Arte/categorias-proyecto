package cl.sda1085.categorias.service;

import cl.sda1085.categorias.dto.CategoriaResponseDTO;
import cl.sda1085.categorias.model.Categoria;
import cl.sda1085.categorias.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    //Categorías del DataInitializer
    private Categoria c1Mock;
    private Categoria c2Mock;
    private Categoria c7Mock;

    @BeforeEach
    void setUp() {
        c1Mock = Categoria.builder().id(1L).nombre("Pinturas").descripcion("Óleos, lienzos y acuarelas...").build();
        c2Mock = Categoria.builder().id(2L).nombre("Armamento Histórico").descripcion("Piezas de combate, armaduras...").build();
        c7Mock = Categoria.builder().id(7L).nombre("Esculturas").descripcion("Obras tridimensionales en mármol...").build();
    }

    @Test
    @DisplayName("Debería retornar el DTO válido para pinturas.")
    void obtenerCategoria1Exitoso() {

        //ARRANGE
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(c1Mock));

        //ACT
        CategoriaResponseDTO resultado = categoriaService.obtenerPorId(1L).get();

        //ASSERT
        assertNotNull(resultado);
        assertEquals("Pinturas", resultado.getNombre());
        verify(categoriaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Debería retornar el DTO válido para armamento.")
    void obtenerCategoria2Exitoso() {

        //ARRANGE
        when(categoriaRepository.findById(2L)).thenReturn(Optional.of(c2Mock));

        //ACT
        CategoriaResponseDTO resultado = categoriaService.obtenerPorId(2L).get();

        //ASSERT
        assertNotNull(resultado);
        assertEquals("Armamento Histórico", resultado.getNombre());
        verify(categoriaRepository, times(1)).findById(2L);
    }

    @Test
    @DisplayName("Debería retornar el DTO válido para esculturas.")
    void obtenerCategoria7Exitoso() {

        //ARRANGE
        when(categoriaRepository.findById(7L)).thenReturn(Optional.of(c7Mock));

        //ACT
        CategoriaResponseDTO resultado = categoriaService.obtenerPorId(7L).get();

        //ASSERT
        assertNotNull(resultado);
        assertEquals("Esculturas", resultado.getNombre());
        verify(categoriaRepository, times(1)).findById(7L);
    }

    @Test
    @DisplayName("Debería lanzar excepción si la categoría no existe.")
    void obtenerCategoriaInexistente() {

        //ARRANGE
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        //ACT
        Optional<CategoriaResponseDTO> resultado = categoriaService.obtenerPorId(99L);

        //ASSERT
        assertNotNull(resultado, "El contenedor de retorno no debería ser nulo.");
        assertTrue(resultado.isEmpty(), "El Optional debe estar vacío si la categoría no existe en la base de datos.");

        verify(categoriaRepository, times(1)).findById(99L);
    }
}
