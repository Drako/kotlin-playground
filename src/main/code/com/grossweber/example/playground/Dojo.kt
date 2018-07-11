package com.grossweber.example.playground

class Dojo(val memory: IntArray = IntArray(MEMORY_SIZE),
  var currentField: Int = 0,
  var output: String = "") {


  companion object {
    const val MEMORY_SIZE = 65536

    fun interpret(sourceCode: String): String {
      TODO()
    }
  }

  fun incrementCurrentField() {
    ++memory[currentField]
  }

  fun decrementCurrentField() {
    --memory[currentField]
    if(currentField < 0) {
      currentField = MEMORY_SIZE-1
    }
  }

  fun incrementCurrentPosition() {
    ++currentField
    if (currentField == MEMORY_SIZE){
      currentField = 0
    }
  }

  fun decrementCurrentPosition() {
    --currentField
    if (currentField == -1){
      currentField = MEMORY_SIZE-1
    }
  }

  fun appendToOutut() {
    output = output + memory[currentField].toChar()
  }

}
