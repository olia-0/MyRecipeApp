package com.example.myrecipeapp.data.mapper

import com.example.myrecipeapp.data.local.entity.SavedRecipeEntity
import com.example.myrecipeapp.data.local.entity.ViewedRecipeEntity
import com.example.myrecipeapp.data.remote.dto.MealDto
import com.example.myrecipeapp.data.remote.dto.RecipeShortDto
import com.example.myrecipeapp.domain.model.Meal
import com.example.myrecipeapp.domain.model.Recipe
import com.example.myrecipeapp.domain.model.RecipeShort

object RecipeMapper {

    fun MealDto.toRecipe(): Meal {
        return Meal(
            idRecipe = idMeal,
            nameRecipe = strMeal,
            photoRecipe = strMealThumb,
            categoryRecipe = strCategory,
            areaRecipe = strArea,
            instructionsRecipe = strInstructions,
            tagsRecipe = strTags,
            youtubeRecipe = strYoutube,
            strIngredient1 = strIngredient1,
            strIngredient2 = strIngredient2,
            strIngredient3 = strIngredient3,
            strIngredient4 = strIngredient4,
            strIngredient5 = strIngredient5,
            strIngredient6 = strIngredient6,
            strIngredient7 = strIngredient7,
            strIngredient8 = strIngredient8,
            strIngredient9 = strIngredient9,
            strIngredient10 = strIngredient10,
            strIngredient11 = strIngredient11,
            strIngredient12 = strIngredient12,
            strIngredient13 = strIngredient13,
            strIngredient14 = strIngredient14,
            strIngredient15 = strIngredient15,
            strIngredient16 = strIngredient16,
            strIngredient17 = strIngredient17,
            strIngredient18 = strIngredient18,
            strIngredient19 = strIngredient19,
            strIngredient20 = strIngredient20,
            strMeasure1 = strMeasure1,
            strMeasure2 = strMeasure2,
            strMeasure3 = strMeasure3,
            strMeasure4 = strMeasure4,
            strMeasure5 = strMeasure5,
            strMeasure6 = strMeasure6,
            strMeasure7 = strMeasure7,
            strMeasure8 = strMeasure8,
            strMeasure9 = strMeasure9,
            strMeasure10 = strMeasure10,
            strMeasure11 = strMeasure11,
            strMeasure12 = strMeasure12,
            strMeasure13 = strMeasure13,
            strMeasure14 = strMeasure14,
            strMeasure15 = strMeasure15,
            strMeasure16 = strMeasure16,
            strMeasure17 = strMeasure17,
            strMeasure18 = strMeasure18,
            strMeasure19 = strMeasure19,
            strMeasure20 = strMeasure20,
        )
    }
    fun fromDto(dto: MealDto): Meal? {
        return dto.idMeal.let {
            Meal(
                idRecipe = it,
                nameRecipe = dto.strMeal,
                photoRecipe = dto.strMealThumb,
                categoryRecipe = dto.strCategory,
                areaRecipe = dto.strArea,
                instructionsRecipe = dto.strInstructions,
                tagsRecipe = dto.strTags,
                youtubeRecipe = dto.strYoutube,
                strIngredient1 = dto.strIngredient1,
                strIngredient2 = dto.strIngredient2,
                strIngredient3 = dto.strIngredient3,
                strIngredient4 = dto.strIngredient4,
                strIngredient5 = dto.strIngredient5,
                strIngredient6 = dto.strIngredient6,
                strIngredient7 = dto.strIngredient7,
                strIngredient8 = dto.strIngredient8,
                strIngredient9 = dto.strIngredient9,
                strIngredient10 = dto.strIngredient10,
                strIngredient11 = dto.strIngredient11,
                strIngredient12 = dto.strIngredient12,
                strIngredient13 = dto.strIngredient13,
                strIngredient14 = dto.strIngredient14,
                strIngredient15 = dto.strIngredient15,
                strIngredient16 = dto.strIngredient16,
                strIngredient17 = dto.strIngredient17,
                strIngredient18 = dto.strIngredient18,
                strIngredient19 = dto.strIngredient19,
                strIngredient20 = dto.strIngredient20,
                strMeasure1 = dto.strMeasure1,
                strMeasure2 = dto.strMeasure2,
                strMeasure3 = dto.strMeasure3,
                strMeasure4 = dto.strMeasure4,
                strMeasure5 = dto.strMeasure5,
                strMeasure6 = dto.strMeasure6,
                strMeasure7 = dto.strMeasure7,
                strMeasure8 = dto.strMeasure8,
                strMeasure9 = dto.strMeasure9,
                strMeasure10 = dto.strMeasure10,
                strMeasure11 = dto.strMeasure11,
                strMeasure12 = dto.strMeasure12,
                strMeasure13 = dto.strMeasure13,
                strMeasure14 = dto.strMeasure14,
                strMeasure15 = dto.strMeasure15,
                strMeasure16 = dto.strMeasure16,
                strMeasure17 = dto.strMeasure17,
                strMeasure18 = dto.strMeasure18,
                strMeasure19 = dto.strMeasure19,
                strMeasure20 = dto.strMeasure20,
            )
        }
    }

//    fun fromEntity(entity: CategoriesEntity): Category {
//        return Category(
//            idCategory = entity.idCategory,
//            strCategory = entity.strCategory,
//            strCategoryThumb = entity.strCategoryThumb,
//            strCategoryDescription = entity.strCategoryDescription
//        )
//    }

//    fun toEntity(model: Category): CategoriesEntity {
//        return CategoriesEntity(
//            idCategory = model.idCategory,
//            strCategory = model.strCategory,
//            strCategoryThumb = model.strCategoryThumb,
//            strCategoryDescription = model.strCategoryDescription
//        )
//    }

//    fun toDto(model: Category): CategoriesDTO {
//        return CategoriesDTO(
//            idCategory = model.idCategory,
//            strCategory = model.strCategory,
//            strCategoryThumb = model.strCategoryThumb,
//            strCategoryDescription = model.strCategoryDescription
//        )
//    }
}
fun RecipeShortDto.toMealShort(): RecipeShort {
    return RecipeShort(id = idMeal, name = strMeal, thumbnail = strMealThumb)
}
fun Recipe.toRecipeShort(): RecipeShort {
    return RecipeShort(id = idRecipe, name = nameRecipe, thumbnail = photoRecipe ?: "")
}

