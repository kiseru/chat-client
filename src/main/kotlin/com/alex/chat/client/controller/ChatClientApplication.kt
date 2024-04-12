package com.alex.chat.client.controller

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket
import java.util.Scanner

fun main() = runBlocking<Unit> {
    println("Welcome to the alexis chat!")
    val socket = Socket("localhost", 5003)
    val authorities = readAuthorities()
    val outputStream = socket.getOutputStream()
    authorize(outputStream, authorities)
    launch { startReceiver(socket.getInputStream()) }
    launch { startSender(outputStream) }
}

private suspend fun readAuthorities(): Authorities {
    val scanner = Scanner(System.`in`)
    print("Enter your username: ")
    val username = withContext(Dispatchers.IO) { scanner.next() }
    print("Enter group for joining: ")
    val groupName = withContext(Dispatchers.IO) { scanner.next() }
    return Authorities(username, groupName)
}

private suspend fun authorize(outputStream: OutputStream, authorities: Authorities) = withContext(Dispatchers.IO) {
    val writer = outputStream.bufferedWriter()
    writer.write(authorities.username)
    writer.newLine()
    writer.write(authorities.groupName)
    writer.newLine()
    writer.flush()
}

private suspend fun startReceiver(inputStream: InputStream) = withContext(Dispatchers.IO) {
    flow<String> {
        val reader = inputStream.bufferedReader()
        while (true) {
            emit(reader.readLine())
        }
    }
        .flowOn(Dispatchers.IO)
        .collect { println(it) }
}

private suspend fun startSender(outputStream: OutputStream) {
    val writer = outputStream.bufferedWriter()
    flow<String> {
        val scanner = Scanner(System.`in`)
        while (true) {
            emit(scanner.nextLine())
        }
    }
        .onEach {
            checkExit(it)
            writer.write(it)
            writer.newLine()
            writer.flush()
        }
        .catch { writer.close() }
        .flowOn(Dispatchers.IO)
        .onCompletion { println("Logging out! Goodbye!") }
        .collect {}
}

private fun checkExit(msg: String) {
    if (msg == "q") {
        throw ExitException()
    }
}

data class Authorities(
    val username: String,
    val groupName: String,
)

class ExitException : RuntimeException()
