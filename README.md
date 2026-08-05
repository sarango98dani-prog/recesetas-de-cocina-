# Recetario

Proyecto final individual — Asignatura de Aplicaciones Móviles.

## Descripción

Recetario es una app para buscar recetas en una API pública, guardar tus favoritas
localmente y anotar tus propias notas y fotos de cada plato que prepares.

## Arquitectura

MVVM + patrón Repositorio (capas `data` / `domain` / `ui`, esta última se
introduce en la Semana 3 según el cronograma del curso):

- **UI (Compose)**: `ui/screens` — lista de recetas guardadas, detalle, ajustes.
- **ViewModel**: `viewmodel` — expone `StateFlow` a la UI, nunca accede a Retrofit/DAO directamente (a partir de la Semana 3 pasa por el Repository).
- **Data local (Room)**: `data/local` — recetas guardadas con notas y foto propia.
- **Data preferencias (DataStore)**: `data/datastore` — unidad de medida (métrico/imperial).
- **Data remota (Retrofit)**: se agrega en la Semana 2, contra [TheMealDB](https://www.themealdb.com/api.php) (API pública gratuita, sin key).

## API utilizada

TheMealDB (`https://www.themealdb.com/api/json/v1/1/`) — búsqueda de recetas por nombre/ingrediente.

## Estado del proyecto

- [x] Semana 1 — pantallas base (Compose + Navigation + Room), ajuste de unidad en DataStore.
- [ ] Semana 2 — Retrofit conectado a TheMealDB.
- [ ] Semana 3 — capas data/domain/ui + Repository.
- [ ] Semana 4 — cámara para foto propia del plato, permisos en tiempo de ejecución.
- [ ] Semana 5 — pulido de estados de carga/error, `.aab` firmado.

## Capturas de pantalla

_(agregar en Semana 5)_

## Diagrama de arquitectura

_(agregar en Semana 3, cuando se introduce el Repository)_
