package service.constraint

import model.PasswordCheckResult

abstract class RegexConstraint(private val regex: Regex, private val failMessage: String) : PasswordCheckConstraint {

    override fun doCheck(password: String): PasswordCheckResult =
        if (password.isNotEmpty() && !regex.containsMatchIn(password)) {
            PasswordCheckResult(result = false, failMessage = failMessage)
        } else {
            PasswordCheckResult(result = true)
        }
}