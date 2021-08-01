package model

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class PasswordCheckResult(
    val result: Boolean,
    val failMessage: String? = null
)