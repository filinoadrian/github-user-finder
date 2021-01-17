package com.filinoadrian.githubuserfinder

import com.filinoadrian.githubuserfinder.di.ViewModelBuilder
import com.filinoadrian.githubuserfinder.di.SearchModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelBuilder::class,
        TestApplicationModule::class,
        SearchModule::class
    ]
)
interface TestApplicationComponent : AndroidInjector<TestMainApplication> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: TestMainApplication): TestApplicationComponent
    }
}