package me.pedroeugenio.blazebot.domain

data class BlazeHistory<T>(
    val records: List<T>,

    val totalPages: Int
)