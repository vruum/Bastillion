package io.bastillion.db.entities

data class User(val id: Long, val username: String,
                val name: String, val email: String) {
}