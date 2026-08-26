# Revisión de la solución original (2019)

## Qué resolvía bien

La práctica original implementaba el flujo solicitado: login simulado, registro, pantalla de pedidos, edición de perfil, retorno de resultados entre Activities y un `Usuario` transportable entre pantallas. También cumplía la mejora de ejecutar la comprobación de credenciales con `AsyncTask`, que era el mecanismo enseñado en el temario de la época.

## Problemas detectados al revisarla en 2026

- `AsyncTask` está obsoleto y la tarea conservaba referencias fuertes al `Context` y a la `Activity` a través de la interfaz `Login`.
- `startActivityForResult` / `onActivityResult` están sustituidos por Activity Result APIs.
- `Usuario` implementaba `Serializable`; para extras Android se usa ahora un `Parcelable` explícito.
- Acciones y claves de extras eran cadenas literales repetidas (`"usuario"`, `"registro"`, `"actualizacion_perfil"`).
- Se asumía que los extras y el `Intent data` nunca eran `null`.
- La validación solo comparaba con `""`, por lo que aceptaba texto formado únicamente por espacios.
- Había textos y colores hardcodeados en layouts y código.
- `MainActivity`, `Main2Activity` y `Main3Activity` no expresaban la responsabilidad de cada pantalla.
- El enlace de contacto era HTTP.
- El proyecto conservaba Support Library 28 y tests generados que solo verificaban `2 + 2 = 4`.

## Modernización aplicada

La revisión mantiene el comportamiento académico, pero usa `LoginActivity`, `OrdersActivity` y `ProfileActivity`; Activity Result APIs; `UserProfile` Parcelable; View Binding; validación aislada; concurrencia estándar de Java; cancelación/limpieza del trabajo en `onDestroy`; recursos de strings; comprobaciones de resultados nulos; HTTPS para el enlace de contacto; tests unitarios útiles y CI con test, lint y build.
