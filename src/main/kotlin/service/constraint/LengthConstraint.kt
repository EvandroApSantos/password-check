package service.constraint

import model.PasswordCheckFailMessages
import model.PasswordCheckResult

const val PASSWORD_MINIMUM_LENGTH = 9

class LengthConstraint: PasswordCheckConstraint {
    override fun doCheck(password: String): PasswordCheckResult =
        if (password.length < PASSWORD_MINIMUM_LENGTH) {
            PasswordCheckResult(result = false, failMessage = PasswordCheckFailMessages.invalidLength)
        } else {
            PasswordCheckResult(result = true)
        }
}