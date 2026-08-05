# Recetario

Proyecto final individual — Asignatura de Aplicaciones Móviles.

## Descripción

Recetario es una aplicación Android que permite buscar recetas de cocina en una
API pública, guardarlas como favoritas para consultarlas sin conexión, anotarles
notas propias y una fotografía del plato preparado, y ajustar preferencias como
la unidad de medida de los ingredientes.

## Objetivo

Aplicar en un proyecto completo los conceptos centrales de desarrollo Android
moderno: interfaz declarativa con Jetpack Compose, arquitectura MVVM + Repository
con separación en capas, persistencia local, consumo de una API REST, uso de una
función de hardware del dispositivo (cámara) y publicación de una build firmada
lista para distribución.

## Arquitectura

MVVM + patrón Repositorio, con capas `ui` / `domain` / `data`:

```
ui/
 ├── screens      → RecipeListScreen, FavoritesScreen, RecipeDetailScreen,
 │                  CameraScreen, SettingsScreen
 └── components   → RecetarioBottomBar

viewmodel/        → RecipeListViewModel, FavoritesViewModel, RecipeDetailViewModel,
                     CameraViewModel, SettingsViewModel (exponen StateFlow, nunca
                     acceden a Retrofit/Room/DataStore directamente)

domain/
 ├── model        → Recipe, UnitSystem
 └── usecase      → GetSavedRecipesUseCase, SearchRecipesUseCase,
                     AddToFavoritesUseCase, SaveRecipeUseCase, DeleteRecipeUseCase,
                     GetRecipeByIdUseCase, GetUnitSystemUseCase, SetUnitSystemUseCase

data/
 ├── local        → RecipeEntity, RecipeDao, AppDatabase (Room)
 ├── remote       → MealApiService, MealDto, MealsResponseDto, RetrofitInstance,
 │                  MealMapper (TheMealDB)
 ├── repository   → RecipeRepository, UserPreferencesRepository
 └── datastore    → UserPreferencesDataStore (unidad de medida)
```

Cada ViewModel depende únicamente de casos de uso del `domain`; los casos de uso
dependen de los repositorios en `data`, que son el único punto de acceso a Room,
Retrofit y DataStore. La UI (Compose) solo conoce modelos de `domain`, nunca
entidades de Room ni DTOs de la API.

## Tecnologías utilizadas

- **Jetpack Compose** — UI declarativa (Material 3, Navigation Compose).
- **Room** — persistencia local de recetas favoritas (notas y foto propia).
- **DataStore (Preferences)** — preferencias de usuario (unidad métrica/imperial).
- **Retrofit + OkHttp + Gson** — consumo de la API REST, con logging interceptor.
- **CameraX** — captura de la fotografía propia del plato.
- **TheMealDB API** (`https://www.themealdb.com/api/json/v1/1/`) — búsqueda de
  recetas por nombre, pública y sin API key.
- **Kotlin Coroutines / Flow** — asincronismo y estados reactivos.
- **Coil** — carga de imágenes remotas y locales.

## Funcionalidades implementadas

- **Búsqueda de recetas**: pantalla principal con búsqueda por nombre contra
  TheMealDB, con estados de carga, éxito y error (sin conexión, error de
  servidor, error inesperado).
- **Favoritos**: guardar recetas encontradas y consultarlas sin conexión desde
  la pestaña Favoritos (Room), con opción de eliminarlas.
- **Detalle de receta**: nombre, categoría, área, notas propias editables y foto
  del plato.
- **Cámara**: captura de una fotografía propia del plato con CameraX, con
  solicitud del permiso `CAMERA` en tiempo de ejecución, guardado en
  almacenamiento interno de la app y persistencia de la ruta en Room.
- **Ajustes**: selección de unidad de medida (métrico/imperial), persistida con
  DataStore.
- **Navegación completa**: barra inferior (Buscar / Favoritos) y pantallas
  apiladas (Detalle, Cámara, Ajustes).

## Cómo ejecutar el proyecto

Requisitos: Android Studio reciente, JDK 21, un dispositivo o emulador con
API 26+ y conexión a Internet (para la búsqueda de recetas).

1. Clonar el repositorio.
2. Abrir la carpeta del proyecto en Android Studio y esperar la sincronización
   de Gradle (o ejecutar `./gradlew build` desde la terminal).
3. Ejecutar la app en un dispositivo/emulador (▶ en Android Studio, o
   `./gradlew installDebug`).
4. Al usar la cámara por primera vez, aceptar el permiso solicitado.

## Cómo generar el AAB firmado

El build de release firma la app usando `keystore.properties`, un archivo local
que **no se versiona** (está en `.gitignore`).

1. Generar un keystore propio (una sola vez):
   ```
   keytool -genkeypair -v -keystore keystore/recetario-release.jks -alias recetario -keyalg RSA -keysize 2048 -validity 10000
   ```
2. Crear `keystore.properties` en la raíz del proyecto con este formato:
   ```
   storeFile=keystore/recetario-release.jks
   storePassword=<tu contraseña>
   keyAlias=recetario
   keyPassword=<tu contraseña>
   ```
3. Generar el paquete:
   ```
   ./gradlew bundleRelease
   ```
4. El `.aab` firmado queda en `app/build/outputs/bundle/release/app-release.aab`.

Si `keystore.properties` no existe, el build de `debug` funciona igual, pero el
de `release` queda sin firmar hasta agregarlo.

**Importante**: el keystore y sus contraseñas no deben subirse al repositorio.
Guardá una copia de respaldo del `.jks` fuera del proyecto (si se pierde, no se
pueden publicar actualizaciones futuras de la app con la misma identidad).

## Estado del proyecto

- [x] Pantallas base (Compose + Navigation) y Room para favoritos.
- [x] Retrofit conectado a TheMealDB, con estados Loading/Success/Error.
- [x] Capas `data` / `domain` / `ui` con patrón Repository.
- [x] Cámara (CameraX) para foto propia del plato, con permisos en tiempo de
      ejecución.
- [x] AAB firmado con keystore propio.
