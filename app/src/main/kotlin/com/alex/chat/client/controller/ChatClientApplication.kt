package com.alex.chat.client.controller

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import ru.kiseru.chat.client.domain.user.User
import ru.kiseru.chat.client.repository.UserRepositoryImpl
import ru.kiseru.chat.client.service.user.impl.UserAuthorizationServiceImpl
import ru.kiseru.chat.client.service.user.impl.UserCreationServiceImpl
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.PrintWriter
import java.net.Socket
import java.util.*

private const val HOST = "localhost"
private const val PORT = 5003

fun main() = runBlocking<Unit> {
    println("Welcome to the alexis chat!")
    val scanner = Scanner(System.`in`)
    val socket = createConnection()
    val reader = createReader(socket)
    val writer = createWriter(socket)
    val user = createUser(System.`in`)
    authorize(user, socket.getOutputStream())
    launch { startReceiver(reader) }
    launch { startSender(scanner, writer) }
}

private fun createConnection(): Socket {
    return Socket(HOST, PORT)
}

private fun createReader(socket: Socket): BufferedReader {
    return BufferedReader(InputStreamReader(socket.getInputStream()))
}

private fun createWriter(socket: Socket): PrintWriter {
    return PrintWriter(socket.getOutputStream(), true)
}

private fun createUser(inputStream: InputStream): User {
    val userRepository = UserRepositoryImpl()
    val userAuthorizationService = UserCreationServiceImpl(userRepository)
    return userAuthorizationService.authorize(inputStream)
}

private fun authorize(user: User, outputStream: OutputStream) {
    val userAuthorizationService = UserAuthorizationServiceImpl()
    userAuthorizationService.authorize(user, outputStream)
}

private suspend fun startReceiver(reader: BufferedReader) = withContext(Dispatchers.IO) {
    flow<String> {
        while (true) {
            emit(reader.readLine())
        }
    }
        .catch {}
        .collect { println(it) }
}


private suspend fun startSender(scanner: Scanner, writer: PrintWriter) = withContext(Dispatchers.IO) {
    flow<String> {
        while (true) {
            emit(scanner.nextLine())
        }
    }
        .onEach { checkExit(it) }
        .onCompletion { println("Logging out! Goodbye!") }
        .catch { writer.close() }
        .collect { writer.println(it) }
}

private fun checkExit(msg: String) {
    if (msg == "q") {
        throw ExitException()
    }
}

class ExitException : RuntimeException()
