package com.grossweber.example.playground

import assertk.assert
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import kotlin.reflect.KVisibility
import kotlin.reflect.full.staticFunctions
import kotlin.reflect.jvm.javaMethod
import kotlin.test.Test

class ApplicationTest {
  @Test
  fun `It should have an entry point`() {
    val main = Application::class.staticFunctions.first { it.name == "main" }
    assert(main).isNotNull()

    with(main) {
      assert(visibility).isEqualTo(KVisibility.PUBLIC)
      assert(parameters.size).isEqualTo(1)
      with (javaMethod!!) {
        assert(parameterTypes[0])
            .isEqualTo(Array<String?>::class.java)
        assert(returnType)
            .isEqualTo(Void.TYPE)
      }
    }
  }
}
