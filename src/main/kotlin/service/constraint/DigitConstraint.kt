package service.constraint

import model.PasswordCheckFailMessages

class DigitConstraint :
    RegexConstraint(regex = Regex("[0-9]"), failMessage = PasswordCheckFailMessages.missingDigit)