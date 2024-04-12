package com.alex.chat.client.controller

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.util.*

fun main() = runBlocking<Unit> {
    println("Welcome to the alexis chat!")
    val scanner = Scanner(System.`in`)
    val socket = createConnection()
    val reader = createReader(socket)
    val writer = createWriter(socket)
    val signInDto = readAuthorizationData(scanner)
    signIn(writer, signInDto)
    launch { startReceiver(reader) }
    launch { startSender(scanner, writer) }
}

private fun createConnection(): Socket {
    return Socket("localhost", 5003)
}

private fun createReader(socket: Socket): BufferedReader {
    return BufferedReader(InputStreamReader(socket.getInputStream()))
}

private fun createWriter(socket: Socket): PrintWriter {
    return PrintWriter(socket.getOutputStream(), true)
}

private fun readAuthorizationData(scanner: Scanner): SignInDto {
    print("Enter your username: ")
    val username = scanner.next()
    print("Enter group for joining: ")
    val groupName = scanner.next()
    return SignInDto(username, groupName)
}

private fun signIn(writer: PrintWriter, signInDto: SignInDto) {
    writer.println(signInDto.username)
    writer.println(signInDto.groupName)
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

data class SignInDto(
    val username: String,
    val groupName: String,
)

class ExitException : RuntimeException()