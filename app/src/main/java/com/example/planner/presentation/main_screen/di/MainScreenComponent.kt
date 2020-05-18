package com.example.planner.presentation.main_screen.di

import androidx.appcompat.app.AppCompatActivity
import com.example.planner.di.AppComponent
import com.example.planner.di.scope.PerFragment
import com.example.planner.presentation.main_screen.MainScreenFragment
import dagger.Component

@Component(
    dependencies = [AppComponent::class]
)
@PerFragment
interface MainScreenComponent {
    fun inject(fragment: MainScreenFragment)
}