package guru.drako.example.playground.webservice.message

import io.ktor.application.ApplicationCall
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.request.receiveText
import io.ktor.response.respondText
import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON
import kotlinx.serialization.list
import org.jetbrains.exposed.sql.Table

object Messages : Table() {
  val id = integer(name = "id").primaryKey().autoIncrement()
  val author = varchar(name = "author", length = 32).index()
  val content = varchar(name = "content", length = 280)
}

@Serializable
data class NewMessage(
  val author: String,
  val content: String
) {
  companion object {
    fun fromJson(json: String): NewMessage = JSON.parse(NewMessage.serializer(), json)
  }

  fun toJson() = JSON.stringify(NewMessage.serializer(), this)
}

@Serializable
data class UpdateMessage(
  @Optional val author: String? = null,
  @Optional val content: String? = null
) {
  companion object {
    fun fromJson(json: String): UpdateMessage = JSON.parse(UpdateMessage.serializer(), json)
  }

  fun toJson() = JSON.stringify(UpdateMessage.serializer(), this)
}

@Serializable
data class Message(
  val id: Int,
  val author: String,
  val content: String
) {
  companion object {
    fun fromJson(json: String): Message = JSON.parse(Message.serializer(), json)

    fun fromJsonArray(json: String): List<Message> = JSON.parse(Message.serializer().list, json)
  }

  fun toJson() = JSON.stringify(Message.serializer(), this)
}

fun List<Message>.toJson() = JSON.stringify(
    Message.serializer().list,
    this
)

suspend fun ApplicationCall.receiveNewMessage(): NewMessage {
  return NewMessage.fromJson(receiveText())
}

suspend fun ApplicationCall.receiveUpdateMessage(): UpdateMessage {
  return UpdateMessage.fromJson(receiveText())
}

suspend fun ApplicationCall.respondMessage(
  message: Message,
  statusCode: HttpStatusCode = HttpStatusCode.OK
) {
  respondText(message.toJson(), ContentType.Application.Json, statusCode)
}

suspend fun ApplicationCall.respondMessages(
  messages: List<Message>,
  statusCode: HttpStatusCode = HttpStatusCode.OK
) {
  respondText(messages.toJson(), ContentType.Application.Json, statusCode)
}
