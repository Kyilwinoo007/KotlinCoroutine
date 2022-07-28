package CoroutinesBuilder

import Thread_VS_Coroutines.mySuspendedFun
import kotlinx.coroutines.*
import kotlin.concurrent.thread

fun  main() = runBlocking{
    println("Main Threads starts: ${Thread.currentThread().name}")


    //Coroutine
    val job: Job = launch {//operate within thread T1
        println("Fake work starts: ${Thread.currentThread().name}") //Thread T1
        delay(100)             //Coroutines is suspended but thread : T1 is free(not blocked)
        println("Fake work end: ${Thread.currentThread().name}")

    }

    job.join()
//    launch {//operate within thread T1
//        println("Fake work1 starts: ${Thread.currentThread().name}") //Thread T1
//        delay(100)             //Coroutines is suspended but thread : T1 is free(not blocked)
//        println("Fake work1 end: ${Thread.currentThread().name}")
//
//    }
//    Thread.sleep(100)
//    runBlocking {
//        delay(200);
//    }

    val deferred: Deferred<Int> = async {//operate within thread T1
        println("Fake work starts: ${Thread.currentThread().name}") //Thread T1
        delay(100)             //Coroutines is suspended but thread : T1 is free(not blocked)
        println("Fake work end: ${Thread.currentThread().name}")
        20
    }

//    deferred.join()
    val number:Int = deferred.await()


    println("Main Threads end: ${Thread.currentThread().name}")
}