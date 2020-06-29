package com.lamp.planner.domain.interactors

import com.lamp.planner.domain.Result
import com.lamp.planner.domain.excetion.Failure
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

abstract class BaseUseCase<in Params, out Type> : CoroutineScope
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
