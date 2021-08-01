package configuration

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.util.pipeline.PipelineContext
import logger
import model.PasswordCheckFailMessages

fun configureStatusPages(
    exceptions: MutableMap<Class<*>, suspend PipelineContext<*, ApplicationCall>.(Throwable) -> Unit>
) {
    exceptions[MismatchedInputException::class.java] = {
        handleInvalidContent(it, call)
    }
    exceptions[JsonMappingException::class.java] = {
        handleInvalidContent(it, call)
    }
    exceptions[JsonParseException::class.java] = {
        handleInvalidContent(it, call)
    }
}

private suspend fun handleInvalidContent(t: Throwable, call: ApplicationCall) {
    logger.warn { t.message }
    call.respond(HttpStatusCode.BadRequest, PasswordCheckFailMessages.invalidPayload)
}