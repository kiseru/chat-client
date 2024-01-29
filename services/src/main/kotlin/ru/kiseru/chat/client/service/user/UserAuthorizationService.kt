package ru.kiseru.chat.client.service.user

import ru.kiseru.chat.client.domain.user.User
import java.io.OutputStream

interface UserAuthorizationService {

    fun authorize(user: User, outputStream: OutputStream)
}
