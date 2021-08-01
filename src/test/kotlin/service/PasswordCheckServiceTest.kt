package service

import model.PasswordCheckFailMessages
import org.junit.Test
import service.constraint.DigitConstraint
import service.constraint.EmptyConstraint
import service.constraint.InvalidCharConstraint
import service.constraint.LengthConstraint
import service.constraint.LowercaseConstraint
import service.constraint.RepeatedCharacterConstraint
import service.constraint.SpecialCharacterConstraint
import service.constraint.UppercaseConstraint
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PasswordCheckServiceTest {
    private val constraints = setOf(
            EmptyConstraint(),
            InvalidCharConstraint(),
            LengthConstraint(),
            LowercaseConstraint(),
            UppercaseConstraint(),
            DigitConstraint(),
            SpecialCharacterConstraint(),
            RepeatedCharacterConstraint()
        )

    private val service = PasswordCheckService(constraints)

    @Test
    fun `Correct password check`() {
        val result = service.check("AbTp9!fok")
        assertTrue(result.result)
    }

    @Test
    fun `Incorrect password - Empty`() {
        val result = service.check("")
        assertFalse(result.result)
        assertEquals(result.failMessage, PasswordCheckFailMessages.emptyPassword)
    }

    @Test
    fun `Incorrect password - Only spaces`() {
        val result = service.check("   ")
        assertFalse(result.result)
        assertEquals(result.failMessage, PasswordCheckFailMessages.invalidChar)
    }

    @Test
    fun `Incorrect password - Invalid length`() {
        val result = service.check("abc")
        assertFalse(result.result)
        assertEquals(result.failMessage, PasswordCheckFailMessages.invalidLength)
    }

    @Test
    fun `Incorrect password - No digit`() {
        val result = service.check("AbTp*!fok")
        assertFalse(result.result)
        assertEquals(result.failMessage, PasswordCheckFailMessages.missingDigit)
    }

    @Test
    fun `Incorrect password - No lowercase letter`() {
        val result = service.check("ABTP9!FOK")
        assertFalse(result.result)
        assertEquals(result.failMessage, PasswordCheckFailMessages.missingLowercase)
    }

    @Test
    fun `Incorrect password - No uppercase letter`() {
        val result = service.check("abtp9!fok")
        assertFalse(result.result)
        assertEquals(result.failMessage, PasswordCheckFailMessages.missingUppercase)
    }

    @Test
    fun `Incorrect password - No special character`() {
        val result = service.check("AbpT91fok")
        assertFalse(result.result)
        assertEquals(result.failMessage, PasswordCheckFailMessages.missingSpecialChar)
    }

    @Test
    fun `Incorrect password - Invalid special character`() {
        val result = service.check("aBtp9=fok")
        assertFalse(result.result)
        assertEquals(result.failMessage, PasswordCheckFailMessages.invalidChar)
    }

    @Test
    fun `Incorrect password - Whitespace`() {
        val result = service.check("AbTp9 fok")
        assertFalse(result.result)
        assertEquals(result.failMessage, PasswordCheckFailMessages.invalidChar)
    }

    @Test
    fun `Incorrect password - Repeated character`() {
        val result = service.check("AbTp9!foA")
        assertFalse(result.result)
        assertEquals(result.failMessage, PasswordCheckFailMessages.repeatedChar)
    }

    @Test
    fun `Correct password - Repeated case-sensitive character`() {
        val result = service.check("AbTp9!foa")
        assertTrue(result.result)
    }

    @Test
    fun `Incorrect password - BR special chars`() {
        val result = service.check("AbTp9!foaçãé")
        assertFalse(result.result)
        assertEquals(result.failMessage, PasswordCheckFailMessages.invalidChar)
    }
}