fun RecipeShort.toRecipe(): Recipe {
    return Recipe(
        idRecipe = id,
        nameRecipe = name,
        photoRecipe = thumbnail,
        categoryRecipe = "",
        areaRecipe = "",
        instructionsRecipe = "",
        tagsRecipe = "",
        youtubeRecipe = "",
        ingredients = "",
        measures = ""
    )
}

fun MealDto.toRecipe3(): Meal {
    return Meal(
        idRecipe = idMeal,
        nameRecipe = strMeal,
        photoRecipe = strMealThumb,
        categoryRecipe = strCategory,
        areaRecipe = strArea,
        instructionsRecipe = strInstructions,
        tagsRecipe = strTags,
        youtubeRecipe = strYoutube,
        strIngredient1 = strIngredient1,
        strIngredient2 = strIngredient2,
        strIngredient3 = strIngredient3,
        strIngredient4 = strIngredient4,
        strIngredient5 = strIngredient5,
        strIngredient6 = strIngredient6,
        strIngredient7 = strIngredient7,
        strIngredient8 = strIngredient8,
        strIngredient9 = strIngredient9,
        strIngredient10 = strIngredient10,
        strIngredient11 = strIngredient11,
        strIngredient12 = strIngredient12,
        strIngredient13 = strIngredient13,
        strIngredient14 = strIngredient14,
        strIngredient15 = strIngredient15,
        strIngredient16 = strIngredient16,
        strIngredient17 = strIngredient17,
        strIngredient18 = strIngredient18,
        strIngredient19 = strIngredient19,
        strIngredient20 = strIngredient20,
        strMeasure1 = strMeasure1,
        strMeasure2 = strMeasure2,
        strMeasure3 = strMeasure3,
        strMeasure4 = strMeasure4,
        strMeasure5 = strMeasure5,
        strMeasure6 = strMeasure6,
        strMeasure7 = strMeasure7,
        strMeasure8 = strMeasure8,
        strMeasure9 = strMeasure9,
        strMeasure10 = strMeasure10,
        strMeasure11 = strMeasure11,
        strMeasure12 = strMeasure12,
        strMeasure13 = strMeasure13,
        strMeasure14 = strMeasure14,
        strMeasure15 = strMeasure15,
        strMeasure16 = strMeasure16,
        strMeasure17 = strMeasure17,
        strMeasure18 = strMeasure18,
        strMeasure19 = strMeasure19,
        strMeasure20 = strMeasure20,
    )
}

