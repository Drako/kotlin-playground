package guru.drako.example.playground

infix fun Int.isDivisibleBy(divisor: Int): Boolean = this % divisor == 0

@Open
class FizzBuzz {
  companion object {
    const val FIZZ = "Fizz"
    const val BUZZ = "Buzz"
    const val FIZZ_BUZZ = FIZZ + BUZZ
  }

  fun fizzbuzz(): String {
    return (1..100)
        .joinToString(separator = "\n") { getLineOutput(it) }
  }

  fun getLineOutput(currentLine: Int): String {
    return when {
      currentLine isDivisibleBy 15 -> FIZZ_BUZZ
      currentLine isDivisibleBy 3 -> FIZZ
      currentLine isDivisibleBy 5 -> BUZZ
      else -> "$currentLine"
    }
  }
}
