package guru.drako.example.playground

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.TestInstance
import kotlin.reflect.full.declaredMemberFunctions
import kotlin.test.Test
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RomanNumeralsConverterTest {
  private val converter = RomanNumeralsConverter()

  @Nested
  @DisplayName("Conversion: Roman -> Arabic")
  inner class Roman2Arabic {
    @Test
    fun `The conversion function exists`() {
      assertTrue(RomanNumeralsConverter::class.declaredMemberFunctions.any { it.name == "roman2arabic" })
    }
  }

  @Nested
  @DisplayName("Conversion: Arabic -> Roman")
  inner class Arabic2Roman {
    @Test
    fun `The conversion function exists`() {
      assertTrue(RomanNumeralsConverter::class.declaredMemberFunctions.any { it.name == "arabic2roman" })
    }
  }
}
