package com.lamp.planner.domain

sealed class Result<out L, out R> {
    data class Error<out L>(val a: L) : Result<L, Nothing>()
    data class Success<out R>(val b: R) : Result<Nothing, R>()

    fun fold(fnL: (L) -> Any, fnR: (R) -> Any): Any =
        when (this) {
            is Error -> fnL(a)
            is Success -> fnR(b)
        }
}
