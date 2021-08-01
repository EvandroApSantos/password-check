package configuration

import com.typesafe.config.ConfigFactory
import controller.PasswordCheckController
import io.ktor.config.HoconApplicationConfig
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import service.PasswordCheckService

object KodeinConfiguration {
    private val mainModule = Kodein.Module("main") {
        bind() from singleton {
            HoconApplicationConfig(ConfigFactory.load().resolve())
        }
        bind() from singleton { JacksonConfiguration() }
        bind() from singleton { PasswordCheckService(createConstraintConfigurationV1(instance())) }
        bind() from singleton { PasswordCheckController(instance()) }
    }

    internal val kodein = Kodein {
        import(mainModule)
    }
}