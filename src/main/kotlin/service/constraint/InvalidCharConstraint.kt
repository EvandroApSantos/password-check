package service.constraint

import model.PasswordCheckFailMessages
import model.PasswordCheckResult

class InvalidCharConstraint: PasswordCheckConstraint {
    private val regex = Regex("[^a-zA-Z0-9!@#\$%^&*()\\-+]")

    override fun doCheck(password: String): PasswordCheckResult =
        if (regex.containsMatchIn(password)) {
            PasswordCheckResult(result = false, failMessage = PasswordCheckFailMessages.invalidChar)
        } else {
            PasswordCheckResult(result = true)
        }
}