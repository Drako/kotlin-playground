package guru.drako.example.playground.webservice.hello

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.html.respondHtml
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.p
import kotlinx.html.title
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.json.JSON
import kotlinx.serialization.stringify

@UseExperimental(ImplicitReflectionSerializer::class)
fun Application.helloModule() {
  install(DefaultHeaders)
  install(CallLogging)
  routing {
    get("/") {
      call.respondHtml {
        head {
          title("Hello world!")
        }
        body {
          p {
            +"Hello world!"
          }
        }
      }
    }

    get("/messages") {
      call.respondText(JSON.stringify(Messages.messages), ContentType.Application.Json)
    }

    get("/messages/by/author/{author?}") {
      call.respondText(
          JSON.stringify(call.parameters["author"]?.let { author ->
            Messages.messages.filter { it.author == author }
          } ?: Messages.messages),
          ContentType.Application.Json
      )
    }
  }
}
