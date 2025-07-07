# 🛒 Sistema de Gestión de Carrito de Compras

Nombre: Kelly Valeria Guaman Leon
Carrera: Computacion- Grupo 1
Docente: Ing. Gabriel Leon

## Link de video
https://www.youtube.com/watch?v=sMeZEbeFhnQ

## Introduccion
Este proyecto fue desarrollado como parte del **período 66** de la asignatura **Programación Orientada a Objetos** en la Universidad Politécnica Salesiana.  
Su objetivo principal es construir una aplicación de escritorio en **Java** con interfaz gráfica utilizando **Swing**, aplicando principios de diseño orientado a objetos y arquitectura modular.

---

## 🎯 Objetivo del proyecto

Desarrollar un sistema de escritorio que permita gestionar usuarios, productos y carritos de compras, con control de acceso por roles e internacionalización.  
El sistema está diseñado para demostrar el uso de buenas prácticas de programación, aplicando los patrones **MVC** y **DAO**, y principios de diseño como **SRP** y **DIP**.

---

## 🛠️ Tecnologías utilizadas

- 💻 **Java **
- 🧰 **IntelliJ IDEA** (con soporte para formularios `.form`)
- ☕ **Swing** para la interfaz gráfica
- 🌐 **ResourceBundle** para internacionalización (i18n)
- 📦 Estructura modular basada en paquetes:
  - `ec.edu.modelo`
  - `ec.edu.dao` y `ec.edu.dao.impl`
  - `ec.edu.controlador`
  - `ec.edu.vista`
  - `ec.edu.util`

---

## 🧱 Patrones de Diseño aplicados

- **MVC (Modelo - Vista - Controlador)**  
  Separa la lógica de negocio, la interfaz gráfica y el flujo de control para mejorar la mantenibilidad.

- **DAO (Data Access Object)**  
  Permite desacoplar el acceso a los datos, facilitando la migración futura a una base de datos real.

- **Principios SOLID**
  - **SRP (Responsabilidad Única)**: Cada clase tiene una única responsabilidad clara.
  - **DIP (Inversión de Dependencias)**: Los controladores dependen de interfaces, no de implementaciones concretas.

---

## 🔐 Funcionalidades principales

- Inicio de sesión con autenticación y control de acceso por rol (`ADMINISTRADOR` / `USUARIO`)
- Registro de nuevos usuarios con validación
- Gestión de usuarios (solo para administradores)
- Gestión de productos (crear, listar, modificar, eliminar)
- Carrito de compras para usuarios
- Cambio dinámico de idioma (Español, Inglés, Francés, Portugués)

---

## 🌍 Internacionalización

El sistema soporta múltiples idiomas mediante archivos `.properties` y la clase `MensajeInternacionalizacionHandler`.  
Cada vista implementa el método `actualizarTextos()` para reflejar los cambios de idioma en tiempo real, sin necesidad de reiniciar la aplicación.

---

## 📚 Recomendaciones

- Ejecutar el proyecto desde `Main.java`
- Usar IntelliJ IDEA para editar formularios `.form` visualmente
- Probar el sistema con diferentes roles para observar el comportamiento dinámico del menú
- Cambiar el idioma desde el menú superior para ver la internacionalización en acción

---


