package service

import model.PasswordCheckResult
import service.constraint.PasswordCheckConstraint

class PasswordCheckService(private val constraints: Set<PasswordCheckConstraint>) {
    fun check(password: String): PasswordCheckResult {
        constraints.forEach {
            it.doCheck(password).let {result ->
                if (!result.result) {
                    return result
                }
            }
        }
        return PasswordCheckResult(result = true)
    }
}