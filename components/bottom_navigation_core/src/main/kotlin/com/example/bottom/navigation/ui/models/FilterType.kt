package com.example.bottom.navigation.ui.models

enum class FilterType(
    val text: String?,
) {
    POPULARITY(null),
    RELEASE_DATE("release_date.desc"),
    VOTE_COUNT("vote_count.desc"),
}