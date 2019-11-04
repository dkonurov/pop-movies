package com.example.bottom.navigation.ui.models

enum class FilterType {
    POPULARITY {
        override fun text(): String {
            return "popularity.desc"
        }
    },
    RELEASE_DATE {
        override fun text(): String {
            return "release_date.desc"
        }
    },
    VOTE_COUNT {
        override fun text(): String {
            return "vote_count.desc"
        }
    };

    abstract fun text(): String
}