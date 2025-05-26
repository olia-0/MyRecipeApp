package com.example.myrecipeapp.domain.usecase

import javax.inject.Inject

class MealInstructionsUseCase @Inject constructor() {

    fun splitInstructions(instructions: String?): List<String> {
        return instructions?.split("\\r\\n","\r\n","\n") ?: emptyList()
    }
}
