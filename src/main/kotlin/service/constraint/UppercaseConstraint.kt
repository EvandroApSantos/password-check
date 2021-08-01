package service.constraint

import model.PasswordCheckFailMessages

class UppercaseConstraint :
    RegexConstraint(regex = Regex("[A-Z]"), failMessage = PasswordCheckFailMessages.missingUppercase)