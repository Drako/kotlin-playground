package guru.drako.example.playground.webservice

import guru.drako.example.playground.webservice.hello.helloModule
import guru.drako.example.playground.webservice.message.messageModule
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.Credential
import io.ktor.auth.UserIdPrincipal
import io.ktor.auth.UserPasswordCredential
import io.ktor.auth.basic
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders

fun Application.mainModule() {
  val TEST_CREDENTIALS = UserPasswordCredential(name = "foo", password = "bar")

  install(DefaultHeaders)
  install(CallLogging)
  install(Authentication) {
    basic(name = "message") {
      realm = "Message Service"
      validate { credentials ->
        if (credentials == TEST_CREDENTIALS) {
          UserIdPrincipal(name = "foo")
        } else null
      }
    }

  }

  helloModule()
  messageModule()
}
