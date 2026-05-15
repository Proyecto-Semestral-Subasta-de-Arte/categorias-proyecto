package cl.sda1085.categorias.config;

import cl.sda1085.categorias.model.Categoria;
import cl.sda1085.categorias.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    //Conexión con 'repository'
    private final CategoriaRepository categoriaRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (categoriaRepository.count() > 0) {
            log.info("La base de datos de categorías ya contiene datos. Omitiendo inicialización.");
            return;
        }
        log.info("Iniciando la creación de 8 categorías para el ecosistema de subastas...");

        //Pinturas (Óleo del Siglo XVIII)
        Categoria c1 = Categoria.builder()
                .nombre("Pinturas")
                .descripcion("Óleos, lienzos y acuarelas de diversas épocas y maestros.")
                .build();

        //Armamento Histórico (Espada Medieval y Armadura Samurai)
        Categoria c2 = Categoria.builder()
                .nombre("Armamento Histórico")
                .descripcion("Piezas de combate, armaduras y equipo militar de valor antiguo.")
                .build();

        //Arqueología (Vasija Precolombina)
        Categoria c3 = Categoria.builder()
                .nombre("Arqueología")
                .descripcion("Artefactos y restos de civilizaciones antiguas y precolombinas.")
                .build();

        //Numismática (Monedas de Oro)
        Categoria c4 = Categoria.builder()
                .nombre("Numismática")
                .descripcion("Colecciones de monedas, billetes y medallas de relevancia histórica.")
                .build();

        //Relojería Vintage (Reloj de Bolsillo)
        Categoria c5 = Categoria.builder()
                .nombre("Relojería Vintage")
                .descripcion("Instrumentos de precisión mecánicos y relojes de colección.")
                .build();

        //Cartografía (Mapa Cartográfico Original)
        Categoria c6 = Categoria.builder()
                .nombre("Cartografía")
                .descripcion("Mapas, globos terráqueos y documentos geográficos históricos.")
                .build();

        //Esculturas
        Categoria c7 = Categoria.builder()
                .nombre("Esculturas")
                .descripcion("Obras tridimensionales en mármol, bronce y otros materiales nobles.")
                .build();

        //Mobiliario Antiguo
        Categoria c8 = Categoria.builder()
                .nombre("Mobiliario Antiguo")
                .descripcion("Muebles y objetos decorativos de diseño clásico y gran antigüedad.")
                .build();

        //Lista de categorías
        categoriaRepository.saveAll(List.of(c1, c2, c3, c4, c5, c6, c7, c8));

        log.info("Se han precargado exitosamente 8 categorías vinculadas al inventario de subastas.");
    }
}
