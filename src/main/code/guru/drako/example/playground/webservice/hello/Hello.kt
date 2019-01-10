package guru.drako.example.playground.webservice.hello

import freemarker.cache.ClassTemplateLoader
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.freemarker.FreeMarker
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.html.respondHtml
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.p
import kotlinx.html.title

fun Application.helloModule() {
  install(FreeMarker) {
    templateLoader = ClassTemplateLoader(Application::class.java.classLoader, "templates")
  }

  routing {
    get("/") {
      call.respondHtml {
        head {
          title("Hello world!")
        }
        body {
          p {
            +"Hello world!"
          }
        }
      }
    }

    get("/greet/{who?}") {
      val name = call.parameters["who"] ?: "world"
      call.respond(FreeMarkerContent("hello.ftl", mapOf("name" to name)))
    }
  }
}
