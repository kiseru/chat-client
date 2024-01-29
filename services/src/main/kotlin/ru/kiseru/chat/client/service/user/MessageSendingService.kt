package ru.kiseru.chat.client.service.user

import java.io.OutputStream

interface MessageSendingService {

    fun send(message: String, outputStream: OutputStream)
}
