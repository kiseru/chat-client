package ru.kiseru.chat.client.service.user

import ru.kiseru.chat.client.domain.user.User
import java.io.InputStream

interface UserCreationService {

    fun authorize(inputStream: InputStream): User
}
