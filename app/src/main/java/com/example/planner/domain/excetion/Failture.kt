package com.example.planner.domain.excetion

sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    class DatabaseErrorQuery(val message: String?) : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure: Failure()
}