fun MealDto.toRecipe(): Recipe {
    val ingredientsList = listOf(
        strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5,
        strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10,
        strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15,
        strIngredient16, strIngredient17, strIngredient18, strIngredient19, strIngredient20
    ).filterNot { it.isNullOrBlank() }

    val measuresList = listOf(
        strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5,
        strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10,
        strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15,
        strMeasure16, strMeasure17, strMeasure18, strMeasure19, strMeasure20
    ).filterNot { it.isNullOrBlank() }

    return Recipe(
        idRecipe = idMeal,
        nameRecipe = strMeal,
        photoRecipe = strMealThumb,
        categoryRecipe = strCategory,
        areaRecipe = strArea,
        instructionsRecipe = strInstructions,
        tagsRecipe = strTags,
        youtubeRecipe = strYoutube,
        ingredients = ingredientsList.joinToString(","),
        measures = measuresList.joinToString(",")
    )
}
//////ROOM
//////////////SAVED
fun Recipe.toSavedRecipeEntity(imagePath: String): SavedRecipeEntity {
    return SavedRecipeEntity(
        id = idRecipe,
        title = nameRecipe,
        category = categoryRecipe ?: "",
        ingredients = ingredients,
        measures = measures,
        steps = instructionsRecipe ?: "",
        imagePath = imagePath,
        imageSize = 0
    )
}

fun SavedRecipeEntity.toRecipe(): Recipe {
    return Recipe(
        idRecipe = id,
        nameRecipe = title,
        categoryRecipe = category ?: "",
        ingredients = ingredients,
        measures = measures,
        instructionsRecipe = steps ?: "",
        photoRecipe = imagePath,
        youtubeRecipe = null,
        areaRecipe = null,
        tagsRecipe = null,
    )
}
fun SavedRecipeEntity.toRecipeShort(): RecipeShort {
    return RecipeShort(
        id = id,
        name = title,
        thumbnail = imagePath
    )
}
fun ViewedRecipeEntity.toRecipeShort(): RecipeShort {
    return RecipeShort(
        id = id,
        name = title,
        thumbnail = imagePath
    )
}
/////////////VIEWED
fun Recipe.toViewedEntity(): ViewedRecipeEntity {
    return ViewedRecipeEntity(
        id = idRecipe,
        title = nameRecipe,
        category = categoryRecipe ?: "",
        ingredients = ingredients,
        measures = measures,
        steps = instructionsRecipe ?: "",
        imagePath = photoRecipe ?: "",
        imageSizeBytes = 0
    )
}

fun ViewedRecipeEntity.toRecipe(): Recipe {
    return Recipe(
        idRecipe = id,
        nameRecipe = title,
        categoryRecipe = category ?: "",
        ingredients = ingredients,
        measures = measures,
        instructionsRecipe = steps ?: "",
        photoRecipe = imagePath,
        youtubeRecipe = null,
        areaRecipe = null,
        tagsRecipe = null,
    )
}

