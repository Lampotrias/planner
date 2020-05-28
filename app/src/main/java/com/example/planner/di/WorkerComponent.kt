package com.example.planner.di

import com.example.planner.di.key.WorkerKey
import com.example.planner.presentation.background.workmanager.ChildWorkerFactory
import com.example.planner.presentation.background.workmanager.SendNotifyWorker
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [AssistedInject_AppAssistedInjectModule::class])
@AssistedModule
interface AppAssistedInjectModule

@Module
interface WorkerBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(SendNotifyWorker::class)
    fun bindSendNotifyWorker(factory: SendNotifyWorker.Factory): ChildWorkerFactory
}