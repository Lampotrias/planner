package com.example.planner.domain.excetion

sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    object DatabaseErrorQuery : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure: Failure()
}