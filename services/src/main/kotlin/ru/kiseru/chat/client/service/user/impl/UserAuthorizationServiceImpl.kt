package ru.kiseru.chat.client.service.user.impl

import ru.kiseru.chat.client.domain.user.User
import ru.kiseru.chat.client.service.user.UserAuthorizationService
import java.io.OutputStream
import java.io.PrintWriter

class UserAuthorizationServiceImpl : UserAuthorizationService {

    override fun authorize(user: User, outputStream: OutputStream) {
        val printWriter = PrintWriter(outputStream)
        printWriter.println(user.username)
        printWriter.println(user.group)
        printWriter.flush()
    }
}
