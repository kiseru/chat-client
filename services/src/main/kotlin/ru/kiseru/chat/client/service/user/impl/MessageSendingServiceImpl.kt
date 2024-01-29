package ru.kiseru.chat.client.service.user.impl

import ru.kiseru.chat.client.service.user.MessageSendingService
import java.io.OutputStream
import java.io.PrintWriter

class MessageSendingServiceImpl : MessageSendingService {

    override fun send(message: String, outputStream: OutputStream) {
        val writer = PrintWriter(outputStream)
        writer.println(message)
        writer.flush()
    }
}
