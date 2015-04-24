package com.github.mkopylec.supervisordeploy

import com.github.mkopylec.supervisordeploy.ext.Environment
import com.github.mkopylec.supervisordeploy.ext.Host
import com.github.mkopylec.supervisordeploy.ext.SupervisorDeployConfig
import com.github.mkopylec.supervisordeploy.tasks.DeployTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class SupervisorDeployPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        applyPlugins(project)
        createExtensions(project)
        addTasks(project)
    }

    private void applyPlugins(Project project) {
//        project.plugins.apply('java')
    }

    private void createExtensions(Project project) {
        project.extensions.create('deployConfig', SupervisorDeployConfig)
        project.deployConfig.environments = project.container(Environment)
        project.deployConfig.environments.all {
            hosts = project.container(Host)
        }
    }

    private void addTasks(Project project) {
        project.task('deploy', type: DeployTask)
    }
}
