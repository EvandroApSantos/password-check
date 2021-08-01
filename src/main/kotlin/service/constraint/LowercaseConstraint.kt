package service.constraint

import model.PasswordCheckFailMessages

class LowercaseConstraint :
    RegexConstraint(regex = Regex("[a-z]"), failMessage = PasswordCheckFailMessages.missingLowercase)