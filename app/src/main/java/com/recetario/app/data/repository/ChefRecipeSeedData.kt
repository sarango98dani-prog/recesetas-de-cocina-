package com.recetario.app.data.repository

import com.recetario.app.domain.model.ChefRecipe
import com.recetario.app.domain.model.Difficulty
import com.recetario.app.domain.model.Ingredient

// Catálogo interno de recetas propias de la Chef Daniela Jima, precargado en el
// primer arranque de la app (ver ChefRecipeRepository.seedIfEmpty()).
object ChefRecipeSeedData {

    val recipes: List<ChefRecipe> = listOf(
        ChefRecipe(
            id = "chef-empanadas-viento",
            name = "Empanadas de Viento",
            category = "Entradas",
            ingredients = listOf(
                Ingredient("Harina de trigo", "500 g"),
                Ingredient("Manteca", "100 g"),
                Ingredient("Queso fresco rallado", "300 g"),
                Ingredient("Agua tibia", "200 ml"),
                Ingredient("Sal", "1 cdita"),
                Ingredient("Aceite para freír", "500 ml")
            ),
            steps = listOf(
                "Mezclar la harina, la manteca y la sal hasta lograr una textura arenosa.",
                "Agregar agua tibia de a poco y amasar hasta obtener una masa suave.",
                "Dividir en bolitas, estirar cada una y rellenar con queso.",
                "Cerrar en forma de media luna y sellar los bordes.",
                "Freír en aceite caliente hasta dorar y servir con azúcar espolvoreada."
            ),
            prepTimeMinutes = 40,
            difficulty = Difficulty.FACIL
        ),
        ChefRecipe(
            id = "chef-ceviche-camaron",
            name = "Ceviche de Camarón",
            category = "Entradas",
            ingredients = listOf(
                Ingredient("Camarón pelado", "500 g"),
                Ingredient("Jugo de limón", "200 ml"),
                Ingredient("Tomate picado", "2 unidades"),
                Ingredient("Cebolla morada", "1 unidad"),
                Ingredient("Cilantro picado", "2 cdas"),
                Ingredient("Salsa de tomate", "3 cdas"),
                Ingredient("Sal y pimienta", "al gusto")
            ),
            steps = listOf(
                "Cocinar los camarones en agua hirviendo por 2 minutos y enfriar.",
                "Encurtir la cebolla morada en jugo de limón por 10 minutos.",
                "Mezclar camarones, tomate, cebolla, cilantro y salsa de tomate.",
                "Sazonar con sal y pimienta y enfriar antes de servir."
            ),
            prepTimeMinutes = 30,
            difficulty = Difficulty.MEDIA
        ),
        ChefRecipe(
            id = "chef-llapingachos",
            name = "Llapingachos con Chorizo",
            category = "Platos principales",
            ingredients = listOf(
                Ingredient("Papa", "1 kg"),
                Ingredient("Queso fresco", "200 g"),
                Ingredient("Achiote", "2 cdas"),
                Ingredient("Cebolla blanca picada", "1 unidad"),
                Ingredient("Chorizo", "6 unidades"),
                Ingredient("Maní molido", "100 g")
            ),
            steps = listOf(
                "Cocinar y hacer puré las papas, mezclar con cebolla y achiote.",
                "Formar tortillas rellenas de queso y dorar en la plancha.",
                "Freír o asar los chorizos.",
                "Preparar salsa de maní y servir todo junto con ensalada."
            ),
            prepTimeMinutes = 50,
            difficulty = Difficulty.MEDIA
        ),
        ChefRecipe(
            id = "chef-seco-pollo",
            name = "Seco de Pollo",
            category = "Platos principales",
            ingredients = listOf(
                Ingredient("Presas de pollo", "1 kg"),
                Ingredient("Cerveza o chicha", "1 taza"),
                Ingredient("Cilantro", "1 atado"),
                Ingredient("Cebolla y pimiento", "1 c/u"),
                Ingredient("Ajo", "3 dientes"),
                Ingredient("Arroz cocido", "para acompañar")
            ),
            steps = listOf(
                "Sofreír cebolla, pimiento y ajo hasta dorar.",
                "Agregar el pollo y sellar por todos los lados.",
                "Licuar cilantro con un poco de agua y añadir junto con la cerveza.",
                "Cocinar a fuego medio hasta que el pollo esté tierno.",
                "Servir con arroz blanco."
            ),
            prepTimeMinutes = 60,
            difficulty = Difficulty.MEDIA
        ),
        ChefRecipe(
            id = "chef-tres-leches",
            name = "Tres Leches",
            category = "Postres",
            ingredients = listOf(
                Ingredient("Harina", "2 tazas"),
                Ingredient("Huevos", "5 unidades"),
                Ingredient("Azúcar", "1 taza"),
                Ingredient("Leche evaporada", "1 lata"),
                Ingredient("Leche condensada", "1 lata"),
                Ingredient("Crema de leche", "1 taza")
            ),
            steps = listOf(
                "Batir huevos con azúcar hasta espumar, agregar harina con movimientos envolventes.",
                "Hornear a 180°C por 30 minutos y dejar enfriar.",
                "Mezclar las tres leches y bañar el bizcocho perforado.",
                "Refrigerar al menos 2 horas antes de decorar con crema."
            ),
            prepTimeMinutes = 90,
            difficulty = Difficulty.MEDIA
        ),
        ChefRecipe(
            id = "chef-higos-queso",
            name = "Higos con Queso",
            category = "Postres",
            ingredients = listOf(
                Ingredient("Higos", "12 unidades"),
                Ingredient("Panela o azúcar morena", "300 g"),
                Ingredient("Canela en rama", "1 unidad"),
                Ingredient("Queso fresco", "200 g")
            ),
            steps = listOf(
                "Hacer un corte en cruz a cada higo y limpiar bien.",
                "Cocinar los higos con panela, canela y agua hasta que estén suaves y almibarados.",
                "Servir tibios acompañados de una porción de queso fresco."
            ),
            prepTimeMinutes = 45,
            difficulty = Difficulty.FACIL
        ),
        ChefRecipe(
            id = "chef-canelazo",
            name = "Canelazo",
            category = "Bebidas",
            ingredients = listOf(
                Ingredient("Agua", "1 litro"),
                Ingredient("Canela en rama", "2 unidades"),
                Ingredient("Panela o azúcar", "150 g"),
                Ingredient("Jugo de naranjilla", "1/2 taza"),
                Ingredient("Aguardiente (opcional)", "100 ml")
            ),
            steps = listOf(
                "Hervir el agua con la canela y la panela por 10 minutos.",
                "Agregar el jugo de naranjilla y cocinar 5 minutos más.",
                "Retirar del fuego, añadir el aguardiente si se desea y servir caliente."
            ),
            prepTimeMinutes = 20,
            difficulty = Difficulty.FACIL
        ),
        ChefRecipe(
            id = "chef-jugo-naranjilla",
            name = "Jugo de Naranjilla",
            category = "Bebidas",
            ingredients = listOf(
                Ingredient("Naranjilla", "6 unidades"),
                Ingredient("Agua", "500 ml"),
                Ingredient("Azúcar", "al gusto"),
                Ingredient("Hielo", "al gusto")
            ),
            steps = listOf(
                "Lavar y cortar las naranjillas por la mitad.",
                "Licuar con agua y colar para retirar las semillas.",
                "Endulzar al gusto y servir con hielo."
            ),
            prepTimeMinutes = 10,
            difficulty = Difficulty.FACIL
        ),
        ChefRecipe(
            id = "chef-locro-papa",
            name = "Locro de Papa",
            category = "Cocina tradicional",
            ingredients = listOf(
                Ingredient("Papa", "1 kg"),
                Ingredient("Cebolla blanca", "1 unidad"),
                Ingredient("Achiote", "2 cdas"),
                Ingredient("Leche", "1 taza"),
                Ingredient("Queso fresco", "200 g"),
                Ingredient("Aguacate", "1 unidad")
            ),
            steps = listOf(
                "Sofreír la cebolla con achiote hasta transparentar.",
                "Agregar las papas picadas y cubrir con agua, cocinar hasta que se deshagan.",
                "Añadir la leche y el queso, cocinar 10 minutos más revolviendo.",
                "Servir con aguacate y una presa de queso encima."
            ),
            prepTimeMinutes = 45,
            difficulty = Difficulty.FACIL
        ),
        ChefRecipe(
            id = "chef-encebollado",
            name = "Encebollado",
            category = "Cocina tradicional",
            ingredients = listOf(
                Ingredient("Atún o albacora", "600 g"),
                Ingredient("Yuca", "1 kg"),
                Ingredient("Cebolla morada encurtida", "2 unidades"),
                Ingredient("Tomate", "2 unidades"),
                Ingredient("Cilantro", "1 atado"),
                Ingredient("Limón", "al gusto")
            ),
            steps = listOf(
                "Cocinar la yuca hasta que esté suave y reservar el caldo.",
                "Cocinar el pescado en el mismo caldo con tomate y cilantro.",
                "Servir el caldo con yuca y pescado, cubrir con cebolla encurtida.",
                "Acompañar con limón, ají y chifles."
            ),
            prepTimeMinutes = 60,
            difficulty = Difficulty.MEDIA
        )
    )
}
