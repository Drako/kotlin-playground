package com.grossweber.example.playground

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DojoTest {
  @Test
  fun `Dojo can be instantiated`() {
    val dojo = Dojo()
    assertNotNull(dojo)
  }

  @Test
  fun `Dojo has an entry point`() {
    val interpret = Dojo.Companion::class.java.getDeclaredMethod("interpret", String::class.java)
    assertEquals(
        expected = String::class.java,
        actual = interpret.returnType
    )
  }
}
