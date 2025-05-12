package com.example.myrecipeapp.data.mapper

import com.example.myrecipeapp.data.remote.dto.CategoriesDTO
import com.example.myrecipeapp.domain.model.Categories
import com.example.myrecipeapp.domain.model.RecipeShort


fun CategoriesDTO.toCategories(): Categories {
    return Categories(strCategory = strCategory)
}
//object CategoryMapper {
//    fun fromDto(dto: CategoriesDTO): Category? {
//        return dto.idCategory?.let {
//            Category(
//                idCategory = it,
//                strCategory = dto.strCategory,
//                strCategoryThumb = dto.strCategoryThumb,
//                strCategoryDescription = dto.strCategoryDescription
//            )
//        }
//    }

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
//}