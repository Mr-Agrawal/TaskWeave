package com.example.taskweave.auth.domain.model

import com.google.firebase.auth.FirebaseUser

data class User(
    val uid: String,
    val email: String?
)

fun FirebaseUser.toUser(): User {
    return User(
        uid = uid,
        email = email
    )
}