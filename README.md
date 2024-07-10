# 📚 ForoHub API

ForoHub es una API RESTful, creada para el programa ONE de Alura y Oracle, que permite gestionar un foro de discusión sobre diversos temas dentro de cursos. La API permite a los usuarios interactuar con tópicos, cursos y respuestas. Además, proporciona autenticación y autorización mediante tokens JWT.

## 🛠️ Tecnologías Utilizadas

- Java 17
- Spring Boot 3.3.1
- Spring Data JPA
- Maven
- Flyway
- MySQL
- Lombok
- Spring Security
- Swagger

## 🗄️ Estructura de la Base de Datos

### 📑 Entidades

**Topico**
- `id`: Long
- `titulo`: String
- `mensaje`: String
- `fechaCreacion`: LocalDateTime
- `usuario`: Usuario
- `curso`: Curso
- `respuestas`: List<Respuesta>

**Curso**
- `id`: Long
- `nombre`: String
- `categoria`: Enum (String)

**Respuesta**
- `id`: Long
- `mensaje`: String
- `fechaCreacion`: LocalDateTime
- `usuario`: Usuario
- `topico`: Topico

**Usuario**
- `id`: Long
- `username`: String
- `email`: String
- `contrasena`: String

## 📍 Endpoints

### 🔑 Autenticación

- `POST /login`: Autentica un usuario y devuelve un token JWT. Requiere el `username` y la `contraseña`.

### 👥 Usuarios

- `GET /usuarios`: Obtiene todos los usuarios. (Requiere token)
- `GET /usuarios/{id}`: Obtiene un usuario por ID. (Requiere token)
- `POST /usuarios`: Crea un nuevo usuario.
- `PUT /usuarios/{id}`: Actualiza un usuario. (Requiere token)
- `DELETE /usuarios/{id}`: Elimina un usuario. (Requiere token)

### 📝 Tópicos

- `GET /topicos`: Obtiene todos los tópicos. (Requiere token)
- `GET /topicos/{id}`: Obtiene un tópico por ID. (Requiere token)
- `GET /topicos/titulo/{titulo}`: Busca tópicos por título. (Requiere token)
- `GET /topicos/anio/{anio}`: Busca tópicos por año de creación. (Requiere token)
- `POST /topicos`: Crea un nuevo tópico. (Requiere token)
- `PUT /topicos/{id}`: Actualiza un tópico. (Requiere token)
- `DELETE /topicos/{id}`: Elimina un tópico. (Requiere token)

### 📚 Cursos

- `GET /cursos`: Obtiene todos los cursos. (Requiere token)
- `GET /cursos/{id}`: Obtiene un curso por ID. (Requiere token)
- `POST /cursos`: Crea un nuevo curso. (Requiere token)
- `PUT /cursos/{id}`: Actualiza un curso. (Requiere token)
- `DELETE /cursos/{id}`: Elimina un curso. (Requiere token)

### 💬 Respuestas

- `GET /topicos/{id}/respuestas`: Obtiene todas las respuestas de un tópico. (Requiere token)
- `GET /topicos/{topicoId}/respuestas/{id_respuesta}`: Obtiene una respuesta de un tópico por ID. (Requiere token)
- `POST /topicos/{id}/respuestas`: Crea una nueva respuesta para un tópico. (Requiere token)
- `PUT /topicos/{topicoId}/respuestas/{id_respuesta}`: Actualiza una respuesta de un tópico. (Requiere token)
- `DELETE /topicos/{topicoId}/respuestas/{id_respuesta}`: Elimina una respuesta de un tópico. (Requiere token)

## 🚀 Instalación y Ejecución

1. Clonar el repositorio:
    ```bash
    git clone <url-del-repositorio>
    cd ForoHub
    ```

2. Configurar la base de datos MySQL en `application.properties`:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/{nombre}
    spring.datasource.username={usuario}
    spring.datasource.password={contraseña}
    spring.jpa.hibernate.ddl-auto=update
    spring.flyway.enabled=true
    api.security.secret=${JWT_SECRET}
    ```

3. Ejecutar el proyecto con Maven:
    ```bash
    ./mvnw spring-boot:run
    ```

4. Acceder a Swagger para probar la API:
    ```
    http://localhost:8080/swagger-ui.html
    ```

## 🔐 Autenticación

Para la mayoría de los endpoints, necesitas un token de autenticación. Para obtener el token, envía una solicitud `POST` a `/login` con las credenciales de usuario. Usa el token en el encabezado `Authorization` de las solicitudes subsiguientes. El token obtenido es del tipo Bearer Token.
