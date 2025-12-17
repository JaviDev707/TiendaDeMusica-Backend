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
* **H2 Database** (o MySQL/PostgreSQL)
* **JWT** (JSON Web Tokens) para autenticación segura.

## 📊 Arquitectura de Datos (Herencia)

El proyecto utiliza la estrategia `InheritanceType.JOINED`. Esto significa que los productos comparten una tabla base y tienen tablas de detalle específicas:

1.  **Producto (Base):** Nombre, precio, stock, descripción.
2.  **Disco:** Artista, género, año.
3.  **Instrumento:** Marca, modelo, tipo.
4.  **Varios:** Marca, tipo.

## Desarrollado por JaviDev707
