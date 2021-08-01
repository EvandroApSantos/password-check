package service.constraint

import model.PasswordCheckFailMessages
import model.PasswordCheckResult

class EmptyConstraint: PasswordCheckConstraint {
    override fun doCheck(password: String): PasswordCheckResult =
        if (password.isEmpty()) {
            PasswordCheckResult(result = false, failMessage = PasswordCheckFailMessages.emptyPassword)
        } else {
            PasswordCheckResult(result = true)
        }
}