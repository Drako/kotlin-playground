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
  DatabaseFactory.init()

  val messageService = MessageService()

  routing {
    get("/messages") {
      call.respondText(
          JSON.stringify(Message.serializer().list, messageService.getAllMessages()),
          ContentType.Application.Json
      )
    }

    get("/messages/by/author/{author?}") {
      call.respondText(
          JSON.stringify(
              Message.serializer().list,
              messageService.getMessagesByAuthor(call.parameters["author"])
          ),
          ContentType.Application.Json
      )
    }
  }
}
