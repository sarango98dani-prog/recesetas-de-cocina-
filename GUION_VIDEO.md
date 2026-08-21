# Guion — Video de sustentación técnica "Recetario"

Duración objetivo: ~15 minutos. Formato: hablás a cámara y compartís pantalla
(app corriendo + Android Studio + GitHub). El texto entre comillas es una
guía de lo que podés decir — no hace falta memorizarlo palabra por palabra,
pero mantiene el orden de ideas y los nombres técnicos correctos.

> Nota antes de grabar: la app en pantalla se llama **"Recetas de la Chef
> Daniela Jima"** (Instituto Tecnológico Rumiñahui) y el menú inferior tiene
> 3 pestañas: **Buscar**, **Recetas Chef** y **Favoritos**. El guion ya lo
> tiene en cuenta.

---

## Minuto 0–1 — Presentación personal y objetivo

**🖥️ Pantalla:** cámara/rostro, o el ícono de la app + pantalla de Splash
recién abierta (fondo degradado con el nombre de la chef y el instituto).

**📂 Archivos:** ninguno todavía.

**🎙️ Decís:**

> "Hola, mi nombre es [tu nombre] y este es mi proyecto final individual de
> la asignatura de Aplicaciones Móviles, del Instituto Tecnológico
> Rumiñahui. Desarrollé una aplicación Android llamada Recetario — que
> dentro de la app se presenta como 'Recetas de la Chef Daniela Jima' —,
> hecha 100% en Kotlin con Jetpack Compose.
>
> El objetivo del proyecto fue aplicar en un caso real los conceptos que
> vimos en el curso: una arquitectura MVVM con patrón Repository y capas
> bien separadas, persistencia local con Room, consumo de una API REST con
> Retrofit, preferencias de usuario con DataStore, y el uso de una función
> de hardware del dispositivo, en mi caso la cámara con CameraX. En los
> próximos minutos les muestro la app funcionando y después entro al código
> para explicar cómo está armada."

---

## Minuto 1–5 — Demostración completa de la app

**🖥️ Pantalla:** el emulador o el celular con la app instalada (APK de
release, para mostrar que corre igual que en producción).

**📂 Archivos:** ninguno, solo la app.

**🎙️ Decís (mientras navegás):**

> "Arranco la app desde cero. [Abrís la app] Lo primero que se ve es la
> pantalla de splash, con el nombre de la chef y del instituto — se muestra
> un par de segundos y pasa sola a la pantalla de bienvenida."
>
> [Aparece HomeScreen] "Esta es la Home: un mensaje de bienvenida y un botón
> para 'Explorar recetas'. Lo toco."
>
> [Aparece RecipeListScreen] "Acá caigo en la pantalla de Búsqueda, que es
> la que consume la API pública TheMealDB. Escribo el nombre de una receta,
> por ejemplo 'chicken', y busco."
>
> [Escribís y tocás buscar] "Mientras espera la respuesta se ve un
> indicador de carga, y cuando llega la respuesta se arma esta lista de
> tarjetas con la imagen, el nombre, la categoría y el área de cada receta,
> todo traído en tiempo real desde la API."
>
> [Tocás una receta] "Si toco una receta, entro al detalle: veo la imagen
> grande, la categoría, el área, un campo para mis notas y un botón para
> tomarle una foto al plato. Justo al tocar la receta, la app ya la guardó
> automáticamente como favorita en la base de datos local — eso lo explico
> en el siguiente bloque."
>
> [Volvés atrás, mostrás brevemente la pestaña "Recetas Chef"] "Antes de
> seguir, les muestro rápido esta segunda pestaña: además de la búsqueda
> por API, la app tiene un catálogo propio de recetas precargadas,
> organizado por carpetas de categoría — Entradas, Platos principales,
> Postres, Bebidas y Cocina tradicional —, y desde acá también puedo
> agregar una receta nueva con foto de la galería. Es una funcionalidad
> extra que sumé sobre el proyecto base, y más adelante en el código les
> muestro que reutiliza la misma arquitectura."

---

## Minuto 5–8 — Explicar funcionalidades (favoritos, notas, cámara, ajustes)

**🖥️ Pantalla:** app en vivo, alternando pantallas.

**📂 Archivos:** ninguno todavía (viene en el bloque técnico).

**🎙️ Decís:**

> "Ahora les muestro con más detalle cada funcionalidad.
>
> Primero, favoritos. [Tocás la pestaña Favoritos] Esta pestaña muestra
> todas las recetas que guardé, y funciona sin conexión a internet porque
> están guardadas en una base de datos local con Room, no se vuelven a
> pedir a la API. Si las borro de acá [tocás el ícono de eliminar], se
> borran de la base de datos.
>
> Segundo, las notas personales. [Entrás al detalle de una receta] Acá
> puedo escribir cualquier anotación mía — un cambio que le hice a la
> receta, algo que quiero recordar — y se guarda automáticamente en Room a
> medida que escribo, sin botón de 'guardar' aparte.
>
> Tercero, la cámara. [Tocás 'Tomar foto del plato'] La primera vez que uso
> esta función, Android me pide permiso de cámara en tiempo de ejecución
> —esto es un permiso peligroso, no se concede solo—. Una vez que lo acepto,
> se abre la vista previa de la cámara usando CameraX, saco la foto con este
> botón central, y automáticamente vuelvo al detalle con la foto ya
> guardada y mostrada. También tengo la opción de elegir una foto de la
> galería en vez de sacarla en el momento, usando el selector de imágenes
> de Android.
>
> Y cuarto, la configuración. [Vas a Ajustes] Acá el usuario elige la
> unidad de medida que prefiere, métrica o imperial. Esta preferencia se
> guarda con DataStore, que es el reemplazo moderno de SharedPreferences, y
> persiste aunque cierre la app por completo."

