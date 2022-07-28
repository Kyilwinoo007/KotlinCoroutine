package CoroutineCancellation

import Thread_VS_Coroutines.mySuspendedFun
import kotlinx.coroutines.*
import kotlin.concurrent.thread

fun  main() = runBlocking{
    println("Main Threads starts: ${Thread.currentThread().name}")
//    val job: Job = launch {//operate within thread T1
//        for (i in 1..500){
//            print("$i.")
//            Thread.sleep(50)
//        }
//    }
//    delay(300)
//    job.cancelAndJoin()

    val job: Job = launch {//operate within thread T1
        for (i in 1..500){
            print("$i.")
            delay(50)
        }
    }
    delay(300)
    job.cancelAndJoin()

    println()
    println()

    val job1: Job = launch {//operate within thread T1
        try {
            for (i in 1..500){
                print("$i.")
                yield()
            }
        }catch (ex:CancellationException){
            println("\n Exception caught safely ${ex.message}")
        }finally {

            withContext(NonCancellable){
                delay(200)
                println("\n Close resource in finally")
            }
        }

    }
    delay(20)
    job1.cancel(CancellationException("my message"))
    job1.join()

    println()
    println()

    val job2: Job = launch(Dispatchers.Default) {//operate within thread T1
        for (i in 1..500){
            if (!isActive){
                break
            }
            print("$i.")
            Thread.sleep(1)
        }
    }
    delay(20)
    job2.cancelAndJoin()

//    println("\n WithTimeOut")
//    println()
//    withTimeout(2000){
//        try {
//            for (i in 1..500){
//                print("$i.")
//                delay(500)
//            }
//        }catch (ex:TimeoutCancellationException){
//
//        }finally {
//
//        }
//
//    }

    println()
    println("\n WithTimeOutOrNull")
    println()

   var result:String? =  withTimeoutOrNull(2000){
        for (i in 1..500){
            print("$i.")
            delay(500)
        }
       "I am done!"

    }
    println("\n result $result")

    println(" \n Main Threads end: ${Thread.currentThread().name}")

}