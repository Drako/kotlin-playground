package guru.drako.example.playground.webservice.message

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.serialization.json.JSON
import kotlinx.serialization.list

fun Application.messageModule() {
  routing {
    get("/messages") {
      call.respondText(
          JSON.stringify(Message.serializer().list, Messages.messages),
          ContentType.Application.Json
      )
    }

    get("/messages/by/author/{author?}") {
      call.respondText(
          JSON.stringify(
              Message.serializer().list,
              call.parameters["author"]?.let { author ->
                Messages.messages.filter { it.author == author }
              } ?: Messages.messages
          ),
          ContentType.Application.Json
      )
    }
  }
}
