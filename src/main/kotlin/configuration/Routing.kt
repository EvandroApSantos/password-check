package configuration

import controller.PasswordCheckController
import io.ktor.application.call
import io.ktor.routing.Route
import io.ktor.routing.post
import io.ktor.routing.route

fun Route.passwordCheck(controller: PasswordCheckController) {
    route("/v1/password") {
        post("/check") {
            controller.performPasswordCheck(call)
        }
    }
}