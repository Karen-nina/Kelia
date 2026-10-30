# Kelia — Plataforma web para la comunidad celíaca

**Sin gluten, sin miedo, sin excusas...**

Kelia es una plataforma web colaborativa orientada a personas con celiaquía. Reúne información sobre productos sin gluten, recetas, establecimientos y publicaciones de la comunidad en un mismo lugar.

Proyecto final integrador desarrollado en 2025. Mi participación se centró principalmente en el **desarrollo Backend**, trabajando en una solución con frontend y backend independientes.

**Estado:** proyecto académico / portafolio.

![Logo de Kelia](kelia-front-develop/public/img/logo.png)

## Índice
- [Funcionalidades principales](#funcionalidades-principales)
- [Objetivo](#objetivo)
- [Screenshots](#screenshots)
- [Lenguajes y tecnologías](#-lenguajes-y-tecnologías)
- [Cómo ejecutar](#cómo-ejecutar)
- [Autora y colaboración](#autora-y-colaboración)

## Funcionalidades principales

- **Usuarios:** registro, inicio de sesión y acceso a secciones protegidas.
- **Productos:** consulta y búsqueda de productos, con filtros por marca, tipo y datos relacionados con ANMAT.
- **Recetas:** exploración de recetas, consulta del detalle, creación de recetas y funcionalidades de comentarios y favoritos.
- **Establecimientos:** consulta de locales y lugares de interés, con visualización geográfica y detalles.
- **Comunidad:** publicaciones, comentarios e interacciones mediante «me gusta».
- **Información:** sección de preguntas frecuentes sobre la plataforma.

> Las funciones descritas corresponden a módulos presentes en el código del proyecto; algunas requieren configurar servicios externos y bases de datos para su funcionamiento completo.

## Objetivo

Desarrollar una plataforma que facilite el acceso a recursos útiles para personas con celiaquía y fomente el intercambio de información dentro de una comunidad.

Desde el punto de vista técnico, el proyecto permitió aplicar desarrollo web full stack, diseño de servicios REST, organización por capas, persistencia de datos, autenticación y pruebas automatizadas. Mi contribución principal estuvo en el **Backend**, desarrollado con Java y Spring Boot.

## Screenshots

Las capturas reales de la aplicación se incorporarán en `docs/screenshots/`. Esta sección está preparada para mostrar imágenes de **Home, Recetas, Productos, Establecimientos y Comunidad**, una vez que se suban los archivos.

| Pantalla | Archivo sugerido |
| --- | --- |
| Inicio / Home | `docs/screenshots/home.png` |
| Recetas | `docs/screenshots/recetas.png` |
| Detalle de receta | `docs/screenshots/detalle-receta.png` |
| Productos | `docs/screenshots/productos.png` |
| Establecimientos | `docs/screenshots/establecimientos.png` |
| Comunidad | `docs/screenshots/comunidad.png` |

> Para evitar mostrar imágenes rotas, los enlaces a las capturas se añadirán cuando los archivos estén efectivamente disponibles en el repositorio.

## 🛠 Lenguajes y tecnologías

**Backend**
- Java 21, Spring Boot 3.4.5
- Spring Web, Spring Security, Spring Data JPA y Spring Data MongoDB
- Hibernate/JPA, PostgreSQL y MongoDB
- Firebase Admin, JWT, Maven
- JUnit, Mockito y Spring Boot Test
- Swagger/OpenAPI, MapStruct y Lombok

**Frontend**
- JavaScript, React, HTML y CSS
- React Router, React Bootstrap, Bootstrap y Bootstrap Icons
- Axios y Firebase
- Google Maps (`@react-google-maps/api`)
- Jest y React Testing Library

**Integraciones presentes en el código**
- Google Places
- Mercado Libre
- Información de productos vinculada con ANMAT

## Cómo ejecutar

### 1. Clonar el repositorio

```bash
git clone https://github.com/Karen-nina/Kelia.git
cd Kelia
```

La estructura general del repositorio es:

```text
Kelia/
├── kelia-back-main/       # API Backend en Java y Spring Boot
└── kelia-front-develop/   # Aplicación Frontend en React
```

### 2. Iniciar el Backend

**Requisitos:** JDK 21, PostgreSQL, MongoDB y credenciales/configuraciones válidas para los servicios utilizados.

Desde la raíz del repositorio:

```bash
cd kelia-back-main
./mvnw spring-boot:run
```

En Windows (CMD o PowerShell), usar:

```powershell
.\mvnw.cmd spring-boot:run
```

Antes de iniciarlo, es necesario configurar las conexiones de bases de datos y cualquier variable o propiedad requerida por los servicios. La inicialización de Firebase requiere un archivo de cuenta de servicio en `src/main/resources/firebase/firebase-service-account.json`; **no publiques ese archivo ni credenciales reales en GitHub**. Utilizá credenciales de un proyecto propio de Firebase y verificá las referencias de configuración del código.

### 3. Iniciar el Frontend

Abrí otra terminal desde la raíz de Kelia:

```bash
cd kelia-front-develop
npm install
npm start
```

Por defecto, el servidor de desarrollo de React suele abrirse en `http://localhost:3000`.

Para que las funcionalidades operen correctamente, es necesario configurar Firebase y las URLs/conexiones de la API de acuerdo con el entorno local.

### 4. Ejecutar las pruebas

Backend:

```bash
cd kelia-back-main
./mvnw test
```

Frontend:

```bash
cd kelia-front-develop
npm test
```

> **Nota:** estos pasos son una guía basada en las dependencias y los scripts del repositorio. La ejecución integral puede requerir configuración adicional de secretos, bases de datos y servicios externos.

## Autora y colaboración

- **Karen Nina Coela** — participación principalmente en desarrollo Backend. [GitHub](https://github.com/Karen-nina).
- **Kelia** — proyecto académico desarrollado de forma colaborativa.

---

*Documentación de presentación para portafolio. Las capturas se agregarán cuando estén disponibles.*
