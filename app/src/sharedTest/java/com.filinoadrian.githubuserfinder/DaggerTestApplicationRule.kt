package com.filinoadrian.githubuserfinder

import android.content.Context
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import androidx.test.core.app.ApplicationProvider

class DaggerTestApplicationRule : TestWatcher() {

    lateinit var component: TestApplicationComponent
        private set

    override fun starting(description: Description?) {
        super.starting(description)

        val app = ApplicationProvider.getApplicationContext<Context>() as TestMainApplication
        component = DaggerTestApplicationComponent.factory().create(app)
        component.inject(app)
    }
}