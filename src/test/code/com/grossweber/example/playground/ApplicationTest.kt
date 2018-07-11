package com.grossweber.example.playground

import assertk.assert
import assertk.assertions.isTrue
import kotlin.test.Test

class ApplicationTest {
  @Test
  fun `It should have an entry point`() {
    val main = Application::class.java.getDeclaredMethod("main", Array<String>::class.java)
    assert(main.isAnnotationPresent(JvmStatic::class.java)).isTrue()
  }
}
