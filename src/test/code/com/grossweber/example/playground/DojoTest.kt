package com.grossweber.example.playground

import assertk.assert
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import kotlin.test.Test

class DojoTest {
  @Test
  fun `Dojo can be instantiated`() {
    val dojo = Dojo()
    assert(dojo).isNotNull()
  }

  @Test
  fun `Dojo has an entry point`() {
    val interpret = Dojo.Companion::class.java.getDeclaredMethod("interpret", String::class.java)
    assert(interpret.returnType).isEqualTo(String::class.java)
  }
}
