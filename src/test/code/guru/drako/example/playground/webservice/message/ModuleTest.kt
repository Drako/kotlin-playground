package guru.drako.example.playground.webservice.message

import guru.drako.example.playground.webservice.mainModule
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.TestApplicationRequest
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.setBody
import io.ktor.server.testing.withTestApplication
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ModuleTest {

  private fun TestApplicationRequest.setBodyJson(json: String) {
    setBody(json)
    addHeader(HttpHeaders.ContentType, "${ContentType.Application.Json}")
  }

  private fun TestApplicationRequest.addAuthHeader() {
    addHeader(HttpHeaders.Authorization, "Basic Zm9vOmJhcg==")
  }

  @Test
  fun `Query demo data`() {
    withTestApplication({
      mainModule()
      DatabaseFactory.reset()
    }) {
      handleRequest(HttpMethod.Get, "/messages") {
        addAuthHeader()
      }.apply {
        val messages = Message.fromJsonArray(response.content!!)
        assertEquals(expected = 3, actual = messages.size)
        assertEquals(
            expected = setOf("Felix", "William", "Vlad"),
            actual = messages.map { it.author }.toSet()
        )
      }
    }
  }

  @Test
  fun `Query data paged`() {
    withTestApplication({
      mainModule()
      DatabaseFactory.reset()
    }) {
      // create 15 extra messages so we have 18
      repeat(15) {
        handleRequest(HttpMethod.Post, "/messages") {
          setBodyJson(NewMessage(author = "Tester", content = "test$it").toJson())
          addAuthHeader()
        }
      }

      handleRequest(HttpMethod.Get, "/messages") {
        addAuthHeader()
      }.apply {
        val messages = Message.fromJsonArray(response.content!!)
        assertEquals(expected = 15, actual = messages.size)
      }

      handleRequest(HttpMethod.Get, "/messages?limit=10") {
        addAuthHeader()
      }.apply {
        val messages = Message.fromJsonArray(response.content!!)
        assertEquals(expected = 10, actual = messages.size)
      }

      handleRequest(HttpMethod.Get, "/messages?limit=10&offset=10") {
        addAuthHeader()
      }.apply {
        val messages = Message.fromJsonArray(response.content!!)
        assertEquals(expected = 8, actual = messages.size)
      }
    }
  }

  @Test
  fun `Create and query`() {
    withTestApplication({
      mainModule()
      DatabaseFactory.reset()
    }) {
      val created = handleRequest(HttpMethod.Post, "/messages") {
        setBodyJson(NewMessage(author = "Felix", content = "test").toJson())
        addAuthHeader()
      }.run {
        Message.fromJson(response.content!!)
      }

      handleRequest(HttpMethod.Get, "/messages/${created.id}") {
        addAuthHeader()
      }.apply {
        assertEquals(expected = HttpStatusCode.OK, actual = response.status())
        val received = Message.fromJson(response.content!!)
        assertEquals(expected = created, actual = received)
      }
    }
  }

  @Test
  fun `Update and query`() {
    withTestApplication({
      mainModule()
      DatabaseFactory.reset()
    }) {
      val created = handleRequest(HttpMethod.Post, "/messages") {
        setBodyJson(NewMessage(author = "Felix", content = "test").toJson())
        addAuthHeader()
      }.run {
        Message.fromJson(response.content!!)
      }

      handleRequest(HttpMethod.Put, "/messages/${created.id}") {
        setBodyJson(UpdateMessage(author = "Vlad").toJson())
        addAuthHeader()
      }

      handleRequest(HttpMethod.Get, "/messages/${created.id}") {
        addAuthHeader()
      }.apply {
        assertEquals(expected = HttpStatusCode.OK, actual = response.status())
        val received = Message.fromJson(response.content!!)
        assertEquals(expected = created.copy(author = "Vlad"), actual = received)
      }
    }
  }

  @Test
  fun `Delete demo content`() {
    withTestApplication({
      mainModule()
      DatabaseFactory.reset()
    }) {
      val messages: List<Message> = handleRequest(HttpMethod.Get, "/messages") {
        addAuthHeader()
      }.run {
        Message.fromJsonArray(response.content!!)
      }

      handleRequest(HttpMethod.Delete, "/messages/${messages[0].id}") {
        addAuthHeader()
      }

      handleRequest(HttpMethod.Get, "/messages/${messages[0].id}") {
        addAuthHeader()
      }.apply {
        assertEquals(expected = HttpStatusCode.NotFound, actual = response.status())
      }
    }
  }
}
