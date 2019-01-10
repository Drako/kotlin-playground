package guru.drako.example.playground.webservice.message

import guru.drako.example.playground.webservice.message.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class MessageService {
  suspend fun getAllMessages(): List<Message> = dbQuery {
    Messages.selectAll().map(this@MessageService::toMessage)
  }

  suspend fun getMessagesByAuthor(author: String? = null): List<Message> {
    return if (author == null) {
      getAllMessages()
    } else {
      dbQuery {
        Messages.select { Messages.author eq author }.map(this@MessageService::toMessage)
      }
    }
  }

  private fun toMessage(row: ResultRow) = Message(
      id = row[Messages.id],
      author = row[Messages.author],
      content = row[Messages.content]
  )
}
