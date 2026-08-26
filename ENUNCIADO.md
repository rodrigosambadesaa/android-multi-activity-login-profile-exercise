# Enunciado original — Programación con varias Activities y AsyncTask

Fuente: `Práctica programación con varias activities y AsyncTask.pdf` de Programación Multimedia y Dispositivos Móviles.

Crear una aplicación Android con una clase `Usuario` con los atributos `nick`, `nombre`, `apellidos` y `sexo`, preparada para enviarse entre Activities.

## Activity 1 — Principal

- Al pulsar **Entrar**, el login se considera correcto únicamente con nick `neo` y contraseña `sesamo`.
- Si es correcto, crear un usuario con nombre `Andrés`, apellidos `Harminio Jiménez` y sexo `h`, y abrir la Activity 2 enviándole dicho objeto.
- Si las credenciales no coinciden, mostrar un `Toast` indicando que nick y/o contraseña no son válidos.
- Al pulsar **Registrarse**, abrir la Activity 3 y procesar su resultado.

## Activity 2 — Pedidos

- Mostrar el título `Pedidos`.
- Mostrar un saludo personalizado con los datos del usuario recibido: `nombre (nick) bienvenido/a. Sus pedidos:`.
- Incluir un menú con las opciones **Perfil** y **Contactar**.
- **Perfil** abre la Activity 3 para editar el usuario.
- **Contactar** abre la página web indicada por la práctica.

## Activity 3 — Registro / perfil

- Mostrar `Regístrese` cuando se abra desde Activity 1 y `Actualizar perfil` cuando se abra desde Activity 2.
- Si recibe un usuario existente, cargar sus datos en los controles y representar el sexo mediante un `RadioGroup`.
- **Cancelar** cierra la Activity sin cambios.
- **Aceptar** devuelve un objeto `Usuario` con los datos introducidos/actualizados y cierra la Activity.

## Mejora indicada por el enunciado

Simular la comprobación del login dentro de una tarea `AsyncTask`, como aproximación docente a una consulta real contra una base de datos en línea, y comunicar el resultado a la Activity.

No existe un volcado de base de datos asociado a la práctica: la comprobación de credenciales es simulada.
