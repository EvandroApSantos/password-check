import configuration.JacksonConfiguration
import configuration.KodeinConfiguration
import configuration.configureStatusPages
import configuration.passwordCheck
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.jackson.jackson
import io.ktor.routing.Routing
import mu.KotlinLogging
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

val logger = KotlinLogging.logger("easantos.password-check")

@Suppress("unused")
fun Application.main(): Kodein {
    return Kodein {
        extend(KodeinConfiguration.kodein, copy = Copy.All)

        onReady {
            val jacksonConfig = instance<JacksonConfiguration>()

            install(ContentNegotiation) {
                jackson {
                    jacksonConfig.objectMapper
                }
            }

            install(Routing) {
                passwordCheck(instance())
            }

            install(StatusPages) {
                configureStatusPages(exceptions)
            }
        }
    }
}
