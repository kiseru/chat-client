package ru.kiseru.chat.client.repository

import ru.kiseru.chat.client.domain.user.User
import ru.kiseru.chat.client.domain.user.UserRepository

class UserRepositoryImpl : UserRepository {

    override fun create(username: String, group: String) =
        User(username, group)
}
