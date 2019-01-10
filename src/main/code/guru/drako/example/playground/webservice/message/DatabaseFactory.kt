package guru.drako.example.playground.webservice.message

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
  fun init() {
    Database.connect(url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")

    transaction {
      create(Messages)

      Messages.insert {
        it[author] = "Felix"
        it[content] = "Hello world!"
      }

      Messages.insert {
        it[author] = "William"
        it[content] = "/bb|[^b]{2}/"
      }

      Messages.insert {
        it[author] = "Vlad"
        it[content] = "как тебя зовут"
      }
    }
  }

  suspend fun <T> dbQuery(block: () -> T): T =
      withContext(Dispatchers.IO) {
        transaction { block() }
      }
}
