package guru.drako.example.playground

const val FIZZ = "Fizz"
const val BUZZ = "Buzz"
const val FIZZ_BUZZ = FIZZ + BUZZ

infix fun Int.isDivisableBy(divisor: Int): Boolean = this % divisor == 0

@Open
class FizzBuzz {

  fun fizzbuzz(): String {
    return (1..100)
        .joinToString(separator = "\n") { getLineOutput(it) }
  }

  fun getLineOutput(currentLine: Int): String {
    return when {
      currentLine isDivisableBy 15 -> FIZZ_BUZZ
      currentLine isDivisableBy 3 -> FIZZ
      currentLine isDivisableBy 5 -> BUZZ
      else -> "$currentLine"
    }
  }
}
