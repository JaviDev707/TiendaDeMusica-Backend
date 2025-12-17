# 🎸 E-Commerce: Tienda de Música 

Este es el backend de una tienda online especializada en música, donde gestionamos instrumentos, discos y otros.

El proyecto está construido con una arquitectura de **Herencia de Tabla Unida (Joined Table)** en la base de datos, lo que permite una escalabilidad limpia y un análisis de datos preciso para Business Intelligence.

## 🚀 Características Principales

* **Gestión Polimórfica de Productos:** Creación y actualización de Discos, Instrumentos y Accesorios mediante un único endpoint inteligente.
* **Seguridad JWT:** Autenticación y autorización basada en roles (`ADMIN` y `USER`) mediante Spring Security.
* **Sistema de Carrito y Pedidos:** Flujo completo desde la selección de ítems hasta el checkout con descuento automático de stock.
* **Comunidad:** Sistema de reseñas por producto y usuario.
* **Base de Datos Relacional:** Estructura normalizada optimizada para integración con **Power BI**.

## 🛠️ Tecnologías Utilizadas

* **Java 17**
* **Spring Boot 3.x** (Spring Security, Spring Data JPA, Spring Web)
* **JSON Polimórfico** (Jackson Annotations)
* **Lombok**
* **MySQL** 
* **JWT** (JSON Web Tokens) para autenticación segura.

## 📊 Arquitectura de Datos (Herencia)

El proyecto utiliza la estrategia `InheritanceType.JOINED`. Esto significa que los productos comparten una tabla base y tienen tablas de detalle específicas:

1.  **Producto (Base):** Nombre, precio, stock, descripción.
2.  **Disco:** Artista, género, año.
3.  **Instrumento:** Marca, modelo, tipo.
4.  **Varios:** Marca, tipo.

## 🔌 Guía de API (Endpoints)

> 🔐 **Seguridad:** Los endpoints marcados como `USER` o `ADMIN` requieren un token válido en el encabezado de la petición:  
> `Authorization: Bearer <tu_token_aquí>`

### 🛡️ Autenticación y Usuarios
| Método | Endpoint | Acceso | Función |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/auth/register` | Público | Registra un nuevo usuario en el sistema y le devuelve un J. |
| `POST` | `/api/auth/login` | Público | Valida credenciales y devuelve el token JWT. |

### 📦 Gestión de Productos (Polimorfismo)
Este módulo utiliza **Jackson Polymorphic Deserialization**. Al enviar un `POST` o `PUT`, es obligatorio incluir el campo `"tipoProducto"`.

| Método | Endpoint | Acceso | Función |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/productos` | Público | Lista todos los productos (Discos, Instrumentos y Varios). |
| `GET` | `/api/productos/id/{id}` | Público | Obtiene el detalle completo de un producto por su ID. |
| `POST` | `/api/productos/crear` | **ADMIN** | Crea un nuevo producto según su tipo específico. |
| `PUT` | `/api/productos/actualizar` | **ADMIN** | Actualiza un producto existente (requiere ID en el JSON). |
| `DELETE` | `/api/productos/eliminar/{id}` | **ADMIN** | Elimina un producto de la base de datos. |

### 🛒 Carrito de Compras
| Método | Endpoint | Acceso | Función |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/carrito` | **USER** | Muestra el carrito actual del usuario autenticado. |
| `POST` | `/api/carrito/agregar` | **USER** | Añade un producto al carrito (JSON: `productoId`, `cantidad`). |
| `POST` | `/api/carrito/actualizarcantidad` | **USER** | Modifica la cantidad de uno de los items del carrito. |
| `DELETE` | `/api/carrito/eliminaritem/{id}` | **USER** | Quita un ítem del carrito. |

### 💳 Pedidos (Checkout)
| Método | Endpoint | Acceso | Función |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/pedidos/checkout` | **USER** | Convierte el carrito en un pedido y descuenta stock. |
| `GET` | `/api/pedidos/historial` | **USER** | Lista el historial de compras del usuario logueado. |
| `GET` | `/api/pedidos/detalle/{pedidoId}` | **USER** | Muestra los detalles de un pedido. |

---

## Desarrollado por JaviDev707
