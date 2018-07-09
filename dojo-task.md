# Code Dojo

## Introduction

The focus of the event is to train TDD (Test Driven Development)
and experiment with Kotlin features.

For testing we want to use [Kotlin-Test](https://kotlinlang.org/api/latest/kotlin.test/kotlin.test/index.html) as far as possible.

The general rules apply:

* Test first
* Baby steps

So don't just implement the entire interpreter in the interpret function
and then celebrate, that the predefined test works.

We also want to apply the TDD cycle:

1. Write failing test
2. Implement tested behaviour
3. Refactor, if it makes sense

## Task

The task is to implement a limited Brainfuck interpreter.

The interpreter state consists of a memory (a container of arbitrary size containing only integers)
and a pointer/index into that container.

The following instructions need to be implemented:

Character | Meaning | Pseudocode
--- | --- | ---
\+ | Increment the number at the current position | `++mem[ptr]`
\- | Decrement the number at the current position | `--mem[ptr]`
\> | Increment the pointer/index | `++ptr`
< | Decrement the pointer/index | `--ptr`
\[ | Begin a loop if the current number is not zero | `while (mem[ptr]) {`
\] | End a loop | `}`
. | Treat the current number as a character and print it | `putc(mem[ptr])`

Every other character in the input string is to be ignored.
Also keep in mind that loops can be nested.

For better testability `.` will not actually print the character, but append it to some output string.
The interpreter's entry point shall be

```kotlin
class Dojo {
  companion object {
    fun interpret(sourceCode: String): String {
      // ...
      return outputString
    }
  }
}
```

## Bonus Tasks

* Detect unbalanced brackets
  * "Unexpected closing bracket"
  * "Unexpected end of file"
* Keep track of position in source for error messages
