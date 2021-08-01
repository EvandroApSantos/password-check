package configuration

import io.ktor.config.ApplicationConfig
import logger
import service.constraint.DigitConstraint
import service.constraint.EmptyConstraint
import service.constraint.InvalidCharConstraint
import service.constraint.LengthConstraint
import service.constraint.LowercaseConstraint
import service.constraint.PasswordCheckConstraint
import service.constraint.PasswordCheckConstraintEnum
import service.constraint.RepeatedCharacterConstraint
import service.constraint.SpecialCharacterConstraint
import service.constraint.UppercaseConstraint

fun createConstraintConfigurationV1(config: ApplicationConfig): Set<PasswordCheckConstraint> =
    config.propertyOrNull("constraints.v1")
        ?.getList()
        ?.mapNotNull { constraintFactory(it) }
        ?.toSet()
        ?: setOf()

private fun constraintFactory(name: String): PasswordCheckConstraint? {
    val enum = try {
        PasswordCheckConstraintEnum.valueOf(name)
    } catch (t: Throwable) {
        logger.warn { "Invalid constraint: $name. Ignoring..." }
        null
    }
    return when (enum) {
        PasswordCheckConstraintEnum.DIGIT -> DigitConstraint()
        PasswordCheckConstraintEnum.EMPTY -> EmptyConstraint()
        PasswordCheckConstraintEnum.INVALID_CHAR -> InvalidCharConstraint()
        PasswordCheckConstraintEnum.LENGTH -> LengthConstraint()
        PasswordCheckConstraintEnum.LOWERCASE -> LowercaseConstraint()
        PasswordCheckConstraintEnum.REPEATED_CHAR -> RepeatedCharacterConstraint()
        PasswordCheckConstraintEnum.SPECIAL_CHAR -> SpecialCharacterConstraint()
        PasswordCheckConstraintEnum.UPPERCASE -> UppercaseConstraint()
        else -> null
    }
}