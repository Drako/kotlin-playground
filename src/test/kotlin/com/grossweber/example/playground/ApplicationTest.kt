package com.grossweber.example.playground

import kotlin.test.Test
import kotlin.test.assertTrue

class ApplicationTest {
  @Test
  fun `It should have an entry point`() {
    val main = Application::class.java.getDeclaredMethod("main", Array<String>::class.java)
    assertTrue(main.isAnnotationPresent(JvmStatic::class.java))
  }
}
