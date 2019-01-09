package guru.drako.example.playground.webservice.hello

import io.ktor.http.HttpMethod
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlin.test.Test
import assertk.assert
import assertk.assertions.isEqualTo
import io.ktor.http.ContentType
import io.ktor.server.testing.contentType
import org.intellij.lang.annotations.Language

class HelloTest {
  @Test
  fun `Test start page`() {
    @Language("HTML") val expectedStartPage = """<!DOCTYPE html>
      |<html>
      |  <head>
      |    <title>Hello world!</title>
      |  </head>
      |  <body>
      |    <p>Hello world!</p>
      |  </body>
      |</html>
      |""".trimMargin()

    withTestApplication({
      helloModule()
    }) {
      handleRequest(HttpMethod.Get, "/").apply {
        assert(response.contentType()).isEqualTo(ContentType.Text.Html.withParameter("charset", "UTF-8"))
        assert(response.content!!).isEqualTo(expectedStartPage)
      }
    }
  }
}
