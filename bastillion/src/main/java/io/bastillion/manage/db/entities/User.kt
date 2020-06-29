package io.bastillion.manage.db.entities

data class User(val id: Long, val username: String,
                val email: String, val password: String?) {
}