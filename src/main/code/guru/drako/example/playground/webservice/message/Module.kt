package guru.drako.example.playground.webservice.message

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.auth.authenticate
import io.ktor.features.DataConversion
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveText
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.delete
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.routing.route
import io.ktor.routing.routing
import kotlinx.serialization.json.JSON
import kotlinx.serialization.list
import kotlinx.serialization.parse

fun Application.messageModule() {
  DatabaseFactory.init()

  val messageService = MessageService()

  routing {
    authenticate("message") {
      route("/messages") {
        get {
          val limit = call.request.queryParameters["limit"]?.toInt()
          val offset = call.request.queryParameters["offset"]?.toInt()

          call.respondMessages(messageService.getAllMessages(limit, offset))
        }

        post {
          val message = call.receiveNewMessage()
          call.respondMessage(
              messageService.createMessage(message),
              HttpStatusCode.Created
          )
        }

        put("/{id}") {
          val id = call.parameters["id"]!!.toInt()

          val message = call.receiveUpdateMessage()
          messageService.updateMessage(id, message)
          call.respond(HttpStatusCode.Accepted)
        }

        delete("/{id}") {
          val id = call.parameters["id"]!!.toInt()

          messageService.dropMessageById(id)
          call.respond(HttpStatusCode.Accepted)
        }

        get("/{id}") {
          val id = call.parameters["id"]!!.toInt()
          val message = messageService.getMessageById(id)

          if (message == null) {
            call.respond(HttpStatusCode.NotFound)
          } else {
            call.respondMessage(message)
          }
        }

        get("/by/author/{author?}") {
          call.respondMessages(
              messageService.getMessagesByAuthor(call.parameters["author"])
          )
        }
      }
    }
  }
}
