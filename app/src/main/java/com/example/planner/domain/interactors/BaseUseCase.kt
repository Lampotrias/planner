package com.example.planner.domain.interactors

import android.provider.Contacts
import com.example.planner.domain.Result
import com.example.planner.domain.excetion.Failure
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.coroutines.CoroutineContext


abstract class BaseUseCase<in Params, out Type>
    : CoroutineScope
        where Type : Any {

    // region Members
    private val parentJob = SupervisorJob()
    private val mainDispatcher = Dispatchers.Main
    private val backgroundDispatcher = Dispatchers.Default

    override val coroutineContext: CoroutineContext
        get() = parentJob + mainDispatcher

    protected abstract suspend fun run(params: Params): Result<Failure, Type>

    operator fun invoke(params: Params, onResult: (Result<Failure, Type>) -> Unit = {}) {
        val job = async(backgroundDispatcher) {
            run(params)
        }
        launch(mainDispatcher) {
            onResult(job.await())
        }
    }
}

class None : Any()