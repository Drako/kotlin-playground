package guru.drako.example

import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.streams.asStream
import kotlin.test.Ignore
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ScrabbleTest {
  companion object {
    val LETTER_VALUES = mapOf(
        'A' to 1, 'E' to 1, 'I' to 1, 'O' to 1, 'U' to 1, 'L' to 1, 'N' to 1, 'R' to 1, 'S' to 1, 'T' to 1,
        'D' to 2, 'G' to 2,
        'B' to 3, 'C' to 3, 'M' to 3, 'P' to 3,
        'F' to 4, 'H' to 4, 'V' to 4, 'W' to 4, 'Y' to 4,
        'K' to 5,
        'J' to 8, 'X' to 8,
        'Q' to 10, 'Z' to 10
    )

    @Suppress("unused")
    @JvmStatic
    fun testLetters() = LETTER_VALUES
        .asSequence()
        .asStream()
        .map { (letter, value) -> Arguments.of(letter, value) }

    @Suppress("unused")
    @JvmStatic
    fun testWords() = Stream.of(
        Arguments.of("APE", 5),
        Arguments.of("MONKEY", 15),
        Arguments.of("MATRIX", 15)
    )
  }

  @ParameterizedTest(name = "{index} ==> {0} should have the value {1}")
  @MethodSource("testLetters")
  @Ignore
  fun `letters should have their correct scores`(letter: Char, expectedScore: Int) {
    assertEquals(expectedScore, Scrabble.score("$letter"))
  }

  @ParameterizedTest(name = "{index} ==> {0} should have a score of {1}")
  @MethodSource("testWords")
  @Ignore
  fun `word score calculation should work`(word: String, expectedScore: Int) {
    assertEquals(expectedScore, Scrabble.score(word))
  }
}
