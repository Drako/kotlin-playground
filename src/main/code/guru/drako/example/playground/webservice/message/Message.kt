package guru.drako.example.playground.webservice.message

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

object Messages : Table() {
  val id = integer(name = "id").primaryKey().autoIncrement()
  val author = varchar(name = "author", length = 32).index()
  val content = varchar(name = "content", length = 280)
}

@Serializable
data class Message(
  @Optional
  val id: Int? = null,
  val author: String,
  val content: String
)