---

## Minuto 8–11 — Explicación técnica del código

**🖥️ Pantalla:** Android Studio, con el panel de proyecto abierto mostrando
la estructura de carpetas `ui / viewmodel / domain / data`.

**📂 Archivos a abrir en este orden:**
1. Estructura de paquetes (`domain/model`, `domain/usecase`, `data/repository`, `data/local`, `data/remote`, `viewmodel`, `ui/screens`)
2. `data/repository/RecipeRepository.kt`
3. `domain/usecase/SearchRecipesUseCase.kt`
4. `viewmodel/RecipeListViewModel.kt`
5. `data/remote/MealApiService.kt` y `data/remote/RetrofitInstance.kt`
6. `data/local/RecipeDao.kt` y `data/local/AppDatabase.kt`
7. `navigation/RecetarioNavGraph.kt`

**🎙️ Decís:**

> "Ahora paso a Android Studio para mostrar cómo está organizado el código
> por dentro. Usé una arquitectura MVVM con patrón Repository, separada en
> cuatro capas: UI, ViewModel, Domain y Data. La regla que me impuse es
> que cada capa solo puede hablar con la capa de abajo: la UI nunca toca
> Retrofit ni Room directamente, siempre pasa por el ViewModel.
>
> [Mostrás RecipeRepository.kt] Este es el Repository de recetas. Es el
> único lugar del proyecto que sabe que existen Retrofit y Room al mismo
> tiempo: expone un método `searchRecipes` que llama a la API, y métodos
> como `getSavedRecipes` o `saveRecipe` que usan el DAO de Room. Para el
> resto de la app, esto es una sola fuente de datos.
>
> [Mostrás SearchRecipesUseCase.kt] Arriba del Repository tengo la capa de
> Domain, con Use Cases. Cada Use Case hace una sola cosa — este, por
> ejemplo, solo sabe buscar recetas — y es lo único que el ViewModel puede
> usar. Esto hace que la lógica de negocio quede desacoplada de Android y
> sea fácil de testear.
>
> [Mostrás RecipeListViewModel.kt] Acá está el ViewModel de la búsqueda.
> Uso un `StateFlow` con un estado sellado, `RecipeListUiState`, que puede
> ser `Loading`, `Success` o `Error` — así la pantalla siempre sabe
> exactamente qué mostrar. La búsqueda corre en una corrutina con
> `viewModelScope.launch`, y si el usuario busca de nuevo antes de que
> termine la anterior, cancelo el Job previo para evitar que una respuesta
> vieja pise a una más reciente.
>
> [Mostrás MealApiService.kt y RetrofitInstance.kt] Este es el consumo de
> la API: una interfaz de Retrofit con el endpoint de búsqueda de TheMealDB,
> y la configuración de Retrofit con OkHttp y un logging interceptor para
> ver las peticiones en el Logcat.
>
> [Mostrás RecipeDao.kt y AppDatabase.kt] Del lado de Room, tengo el DAO
> con las consultas —anotadas con `@Query`, `@Upsert`, `@Delete`— y la
> base de datos. Todas las consultas de lectura devuelven `Flow`, así que
> cuando cambia un dato en la base, la pantalla se actualiza sola, sin que
> yo tenga que refrescar nada a mano.
>
> [Mostrás RecetarioNavGraph.kt] Por último, la navegación: uso Navigation
> Compose con un grafo centralizado donde defino cada pantalla y cómo se
> pasan los parámetros, como el id de la receta al entrar al detalle."

---

## Minuto 11–13 — Entregables del proyecto

**🖥️ Pantalla:** navegador con el repositorio en GitHub, y el explorador de
archivos local mostrando los `.apk`/`.aab` generados.

**📂 Archivos / pantallas a mostrar:**
- Página principal del repo en GitHub.
- Pestaña de commits (historial).
- `README.md` renderizado en GitHub (secciones de arquitectura, tecnologías y diagrama).
- Carpeta `app/build/outputs/apk/release/app-release.apk`.
- Carpeta `app/build/outputs/bundle/release/app-release.aab`.

**🎙️ Decís:**

