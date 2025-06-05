package com.example.testappqr.data.models

data class UserCreationDTO(
    val userId : String,
    val lastName : String,
    val firstName : String,
    val email : String
)
data class SpecialityCreationDTO(
    val specialityName : String
)