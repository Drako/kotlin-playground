package guru.drako.example.playground

import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.valueParameters
import kotlin.reflect.jvm.javaMethod
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ApplicationTest {
  @Test
  fun `It should have an entry point`() {
    val main = Application.Companion::class.declaredFunctions.first { it.name == "main" }
    assertNotNull(main)

    with(main) {
      assertEquals(expected = KVisibility.PUBLIC, actual = visibility)
      assertEquals(expected = 1, actual = valueParameters.size)
      with(javaMethod!!) {
        assertEquals(expected = Array<String>::class.java, actual = parameterTypes[0])
        assertEquals(expected = Void.TYPE, actual = returnType)
      }
    }
  }
}
