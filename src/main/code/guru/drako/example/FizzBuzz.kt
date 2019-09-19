package guru.drako.example

class FizzBuzz {
  /**
   * Check whether a number can be evenly divided by another one.
   */
  private infix fun Int.isDivisibleBy(other: Int) = this % other == 0

  /**
   * @return "Fizz", if [n] is divisible by 3, null otherwise.
   */
  private fun fizz(n: Int) = n.takeIf { it isDivisibleBy 3 }?.let { "Fizz" }

  /**
   * @return "Buzz", if [n] is divisible by 5, null otherwise.
   */
  private fun buzz(n: Int): String? {
    return if (n isDivisibleBy 5) {
      "Buzz"
    } else {
      null
    }
  }

  /**
   * @return
   *   "FizzBuzz", for [n] divisible by 15
   *   "Fizz", for [n] divisible by 3
   *   "Buzz", for [n] divisible by 5
   *   [n.toString()] otherwise
   */
  fun fizzBuzz(n: Int) = sequenceOf(this::fizz, this::buzz)
      .map { it(n) }
      .filterNotNull()
      .joinToString(separator = "")
      .ifBlank { "$n" }
}
