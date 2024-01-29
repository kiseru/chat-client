package ru.kiseru.chat.client.domain.user

interface UserRepository {

    fun create(username: String, group: String): User
}
