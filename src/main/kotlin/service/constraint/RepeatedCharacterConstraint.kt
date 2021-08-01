package service.constraint

import extension.hasRepeatedChar
import model.PasswordCheckFailMessages
import model.PasswordCheckResult

class RepeatedCharacterConstraint: PasswordCheckConstraint {
    override fun doCheck(password: String): PasswordCheckResult =
        if (password.hasRepeatedChar()) {
            PasswordCheckResult(result = false, failMessage = PasswordCheckFailMessages.repeatedChar)
        } else {
            PasswordCheckResult(result = true)
        }
}