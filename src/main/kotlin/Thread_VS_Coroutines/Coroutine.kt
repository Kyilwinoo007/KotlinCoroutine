package Thread_VS_Coroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

fun main(){
    println("Main Threads starts: ${Thread.currentThread().name}")


    //Thread
    thread {   //create a thread
        println("Fake work starts: ${Thread.currentThread().name}")
        Thread.sleep(100)
        println("Fake work end: ${Thread.currentThread().name}")
    }

    //Coroutine
    GlobalScope.launch {//operate within thread T1
        println("Fake work starts: ${Thread.currentThread().name}") //Thread T1
        mySuspendedFun(100)             //Coroutines is suspended but thread : T1 is free(not blocked)
        println("Fake work end: ${Thread.currentThread().name}")

    }
//    Thread.sleep(100)
    runBlocking {
        mySuspendedFun(200);
    }
    println("Main Threads end: ${Thread.currentThread().name}")

}
suspend fun mySuspendedFun(time:Long){
    delay(time)
}