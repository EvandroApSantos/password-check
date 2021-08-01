package controller

import com.fasterxml.jackson.module.kotlin.readValue
import configuration.JacksonConfiguration
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.TestApplicationRequest
import io.ktor.server.testing.TestApplicationResponse
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import main
import model.PasswordCheckFailMessages
import model.PasswordCheckResult
import org.junit.Test
import kotlin.test.assertEquals

class PasswordCheckControllerTest {
    private val path = "/v1/password/check"
    private val objectMapper = JacksonConfiguration().objectMapper

    @Test
    fun `Empty json`() =
        dispatchRequest {
            handleRequest(HttpMethod.Post, path) {
                setRequestBody(this, "{}")
            }.apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
                assertEquals(PasswordCheckFailMessages.invalidPayload, response.content)
            }
        }

    @Test
    fun `Empty body`() =
        dispatchRequest {
            handleRequest(HttpMethod.Post, path) {
                setRequestBody(this, "")
            }.apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
                assertEquals(PasswordCheckFailMessages.invalidPayload, response.content)
            }
        }

    @Test
    fun `Missing required field`() =
        dispatchRequest {
            handleRequest(HttpMethod.Post, path) {
                setRequestBody(this, """{"passwd": "123"}""")
            }.apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
                assertEquals(PasswordCheckFailMessages.invalidPayload, response.content)
            }
        }

    @Test
    fun `Empty password`() =
        dispatchRequest {
            handleRequest(HttpMethod.Post, path) {
                setRequestBody(this, createJsonString(""))
            }.apply {
                doResponseAssertions(response, PasswordCheckResult(false, PasswordCheckFailMessages.emptyPassword))
            }
        }

    @Test
    fun `Null password`() =
        dispatchRequest {
            handleRequest(HttpMethod.Post, path) {
                setRequestBody(this, """{"passwd": null}""")
            }.apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
                assertEquals(PasswordCheckFailMessages.invalidPayload, response.content)
            }
        }

    @Test
    fun `Only spaces`() =
        dispatchRequest {
            handleRequest(HttpMethod.Post, path) {
                setRequestBody(this, createJsonString("  "))
            }.apply {
                doResponseAssertions(response, PasswordCheckResult(false, PasswordCheckFailMessages.invalidChar))
            }
        }

    @Test
    fun `Invalid length`() =
        dispatchRequest {
            handleRequest(HttpMethod.Post, path) {
                setRequestBody(this, createJsonString("abc"))
            }.apply {
                doResponseAssertions(response, PasswordCheckResult(false, PasswordCheckFailMessages.invalidLength))
            }
        }

    @Test
    fun `No digit`() =
        dispatchRequest {
            handleRequest(HttpMethod.Post, path) {
                setRequestBody(this, createJsonString("AbTp*!fok"))
            }.apply {
                doResponseAssertions(response, PasswordCheckResult(false, PasswordCheckFailMessages.missingDigit))
            }
        }

    @Test
    fun `No lowercase`() =
        dispatchRequest {
            handleRequest(HttpMethod.Post, path) {
                setRequestBody(this, createJsonString("ABTP9!FOK"))
            }.apply {
                doResponseAssertions(response, PasswordCheckResult(false, PasswordCheckFailMessages.missingLowercase))
            }
        }

    @Test
    fun `No uppercase`() =
        dispatchRequest {
            handleRequest(HttpMethod.Post, path) {
                setRequestBody(this, createJsonString("abtp9!fok"))
            }.apply {
                doResponseAssertions(response, PasswordCheckResult(false, PasswordCheckFailMessages.missingUppercase))
            }
        }

    @Test
    fun `No special character`() =
        dispatchRequest {
            handleRequest(HttpMethod.Post, path) {
                setRequestBody(this, createJsonString("AbpT91fok"))
            }.apply {
                doResponseAssertions(response, PasswordCheckResult(false, PasswordCheckFailMessages.missingSpecialChar))
            }
        }

    @Test
    fun `Invalid special character`() =
        dispatchRequest {
            handleRequest(HttpMethod.Post, path) {
                setRequestBody(this, createJsonString("aBtp9=fok"))
            }.apply {
                doResponseAssertions(response, PasswordCheckResult(false, PasswordCheckFailMessages.invalidChar))
            }
        }

    @Test
    fun `Contains whitespace`() =
        dispatchRequest {
            handleRequest(HttpMethod.Post, path) {
                setRequestBody(this, createJsonString("AbTp9 fok"))
            }.apply {
                doResponseAssertions(response, PasswordCheckResult(false, PasswordCheckFailMessages.invalidChar))
            }
        }

    @Test
    fun `Repeated char`() =
        dispatchRequest {
            handleRequest(HttpMethod.Post, path) {
                setRequestBody(this, createJsonString("AbTp9!foA"))
            }.apply {
                doResponseAssertions(response, PasswordCheckResult(false, PasswordCheckFailMessages.repeatedChar))
            }
        }

    @Test
    fun `Repeated case-sensitive character`() =
        dispatchRequest {
            handleRequest(HttpMethod.Post, path) {
                setRequestBody(this, createJsonString("AbTp9!foa"))
            }.apply {
                doResponseAssertions(response, PasswordCheckResult(true))
            }
        }

    @Test
    fun `BR special chars`() =
        dispatchRequest {
            handleRequest(HttpMethod.Post, path) {
                setRequestBody(this, createJsonString("AbTp9!foaçãé"))
            }.apply {
                doResponseAssertions(response, PasswordCheckResult(false, PasswordCheckFailMessages.invalidChar))
            }
        }

    @Test
    fun `Correct password`() =
        dispatchRequest {
            handleRequest(HttpMethod.Post, path) {
                setRequestBody(this, createJsonString("AbTp9!fok"))
            }.apply {
                doResponseAssertions(response, PasswordCheckResult(true))
            }
        }

    private fun dispatchRequest(test: TestApplicationEngine.() -> Unit) =
        withTestApplication({ main() }) {
            test.invoke(this)
        }

    private fun setRequestBody(request: TestApplicationRequest, content: String) {
        request.setBody(content)
        request.addHeader("content-type", "application/json")
    }

    private fun createJsonString(password: String?): String =
        """{
            |"password": "$password"
        |}""".trimMargin()

    private fun doResponseAssertions(response: TestApplicationResponse, result: PasswordCheckResult) =
        objectMapper.readValue<PasswordCheckResult>(response.content!!).let {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals(it, result)
        }
}