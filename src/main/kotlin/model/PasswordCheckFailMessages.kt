package model

object PasswordCheckFailMessages {
    const val emptyPassword = "The password is empty"
    const val invalidLength = "The password must have nine or more characters"
    const val missingDigit = "The password must have at least one digit"
    const val missingLowercase = "The password must have at least one lowercase letter"
    const val missingUppercase = "The password must have at least one uppercase letter"
    const val missingSpecialChar = "The password must have at least one of these special characters: !@#\$%^&*()-+"
    const val invalidChar = "The password can only contain letters, numbers and these special characters: !@#\$%^&*()-+"
    const val repeatedChar = "The password can not have repeated characters"
    const val invalidPayload = "The content is invalid or required fields are missing"
}