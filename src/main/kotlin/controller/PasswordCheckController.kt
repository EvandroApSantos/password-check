package controller

import io.ktor.application.ApplicationCall
import io.ktor.request.receive
import io.ktor.response.respond
import model.PasswordCheckRequest
import service.PasswordCheckService

class PasswordCheckController(private val service: PasswordCheckService) {
    suspend fun performPasswordCheck(call: ApplicationCall) {
        val (password) = call.receive<PasswordCheckRequest>()
        call.respond(service.check(password))
    }
}