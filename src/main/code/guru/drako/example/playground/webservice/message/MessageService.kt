package guru.drako.example.playground.webservice.message

import guru.drako.example.playground.webservice.message.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

class MessageService {
  companion object {
    const val DEFAULT_LIMIT = 15
    const val DEFAULT_OFFSET = 0
  }

  suspend fun getAllMessages(limit: Int? = null, offset: Int? = null): List<Message> = dbQuery {
    Messages
        .selectAll()
        .limit(n = limit ?: DEFAULT_LIMIT, offset = offset ?: DEFAULT_OFFSET)
        .map(this@MessageService::toMessage)
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

  suspend fun getMessageById(id: Int): Message? = dbQuery {
    Messages
        .select { Messages.id eq id }
        .limit(1)
        .singleOrNull()
        ?.let(this@MessageService::toMessage)
  }

  suspend fun dropMessageById(id: Int) = dbQuery {
    Messages.deleteWhere { Messages.id eq id }
  }

  suspend fun createMessage(message: NewMessage) = dbQuery {
    val id = Messages.insert {
      it[author] = message.author
      it[content] = message.content
    }.generatedKey

    Message(
        id = id!! as Int,
        author = message.author,
        content = message.content
    )
  }

  suspend fun updateMessage(id: Int, message: UpdateMessage) = dbQuery {
    Messages.update({ Messages.id eq id }) {
      if (message.author != null) it[author] = message.author
      if (message.content != null) it[content] = message.content
    }
  }

  private fun toMessage(row: ResultRow) = Message(
      id = row[Messages.id],
      author = row[Messages.author],
      content = row[Messages.content]
  )
}
