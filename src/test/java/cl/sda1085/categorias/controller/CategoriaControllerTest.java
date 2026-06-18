package cl.sda1085.categorias.controller;

import cl.sda1085.categorias.assembler.CategoriaModelAssembler;
import cl.sda1085.categorias.dto.CategoriaRequestDTO;
import cl.sda1085.categorias.dto.CategoriaResponseDTO;
import cl.sda1085.categorias.model.Categoria;
import cl.sda1085.categorias.service.CategoriaService;
import cl.sda1085.categorias.util.CategoriaDataFaker;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoriaController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("CategoriaController – Tests Unitarios")

class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private CategoriaService categoriaService;

    @MockitoBean
    private CategoriaModelAssembler assembler;

    private CategoriaResponseDTO responseDTO;
    private CategoriaRequestDTO requestDTO;
    private Long idTest;

    @BeforeEach
    void setUp() {
        Categoria entidadFake = CategoriaDataFaker.createFakeEntity();
        idTest = entidadFake.getId();

        responseDTO = CategoriaDataFaker.createFakeResponseDTO(entidadFake);
        requestDTO = CategoriaDataFaker.createFakeRequestDTO();

        //Configuración estandarizada del comportamiento del simulador del ensamblador
        given(assembler.toModel(any(CategoriaResponseDTO.class))).willAnswer(invocation -> {
            CategoriaResponseDTO dto = invocation.getArgument(0);
            dto.removeLinks();
            dto.add(Link.of("/api/categorias/" + dto.getId()).withSelfRel());
            dto.add(Link.of("/api/categorias").withRel("todas-las-categorias"));
            return dto;
        });
    }

    @Test
    @DisplayName("GET /api/categorias/{id} → 200 OK con links HAL HATEOAS si existe.")
    void obtenerPorId_exitoso() throws Exception {
        given(categoriaService.obtenerPorId(idTest)).willReturn(Optional.of(responseDTO));

        mockMvc.perform(get("/api/categorias/{id}", idTest)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(idTest.intValue())))
                .andExpect(jsonPath("$.nombre", is(responseDTO.getNombre())))
                .andExpect(jsonPath("$._links.self.href", containsString("/api/categorias/" + idTest)));

        verify(categoriaService).obtenerPorId(idTest);
    }

    @Test
    @DisplayName("POST /api/categorias → 201 Created al guardar nueva clasificacion.")
    void guardar_exitoso() throws Exception {
        given(categoriaService.guardar(any(CategoriaRequestDTO.class))).willReturn(responseDTO);

        mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(idTest.intValue())));

        verify(categoriaService).guardar(any(CategoriaRequestDTO.class));
    }

    @Test
    @DisplayName("PUT /api/categorias/{id} → 200 OK al modificar registro.")
    void actualizar_exitoso() throws Exception {

        given(categoriaService.actualizar(eq(idTest), any(CategoriaRequestDTO.class))).willReturn(Optional.of(responseDTO));

        mockMvc.perform(put("/api/categorias/{id}", idTest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET /api/categorias/existe/{nombre} → Retorna verificacion booleana.")
    void existePorNombre_retornaTrue() throws Exception {
        String nombreTest = "Esculturas";
        given(categoriaService.existePorNombre(nombreTest)).willReturn(true);

        mockMvc.perform(get("/api/categorias/existe/{nombre}", nombreTest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        verify(categoriaService).existePorNombre(nombreTest);
    }
}
