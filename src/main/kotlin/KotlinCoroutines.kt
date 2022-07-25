import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    println("Main Threads starts: ${Thread.currentThread().name}")

    try {
        withTimeout(2000){
            for (i in 0..500){
                print("$i.")
                delay(500)
            }
        }
    }catch (ex:TimeoutCancellationException){
        //..code
    }finally {
        //..code
    }

  var value : String?=   withTimeoutOrNull(2000){
        for (i in 0..500){
            print("\n $i.")
            delay(500)
        }
      "I\'m done"
    }
    print("\n message $value")


    //sequential
    val time = measureTimeMillis {
        var msgOne  = getMessageOne()
        var msgTwo = getMessageTwo()
        println("\n Entire Message is ${msgOne + msgTwo}")
    }
    println(" \n Complete in $time ms")


    //parallel or concurrency
    val time1 = measureTimeMillis {
        var msgOne : Deferred<String>  = async {
            getMessageOne()
        }
        var msgTwo :Deferred<String> = async {
            getMessageTwo()
        }
        println("\n Entire Message is ${msgOne.await() + msgTwo.await()}")
    }
    println(" \n Complete in $time1 ms")


    //lazy Execution
    var msgOne : Deferred<String>  = async(start = CoroutineStart.LAZY) {
        getMessageOne()
    }
    var msgTwo :Deferred<String> = async(start = CoroutineStart.LAZY) {
        getMessageTwo()
    }
    println("\n Entire Message is ${msgOne.await() + msgTwo.await()}")



//    var job: Job = launch(Dispatchers.Default) {
//        try {
//            for (i in 0..500) {
////           if (!isActive){
////               return@launch // break
////           }
//                print("$i.")
////           Thread.sleep(1)
//                delay(5)
//                //yield()
//            }
//        }catch (ex:CancellationException){
//            print("\nException caught safely")
//        }finally {
//            withContext(NonCancellable){
//                delay(200)
//                print("\nClose resources in finally")
//            }
//
//        }
//    }
//    delay(10)
//
//    job.cancelAndJoin()


//    var jobDeferred: Deferred<String> =  async{
//
//        println("Fake Threads starts: ${Thread.currentThread().name}")
//        delay(1000)
//        println("Fake Threads end: ${Thread.currentThread().name}")
//        "This is fake threads"
//
//    }
//    var txt = jobDeferred.await()


    launch {
        println("C1 : ${Thread.currentThread().name}")
        delay(100)
        println("C1 after delay : ${Thread.currentThread().name}")

    }

    launch (Dispatchers.Default){
        println("C2 : ${Thread.currentThread().name}")
        delay(100)
        println("C2 after delay : ${Thread.currentThread().name}")

    }

    launch (Dispatchers.Unconfined){
        println("C3 : ${Thread.currentThread().name}")
        delay(100)
        println("C3 after delay : ${Thread.currentThread().name}")

        launch (coroutineContext){
            println("C5 : ${Thread.currentThread().name}")
            delay(100)
            println("C5 after delay : ${Thread.currentThread().name}")
        }

    }
    launch (coroutineContext){
        println("C4 : ${Thread.currentThread().name}")
        delay(100)
        println("C4 after delay : ${Thread.currentThread().name}")
    }

    println("\nMain Threads ends: ${Thread.currentThread().name}")
}

suspend fun myOwnSuspendingFunction() {
    delay(1000)
}

suspend fun getMessageOne():String{
    delay(1000L)
    println("\n After working in getMessageOne()")
    return "Hello "
}
suspend fun getMessageTwo():String{
    delay(1000L)
    println("\nAfter working in getMessageTwo()")
    return "World!"
}