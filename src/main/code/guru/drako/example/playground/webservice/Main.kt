package guru.drako.example.playground.webservice

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders

fun Application.mainModule() {
  install(DefaultHeaders)
  install(CallLogging)
}
