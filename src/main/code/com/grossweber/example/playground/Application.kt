package com.grossweber.example.playground

import kotlinx.coroutines.experimental.runBlocking

class Application(val args: Array<String>) {
  companion object {
    @JvmStatic
    fun main(args: Array<String>) {
      val app = Application(args)
      app.run()
    }
  }

  fun run() = runBlocking {
    // feel free to call suspending functions here
  }
}
