package guru.drako.example.playground.webservice.message

import kotlinx.serialization.Serializable

@Serializable
data class Message(
  val author: String,
  val content: String
)
