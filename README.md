
# Proyecto de Gestión de Actividades y Usuarios

Este proyecto es una API REST para gestionar usuarios y actividades, incluyendo funcionalidades como registro de usuarios, creación y consulta de actividades, inscripción de usuarios en actividades, e importación/exportación de datos en formato JSON.

## Cambios Realizados

A continuación, se detallan los cambios realizados en el proyecto, explicando las razones detrás de cada modificación.

---

### 1. **Uso de `ResponseEntity` en los Controladores**

#### Cambios:
- Todos los controladores (`UsuarioController` y `ActividadController`) fueron actualizados para devolver instancias de `ResponseEntity`.

#### Razón:
- `ResponseEntity` permite definir tanto el estado HTTP como el cuerpo de la respuesta de forma explícita.
- Proporciona mayor control sobre las respuestas, facilitando el manejo de errores y casos especiales (e.g., `404 Not Found`, `400 Bad Request`, `201 Created`).

---

### 2. **Manejo de Excepciones**

#### Cambios:
- Se incluyó el manejo explícito de excepciones como `IllegalArgumentException` en los servicios y controladores.
- Se agregó un controlador global (`@ControllerAdvice`) para capturar excepciones y responder con mensajes y estados apropiados.

#### Razón:
- Centralizar el manejo de errores mejora la legibilidad y la consistencia del código.
- Permite devolver mensajes claros al cliente cuando ocurren errores específicos o inesperados.

---

### 3. **Validaciones en los Servicios**

#### Cambios:
- Se añadieron validaciones en los métodos de los servicios (`UsuarioService` y `ActividadService`) para verificar datos obligatorios como nombre, email y capacidad.
- Ejemplo:
  ```java
  if (usuario.getNombre() == null || usuario.getNombre().isEmpty()) {
      return "Debes introducir un nombre de usuario.";
  }
  ```

#### Razón:
- Asegura que los datos ingresados sean válidos antes de intentar procesarlos.
- Previene errores en la base de datos y garantiza la integridad de los datos.

---

### 4. **Actualización Condicional de Entidades**

#### Cambios:
- Se implementó una lógica condicional para actualizar solo los campos proporcionados por el cliente al modificar entidades.

#### Razón:
- Evita sobrescribir campos existentes con valores `null` si no se proporcionan todos los datos en la solicitud.
- Mejora la flexibilidad del API para actualizar parcialmente los registros.

---

### 5. **Eliminación de Métodos en Entidades**

#### Cambios:
- Se eliminó el método `añadirActividad` de la entidad `Usuario` y la lógica de asociación se trasladó al servicio `ActividadService`.

#### Razón:
- Las entidades deben actuar como contenedores de datos y no manejar lógica de negocio.
- Este cambio sigue las buenas prácticas de diseño, manteniendo la lógica de negocio en los servicios.

---

### 6. **Simplificación del Servicio `ActividadService`**

#### Cambios:
- La lógica de inscripción de usuarios a actividades fue trasladada completamente al servicio.
- Ejemplo:
  ```java
  if (actividad.getNumUsuarios() >= actividad.getCapacidadMaxima()) {
      return "La actividad no tiene plazas disponibles.";
  }
  actividad.setNumUsuarios(actividad.getNumUsuarios() + 1);
  ```

#### Razón:
- Centraliza la lógica de negocio en un único lugar, facilitando el mantenimiento y la depuración.
- Evita duplicación de lógica en múltiples partes del sistema.

---

### 7. **Rutas RESTful Mejoradas**

#### Cambios:
- Las rutas del API fueron ajustadas para seguir las convenciones REST.
- Ejemplo:
  - `GET /users/{id}` para consultar un usuario por ID.
  - `POST /activitats` para crear una nueva actividad.

#### Razón:
- Mantener las rutas intuitivas y consistentes facilita su uso y comprensión por parte de los desarrolladores.

---

### 8. **Manejo de Listas Vacías**

#### Cambios:
- Se añadieron verificaciones para manejar respuestas con listas vacías, devolviendo `204 No Content` en lugar de respuestas vacías.
- Ejemplo:
  ```java
  if (actividades.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No hay actividades disponibles para exportar.");
  }
  ```

#### Razón:
- Proporciona respuestas más claras al cliente y sigue las mejores prácticas en APIs REST.

---

### 9. **Separación de Responsabilidades**

#### Cambios:
- Se garantizó que los controladores deleguen toda la lógica de negocio a los servicios.
- Ejemplo:
  ```java
  return actividadService.crearActividad(actividad);
  ```

#### Razón:
- Mejora la mantenibilidad del código al mantener los controladores enfocados únicamente en manejar solicitudes HTTP.

---

### 10. **Validaciones Personalizadas en Repositorios**

#### Cambios:
- Métodos personalizados como `existsByEmail` fueron añadidos al repositorio `UsuarioRepository`.
- Ejemplo:
  ```java
  boolean exists = usuarioRepository.existsByEmail("ejemplo@correo.com");
  ```

#### Razón:
- Simplifica las consultas específicas necesarias para validar datos únicos como el correo electrónico.

---

## Ejemplo de Respuesta del API

### Crear una Actividad
**Request:**
```http
POST /appActivitats/activitats
Content-Type: application/json

{
  "nombre": "Clase de Yoga",
  "descripcion": "Sesión de relajación y estiramientos",
  "numUsuarios": 0,
  "capacidadMaxima": 20
}
```

**Response:**
```http
201 Created
"Actividad creada exitosamente."
```

---

### Consultar Usuario por ID No Existente
**Request:**
```http
GET /appActivitats/users/99
```

**Response:**
```http
404 Not Found
"No se ha encontrado el usuario con ID: 99."
```

---

## Conclusión

Los cambios realizados garantizan un código más limpio, escalable y fácil de mantener. Las mejoras en el manejo de errores, validaciones y separación de responsabilidades aseguran que el sistema sea robusto y siga las mejores prácticas en desarrollo de APIs REST.
