package com.example.bottom.navigation.ui.models

enum class FilterType {
    POPULARITY {
        override val text: String = "popularity.desc"
    },
    RELEASE_DATE {
        override val text: String = "release_date.desc"
    },
    VOTE_COUNT {
        override val text: String = "vote_count.desc"
    };

    abstract val text: String
}