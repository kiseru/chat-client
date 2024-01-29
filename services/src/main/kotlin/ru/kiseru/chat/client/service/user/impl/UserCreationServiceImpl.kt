package ru.kiseru.chat.client.service.user.impl

import ru.kiseru.chat.client.domain.user.User
import ru.kiseru.chat.client.domain.user.UserRepository
import ru.kiseru.chat.client.service.user.UserCreationService
import java.io.InputStream
import java.util.*

class UserCreationServiceImpl(
    private val userRepository: UserRepository
) : UserCreationService {

    override fun authorize(inputStream: InputStream): User {
        val scanner = Scanner(inputStream)
        print("Enter your username: ")
        val username = scanner.next()
        print("Enter group for joining: ")
        val groupName = scanner.next()
        return userRepository.create(username, groupName)
    }
}
