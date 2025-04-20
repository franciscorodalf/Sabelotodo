# Sabelotodo - Juego de Preguntas y Respuestas

![Logo del Proyecto](https://github.com/franciscorodalf/Sabelotodo/blob/main/sabelotodo/images/logo.png)

## Descripción General del Juego

**Sabelotodo** es un juego interactivo de preguntas y respuestas donde los jugadores pueden poner a prueba sus conocimientos en diversas categorías como ciencia, historia, literatura, arte, geografía y entretenimiento. El objetivo del juego es girar una ruleta para seleccionar una categoría, responder correctamente a la pregunta y seguir avanzando. Si el jugador responde correctamente, puede seguir jugando, pero si falla, se registrará su puntaje y estadísticas.

Este juego está diseñado para cualquier persona que quiera disfrutar de un reto intelectual mientras aprende y se divierte. Es un juego adecuado para personas de todas las edades y niveles educativos. Lo que hace único a **Sabelotodo** es la **ruleta interactiva** que añade un nivel extra de emoción, y el sistema de estadísticas que permite ver el progreso y rendimiento del jugador.

---

## Captura de Pantalla Inicial

| **Pantalla de Login** | **Pantalla de Pregunta** | **Pantalla de la Ruleta** |
| --------------------- | ------------------------- | ------------------------- |
| ![Pantalla de Login](https://github.com/franciscorodalf/Sabelotodo/blob/main/sabelotodo/images/pestania-login.png) | ![Pantalla de Pregunta](https://github.com/franciscorodalf/Sabelotodo/blob/main/sabelotodo/images/pestania-pregunta.png) | ![Pantalla de la Ruleta](https://github.com/franciscorodalf/Sabelotodo/blob/main/sabelotodo/images/pestania-ruleta.png) |

---

## Tecnologías Utilizadas

- **Java 17**  
  Lenguaje de programación utilizado para la lógica del juego y la interfaz gráfica.

- **SQLite**  
  Base de datos utilizada para almacenar usuarios, preguntas y estadísticas.

- **JavaFX**  
  Framework para crear la interfaz gráfica interactiva y animaciones del juego.

- **ControlsFX**  
  Proporciona componentes adicionales como diálogos y gráficos para mejorar la experiencia del usuario.

- **JUnit**  
  Herramienta utilizada para realizar pruebas unitarias y asegurar que el código funcione correctamente.

---

## Requisitos del Sistema

Para ejecutar este proyecto, necesitas:

- **Java 17 o superior**  
  La aplicación requiere **Java 17** para compilar y ejecutar correctamente.

- **Base de Datos**  
  Asegúrate de tener **SQLite** configurado adecuadamente para almacenar preguntas, respuestas y datos del usuario.

- **Sistema Operativo**  
  Compatible con **Windows, macOS, y Linux**. La aplicación ha sido probada en estas plataformas.

---

## Información Adicional

### Mejoras Planeadas

1. **Modo Multijugador**  
   Actualmente el juego es para un solo jugador. Se planea agregar un **modo multijugador** para que los jugadores puedan competir entre ellos.

2. **Añadir más categorías**  
   Se planea expandir el juego con más categorías como **Deportes**, **Música**, **Cine**, entre otras.

3. **Modo Desafío**  
   Crear un **modo desafío** donde los jugadores puedan competir contra sí mismos con preguntas de dificultad creciente.

4. **Optimización de la Base de Datos**  
   Mejorar el rendimiento de las consultas a la base de datos para manejar más usuarios y preguntas.

---

### Desafíos Técnicos

1. **Integración de Base de Datos**  
   Uno de los mayores desafíos fue la integración de **SQLite** para almacenar y recuperar preguntas, respuestas y estadísticas de manera eficiente.

2. **Interfaz de Usuario**  
   Crear una interfaz de usuario dinámica y fluida, especialmente al cambiar entre pantallas y manejar animaciones de la ruleta.

3. **Validación de Datos**  
   Implementar la validación de usuario y datos correctamente para evitar entradas incorrectas y garantizar la seguridad del juego.

4. **Sincronización de Lógica del Juego**  
   Mantener un flujo de juego consistente y sincronizado entre la base de datos, la lógica y la interfaz de usuario.

---

### Lecciones Aprendidas

1. **Uso del Patrón MVC**  
   Implementé el patrón **Model-View-Controller (MVC)** para separar las responsabilidades de la lógica de juego y la interfaz, lo que facilitó la escalabilidad y el mantenimiento del código.

2. **Persistencia de Datos**  
   Aprendí mucho sobre cómo manejar bases de datos en aplicaciones de escritorio y la importancia de sincronizar los datos entre la interfaz y la base de datos.

3. **Mejoras en la Experiencia de Usuario**  
   La importancia de crear una experiencia interactiva y fluida para el usuario, utilizando animaciones y transiciones suaves.

---

## Diseño


### Diseño Final Implementado
![Diseño Final](https://github.com/franciscorodalf/Sabelotodo/blob/main/sabelotodo/images/pestania-login.png)  

---

## Tecnologías Usadas

- **JavaFX**
- **SQLite**
- **JUnit**
- **ControlsFX**

---

## Instrucciones de Instalación

1. **Clonar el repositorio**:

   ```bash
   git clone https://github.com/franciscorodalf/Sabelotodo.git
   cd Sabelotodo
   ```

2. **Compilar y ejecutar el proyecto**:
   Asegúrate de tener **Maven** instalado. Luego ejecuta:

   ```bash
   mvn clean javafx:run
   ```


### Tabla de Características

| Característica       | Estado |
| -------------------- | ------ |
| Login                | ✅     |
| Preguntas            | ✅     |
| Ruleta               | ✅     |
| Validación de datos  | ✅     |
| Estadísticas         | ✅     |

### Roadmap

1. **Agregar más categorías**.
2. **Desarrollar el modo multijugador**.
3. **Añadir más preguntas con dificultad ajustable**.
