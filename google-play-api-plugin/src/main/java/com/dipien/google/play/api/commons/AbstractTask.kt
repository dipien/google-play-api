package com.dipien.google.play.api.commons

import com.dipien.google.play.api.GooglePlayPublisherPlugin
import com.dipien.google.play.api.GooglePlayPublisherPluginExtension
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction

abstract class AbstractTask : DefaultTask() {

    @get:Internal
    protected lateinit var propertyResolver: PropertyResolver

    @TaskAction
    fun doExecute() {
        propertyResolver = PropertyResolver(project)
        onExecute()
    }

    @Internal
    protected fun getExtension(): GooglePlayPublisherPluginExtension {
        return project.extensions.getByName(GooglePlayPublisherPlugin.EXTENSION_NAME) as GooglePlayPublisherPluginExtension
    }

    protected abstract fun onExecute()
}
