package service.constraint

enum class PasswordCheckConstraintEnum {
    DIGIT,
    EMPTY,
    INVALID_CHAR,
    LENGTH,
    LOWERCASE,
    REPEATED_CHAR,
    SPECIAL_CHAR,
    UPPERCASE
}