> "Para cerrar, les muestro los entregables. Este es el repositorio en
> GitHub, con el historial de commits de todo el desarrollo — se puede ver
> que fui avanzando por etapas: primero la navegación y Room, después
> Retrofit, después la separación en capas, la cámara, y al final el
> catálogo propio y la firma de la app.
>
> En el README documenté la descripción del proyecto, el objetivo, la
> arquitectura con el árbol de carpetas real, las tecnologías usadas, para
> qué se usa la API de TheMealDB, y un diagrama de la arquitectura en
> capas.
>
> Y estos son los dos artefactos firmados que generé con Gradle: el APK,
> para instalación directa en un celular, y el AAB, que es el formato que
> pide Play Store. Ambos están firmados con un keystore propio que generé
> con `keytool` — ese archivo no está en el repositorio por seguridad, está
> excluido en el `.gitignore` junto con las contraseñas."

---

## Minuto 13–15 — Conclusión

**🖥️ Pantalla:** volvés a cámara (o dejás el README abierto de fondo).

**📂 Archivos:** ninguno.

**🎙️ Decís:**

> "Para cerrar, algunas reflexiones sobre el proyecto.
>
> Lo que más aprendí fue a pensar en capas: al principio tenía todo mezclado
> en las pantallas, y separar Domain y Data me obligó a pensar cada
> funcionalidad como un flujo claro entre UI, ViewModel, Use Case y
> Repository. También aprendí a manejar Coroutines y Flow de forma
> reactiva, en vez de pedir datos una sola vez.
>
> Entre las decisiones técnicas, elegí el patrón Repository para no tener
> que elegir entre Retrofit o Room desde la pantalla — el Repository decide
> eso por mí. Elegí TheMealDB porque es una API pública y gratuita, sin
> necesidad de API key, ideal para un proyecto académico. Y elegí un estado
> sellado con Loading, Success y Error en el ViewModel para que la UI nunca
> se quede en un estado ambiguo.
>
> El beneficio principal de esta arquitectura es que cada capa se puede
> cambiar sin romper las demás: si mañana cambio la API o agrego una base
> de datos remota, el ViewModel y la UI ni se enteran. Eso es lo que buscaba
> demostrar con este proyecto. Muchas gracias."

---

## Preguntas frecuentes del docente y respuestas breves

**¿Por qué elegiste MVVM y no otra arquitectura?**
> "Porque es el patrón recomendado oficialmente por Google para Android y
> se integra muy bien con Jetpack Compose a través de `StateFlow` y
> `collectAsState`. Además separa claramente el estado de la UI de la
> lógica de negocio."

**¿Cuál es la diferencia entre el ViewModel y un Use Case?**
> "El ViewModel maneja el estado de la pantalla y el ciclo de vida de
> Android; el Use Case es lógica de negocio pura, sin depender de Android,
> que representa una sola acción (buscar, guardar, eliminar). El ViewModel
> orquesta Use Cases, no accede directo al Repository."

**¿Por qué usás Repository si ya tenés Room y Retrofit por separado?**
> "Porque así el resto de la app no necesita saber si un dato viene de
> internet o de la base local. El Repository combina ambas fuentes y
> expone una sola API limpia hacia el Domain."

**¿Cómo manejás los errores de red?**
> "En el ViewModel capturo `IOException` para errores de conexión,
> `HttpException` para errores del servidor, y una excepción genérica como
> respaldo, sin swallowear la cancelación de corrutinas. Cada caso mapea a
> un estado `Error` con un mensaje distinto para el usuario."

**¿Por qué Flow y no simplemente una función suspend que devuelva la lista una vez?**
> "Porque los datos de Room cambian con el tiempo — favoritos que se
> agregan o eliminan — y con Flow la pantalla se recompone sola cuando
> cambia la base, sin tener que pedir los datos de nuevo a mano."

**¿Cómo pediste el permiso de la cámara?**
> "Con `ActivityResultContracts.RequestPermission()`, que es la API
> moderna de Android para permisos en tiempo de ejecución. Si el usuario lo
> rechaza, le muestro una pantalla con la explicación y un botón para
> volver a pedirlo, en vez de dejarlo trabado."

**¿Qué pasa si el usuario no tiene conexión a internet?**
> "La búsqueda contra TheMealDB muestra el estado de error correspondiente,
> pero los favoritos guardados y el catálogo propio de la chef siguen
> funcionando normalmente porque viven en Room, en el dispositivo."

**¿Dónde se guarda la foto que toma la cámara?**
> "En el almacenamiento interno privado de la app, con `context.filesDir`,
> y la ruta del archivo se guarda como texto en Room. No uso la galería
> pública para no pedir permisos de almacenamiento adicionales."

**¿El proyecto tiene tests?**
> "No llegué a escribir tests automatizados en esta entrega, pero la
> arquitectura en capas —sobre todo los Use Cases sin dependencias de
> Android— está pensada justamente para que sean fáciles de testear a
> futuro con JUnit y con fakes del Repository."

**¿Cómo firmaste el APK y el AAB?**
> "Generé un keystore propio con `keytool`, configuré un `signingConfig`
> en Gradle que lee las credenciales desde un archivo `keystore.properties`
> que no subo al repositorio, y corrí `./gradlew assembleRelease` y
> `./gradlew bundleRelease` para generar ambos artefactos firmados."
