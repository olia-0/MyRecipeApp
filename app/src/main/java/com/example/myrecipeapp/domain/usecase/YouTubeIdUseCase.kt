package com.example.myrecipeapp.domain.usecase

import javax.inject.Inject

class YouTubeIdUseCase @Inject constructor() {
    fun extractYouTubeId(url: String?): String? {
        if (url.isNullOrEmpty()) return null
        val regex = """(?:https?://)?(?:www\.)?youtube\.com/watch\?v=([a-zA-Z0-9_-]+)""".toRegex()
        val matchResult = regex.find(url)
        return matchResult?.groups?.get(1)?.value
    }
}