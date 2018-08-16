package br.com.zup.model

data class Result(
    val idUser: String,
    val name: String,
    val idSale: String
)

data class User(
    val id: String,
    val name: String
)

data class Sale(
    val id: String,
    val idUser: String
)
