package service.constraint

import model.PasswordCheckFailMessages

class SpecialCharacterConstraint :
    RegexConstraint(regex = Regex("[!@#\$%^&*()\\-+]"), failMessage = PasswordCheckFailMessages.missingSpecialChar)