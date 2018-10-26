package com.dcoletto.serialdelight

import org.gradle.api.Plugin;
import org.gradle.api.Project;

class SerialDelightPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.tasks.create("serialDelight", SerialDelightTask.class)
    }
}