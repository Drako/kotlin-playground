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

  @Test
  fun `should increment current value`() {
    val dojo = Dojo()
    assert(dojo.memory[dojo.currentField]).isEqualTo(0)
    dojo.incrementCurrentField()
    assert(dojo.memory[dojo.currentField]).isEqualTo(1)
    dojo.incrementCurrentField()
    assert(dojo.memory[dojo.currentField]).isEqualTo(2)
  }

  @Test
  fun `should decrement current value`() {
    val dojo = Dojo()
    assert(dojo.memory[dojo.currentField]).isEqualTo(0)
    dojo.decrementCurrentField()
    assert(dojo.memory[dojo.currentField]).isEqualTo(-1)
  }

  @Test
  fun `should increment current position`(){
    val dojo = Dojo()
    assert(dojo.currentField).isEqualTo(0)
    dojo.incrementCurrentPosition()
    assert(dojo.currentField).isEqualTo(1)
    dojo.incrementCurrentPosition()
    assert(dojo.currentField).isEqualTo(2)
  }

  @Test
 fun `should wrap around memory size`() {
    val dojo = Dojo(currentField = Dojo.MEMORY_SIZE-1)
    dojo.incrementCurrentPosition()
    assert(dojo.currentField).isEqualTo(0)
  }

  @Test
  fun `should wrap to end field`() {
    val dojo = Dojo()
    dojo.decrementCurrentPosition()
    assert(dojo.currentField).isEqualTo(Dojo.MEMORY_SIZE-1)
  }

  @Test
  fun `should decrement current position`(){
    val dojo = Dojo(currentField = 5)
    dojo.decrementCurrentPosition()
    assert(dojo.currentField).isEqualTo(4)
  }

  @Test
  fun `should append current field to output string`(){
    val dojo = Dojo(memory = IntArray(1) { 65 })

    dojo.appendToOutut()

    assert (dojo.output).isEqualTo("A")
  }
}
