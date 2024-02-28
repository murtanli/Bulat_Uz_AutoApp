package com.example.autoapp.api

data class LoginResponse (
    val message: String,
    val user_id: Int
)

data class salon_inf(
    val name: String,
    val address: String
)

data class All_cars(
    val pk: Int,
    val brand: String,
    val model: String,
    val price: Int,
    val image_url: String,
    val mileage: Int,
    val condition: String,
    val owners_count: Int,
    val car_type: String,
    val salon: salon_inf
)


data class SaveResponse (
    val message: String
)