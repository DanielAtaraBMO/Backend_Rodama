# Rodama Ecommerce — Backend API

API RESTful para la tienda de ropa **Rodama**, desarrollada con **Spring Boot 3.2.5**, autenticación **JWT** y base de datos **MySQL**. Incluye documentación interactiva con **Swagger/OpenAPI 3**.

---

## Tabla de Contenidos

- [Tecnologías](#tecnologías)
- [Requisitos](#requisitos)
- [Configuración](#configuración)
- [Ejecución](#ejecución)
- [Swagger UI](#swagger-ui)
- [Autenticación JWT](#autenticación-jwt)
- [Endpoints](#endpoints)
  - [Auth](#auth)
  - [Productos](#productos)
  - [Usuarios](#usuarios)
  - [Pedidos](#pedidos)
  - [Pagos](#pagos)
- [Modelos de Datos](#modelos-de-datos)
- [Arquitectura del Proyecto](#arquitectura-del-proyecto)

---

## Tecnologías

| Tecnología | Versión |
|---|---|
| Java | 17 / 21 |
| Spring Boot | 3.2.5 |
| Spring Security | 6 |
| JWT (jjwt) | 0.12.5 |
| JPA / Hibernate | 6.4 |
| MySQL | 8+ |
| SpringDoc OpenAPI | 2.5.0 |
| Lombok | Latest |
| Maven | Wrapper incluido |

---

## Requisitos

- **Java 17 o 21** instalado y en el PATH
- **MySQL 8** corriendo localmente en el puerto `3306`
- Base de datos `rodama` creada en MySQL

```sql
CREATE DATABASE rodama;
```

---

## Configuración

El archivo de configuración se encuentra en:
`src/main/resources/application.yaml`

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/rodama?useSSL=false&serverTimezone=UTC
    username: root
    password: Admin123*      # Ajusta a tu contraseña de MySQL

jwt:
  secret: my_super_secret_key_rodama_2026_1234567890123456
  expiration: 86400000       # 24 horas en milisegundos
```

> ⚠️ **Ajusta** el `username` y `password` de MySQL según tu entorno local.

---

## Ejecución

```bash
# Clonar el proyecto
git clone <url-del-repositorio>
cd Backend_Rodama

# Ejecutar con el Maven Wrapper (no requiere Maven instalado)
.\mvnw.cmd spring-boot:run     # Windows
./mvnw spring-boot:run         # Linux / Mac
```

Al arrancar, el sistema crea automáticamente la cuenta de administrador si no existe:

| Campo | Valor |
|---|---|
| Email | `admin@rodama.com` |
| Password | `Admin123` |
| Rol | `ROLE_ADMIN` |

---

## Swagger UI

Una vez iniciado el servidor, accede a la documentación interactiva:

```
http://localhost:8080/swagger-ui/index.html
```

Para probar endpoints protegidos desde Swagger:
1. Llama a `POST /api/auth/login` con las credenciales del admin.
2. Copia el `token` de la respuesta.
3. Haz clic en el botón **Authorize** (🔒) en la parte superior de Swagger.
4. Ingresa: `Bearer <tu-token>` y confirma.

---

## Autenticación JWT

La API usa **Bearer Token** (JWT) en el header `Authorization`:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

### Flujo de Autenticación

```
Cliente → POST /api/auth/login → { email, password }
Servidor → 200 OK → { token: "eyJ..." }
Cliente → GET/POST/PUT/DELETE /api/** → Header: Authorization: Bearer eyJ...
Servidor → 200 OK o 403 Forbidden
```

---

## Endpoints

### Auth

| Método | Endpoint | Acceso | Descripción |
|---|---|---|---|
| `POST` | `/api/auth/login` | Público | Iniciar sesión, retorna JWT |

**Body de Login:**
```json
{
  "email": "admin@rodama.com",
  "password": "Admin123"
}
```

**Respuesta exitosa:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### Productos

| Método | Endpoint | Acceso | Descripción |
|---|---|---|---|
| `GET` | `/api/producto` | 🌐 Público | Listar todos los productos |
| `GET` | `/api/producto/{id}` | 🌐 Público | Obtener producto por ID |
| `GET` | `/api/producto/categoria/{categoria}` | 🌐 Público | Filtrar por categoría |
| `POST` | `/api/producto` | 🔒 Autenticado | Crear nuevo producto |
| `PUT` | `/api/producto/{id}` | 🔒 Autenticado | Actualizar producto |
| `DELETE` | `/api/producto/{id}` | 🔒 Autenticado | Eliminar producto |

**Categorías válidas:** `DEPORTIVO`, `CASUAL`, `NOCTURNO`, `ACCESORIOS`, `COMBOS`

**Body para crear/actualizar producto:**
```json
{
  "nombre": "Chaqueta Sport",
  "precio": 89990.0,
  "categoria": "DEPORTIVO",
  "descripcion": "Chaqueta deportiva con cierre",
  "stock": 15,
  "talla": "M",
  "imageUrl": "https://example.com/imagen.jpg"
}
```

---

### Usuarios

| Método | Endpoint | Acceso | Descripción |
|---|---|---|---|
| `POST` | `/api/usuario` | 🌐 Público | Registrar nuevo usuario |
| `GET` | `/api/usuario` | 🔒 Autenticado | Listar todos los usuarios |
| `GET` | `/api/usuario/{id}` | 🔒 Autenticado | Obtener usuario por ID |
| `PUT` | `/api/usuario/{id}` | 🔒 Autenticado | Actualizar usuario |
| `DELETE` | `/api/usuario/{id}` | 🔒 Autenticado | Eliminar usuario |

**Body para registrar usuario:**
```json
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "correo": "juan@email.com",
  "telefono": "3001234567",
  "password": "password123"
}
```

> El campo `rol` es opcional. Si no se envía, se asigna `ROLE_USER` por defecto.

---

### Pedidos

| Método | Endpoint | Acceso | Descripción |
|---|---|---|---|
| `GET` | `/api/pedido` | 🔒 Autenticado | Listar todos los pedidos |
| `GET` | `/api/pedido/{id}` | 🔒 Autenticado | Obtener pedido por ID |
| `POST` | `/api/pedido` | 🔒 Autenticado | Crear pedido |
| `PUT` | `/api/pedido/{id}` | 🔒 Autenticado | Actualizar pedido |
| `DELETE` | `/api/pedido/{id}` | 🔒 Autenticado | Eliminar pedido |

---

### Pagos

| Método | Endpoint | Acceso | Descripción |
|---|---|---|---|
| `GET` | `/api/pago` | 🔒 Autenticado | Listar todos los pagos |
| `GET` | `/api/pago/{id}` | 🔒 Autenticado | Obtener pago por ID |
| `POST` | `/api/pago` | 🔒 Autenticado | Registrar pago |
| `PUT` | `/api/pago/{id}` | 🔒 Autenticado | Actualizar pago |
| `DELETE` | `/api/pago/{id}` | 🔒 Autenticado | Eliminar pago |

---

## Modelos de Datos

### Producto

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | Long | Identificador único (auto) |
| `nombre` | String | Nombre del producto |
| `precio` | Double | Precio en pesos colombianos |
| `categoria` | Enum | `DEPORTIVO`, `CASUAL`, `NOCTURNO`, `ACCESORIOS`, `COMBOS` |
| `descripcion` | String | Descripción del producto |
| `stock` | Integer | Cantidad disponible |
| `talla` | String | Talla del producto (XS, S, M, L, XL, XXL) |
| `imageUrl` | String | URL de la imagen del producto |

### Usuario

| Campo | Tipo | Descripción |
|---|---|---|
| `id` | Long | Identificador único (auto) |
| `nombre` | String | Nombre del usuario |
| `apellido` | String | Apellido del usuario |
| `correo` | String | Correo electrónico (único) |
| `telefono` | String | Número de teléfono |
| `password` | String | Contraseña encriptada (BCrypt) |
| `rol` | Enum | `ROLE_ADMIN`, `ROLE_USER` |

---

## Arquitectura del Proyecto

El proyecto sigue una **arquitectura en capas** (Domain-Driven Design simplificado):

```
src/main/java/com/rodama/ecommerce/
│
├── domain/
│   └── model/               # Entidades de dominio puro (Producto, Usuario...)
│       └── enums/           # Enumerados (Categoria, Rol)
│
├── application/
│   ├── controller/          # Controladores REST (@RestController)
│   ├── dto/                 # Objetos de transferencia de datos
│   ├── service/             # Interfaces de servicio
│   │   └── implementation/  # Implementaciones de servicios
│   └── util/                # Utilidades (JwtUtil)
│
└── infrastructure/
    ├── config/              # Configuraciones (Security, CORS, Swagger, DataInitializer)
    ├── entity/              # Entidades JPA (@Entity)
    ├── repository/          # Repositorios JPA (JpaRepository)
    └── security/            # Filtro JWT (JwtAuthFilter)
```

### Flujo de una Petición

```
HTTP Request
    └── JwtAuthFilter (valida token)
         └── SecurityConfig (verifica permisos)
              └── Controller (recibe y valida DTO)
                   └── Service (lógica de negocio)
                        └── Repository (accede a MySQL)
                             └── HTTP Response
```

---

## CORS

La API acepta peticiones desde cualquier origen local. Configurado para:
- `http://localhost:5173` (Vite / React)
- `http://localhost:3000` (Next.js / React)
- `http://127.0.0.1:5500` (Live Server / VS Code)

---

## Colección Postman / API Docs

Accede a la especificación OpenAPI en formato JSON para importar en Postman:

```
http://localhost:8080/v3/api-docs
```
