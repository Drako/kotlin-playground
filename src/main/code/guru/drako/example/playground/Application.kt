package guru.drako.example.playground

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@UseExperimental(ExperimentalCoroutinesApi::class)
fun CoroutineScope.fibonacci() = produce(capacity = 1000) {
  var a = 0
  var b = 1
  while (true) {
    send(b)
    a = b.also { b = a + b }
  }
}

@UseExperimental(ExperimentalCoroutinesApi::class)
fun <T> CoroutineScope.channelOf(vararg values: T) = produce {
  values.forEach {
    send(it)
  }
}

suspend fun main() {
  val answer = GlobalScope.async {
    println("Started")
    delay(2000)
    println("Finished")
    42
  }

  // infinite channel needs canceling
  val reader = GlobalScope.fibonacci()
  for (n in 1..10) {
    println(reader.receive())
  }
  reader.cancel()

  // finite channel
  for (n in GlobalScope.channelOf(1, 2, 3, 4, 5, 6)) {
    println(n)
  }

  println(answer.await())
}
