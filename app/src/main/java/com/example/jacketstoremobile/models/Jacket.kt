package com.example.jacketstoremobile.models

data class Jacket(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var season: String = "",
    var type: String = "",
    var imageUrls: List<String> = emptyList()
)