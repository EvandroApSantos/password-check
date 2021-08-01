package service.constraint

import model.PasswordCheckResult

interface PasswordCheckConstraint {
    fun doCheck(password: String): PasswordCheckResult
}
