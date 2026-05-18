# Microservicio de Categorías ('categorías')

## Integrantes
* **Gonzalo Hormazábal**
* **Geraldinne González**


## Descripción
Módulo clasificador que organiza semánticamente las obras de arte y antigüedades del ecosistema. Provee buscadores dinámicos y estructuras DTO validadas.

* **Puerto:** `8090`
* **Base de Datos:** `categorias_db` (MySQL)


## Funcionalidades Clave
* Precarga controlada de 8 categorías estratégicas asociadas a subastas de alto valor (Pinturas, Armamento Histórico, Arqueología, Numismática, etc.).
* Endpoints avanzados para búsquedas por similitudes parciales con `@RequestParam`.
* Bloqueo contra payloads corruptos mediante interceptores `@Valid` en los DTOs.


## Configuración (`application.properties`)
* server.port=8090
* spring.datasource.url=jdbc:mysql://localhost:3306/categorias_db
* spring.datasource.username=root
* spring.datasource.password=
* spring.jpa.hibernate.ddl-auto=update
* logging.level.cl.sda1085.categorias=DEBUG


## Pasos para Ejecutar

1. Preparación de la Base de Datos
Antes de ejecutar el servicio, crear la conexión a la base de datos de MySQL (XAMPP) corriendo en el puerto 3306 y con el nombre 'categorias_db'.

2. Verificación de Credenciales
Revisar que el archivo application.properties tenga por defecto, usuario root y contraseña vacía.

3. Lanzamiento del Microservicio
Ejecutar (run) la clase principal con la anotación @SpringBootApplication (CategoriasApplication.java).

4. Reglas de Seguridad
Al consumir los endpoints en Postman, ten en cuenta el comportamiento de la cadena de filtros de seguridad:

* Consulta de Filtros (GET /api/categorias): Listar las agrupaciones del catálogo es completamente abierto para garantizar la libre navegación de la plataforma (No Auth).
* Mantenimiento de Estructura (POST / PUT / DELETE): Crear, renombrar o dar de baja categorías del catálogo de arte es facultad exclusiva de los administradores del sistema, requiriendo de forma obligatoria Basic Auth con el rol de ADMIN.
