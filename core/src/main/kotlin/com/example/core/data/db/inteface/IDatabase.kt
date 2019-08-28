package com.example.core.data.db.inteface

interface IDatabase {

    fun getFavoriteDao(): FavoriteDao

    fun getMovieDao(): MovieDao
}