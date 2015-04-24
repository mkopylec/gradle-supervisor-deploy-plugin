package com.github.mkopylec.supervisordeploy.tasks

import com.github.mkopylec.supervisordeploy.ext.CommonSettings
import com.github.mkopylec.supervisordeploy.ext.Environment
import com.github.mkopylec.supervisordeploy.ext.Host
import com.github.mkopylec.supervisordeploy.ext.SupervisorTemplate
import com.github.mkopylec.supervisordeploy.services.Deployer
import org.gradle.api.DefaultTask
import org.gradle.api.InvalidUserDataException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class DeployTask extends DefaultTask {

    @Input
    String env

    @TaskAction
    def deploy() {
        Environment environment = getEnvironment()
        Map<String, CommonSettings> hostsConfigs = getHostsConfigs(environment)
        Map<String, SupervisorTemplate> hostsTemplates = getHostsTemplates(environment)
        deployOnHosts(hostsConfigs, hostsTemplates)
    }

    protected Environment getEnvironment() {
        if (!project.deployConfig.environments) {
            throw new InvalidUserDataException('No environment defined')
        }
        Environment environment = project.deployConfig.environments.findByName(env)
        if (!environment) {
            throw new InvalidUserDataException("Invalid environment '$env'")
        }
        environment
    }

    protected Map<String, CommonSettings> getHostsConfigs(Environment environment) {
        if (!environment.hosts) {
            throw new InvalidUserDataException("No hosts defined for '$environment.name' environment")
        }
        Map<String, CommonSettings> hostsConfigs = [:]
        environment.hosts.each {
            CommonSettings config = resolveConfig(it, environment)
            if (!config) {
                throw new InvalidUserDataException("No config defined for host '$it.name'")
            }
            hostsConfigs << ["$it.name": config]
        }
        return hostsConfigs
    }

    protected Map<String, SupervisorTemplate> getHostsTemplates(Environment environment) {
        if (!environment.hosts) {
            throw new InvalidUserDataException("No hosts defined for '$environment.name' environment")
        }
        Map<String, SupervisorTemplate> hostsTemplates = [:]
        environment.hosts.each {
            SupervisorTemplate template = resolveTemplate(it, environment)
            if (!template) {
                throw new InvalidUserDataException("No supervisor template defined for host '$it.name'")
            }
            hostsTemplates << ["$it.name": template]
        }
        return hostsTemplates
    }

    protected void deployOnHosts(Map<String, CommonSettings> hostsConfigs, Map<String, SupervisorTemplate> hostsTemplates) {
        Deployer deployer = new Deployer()
        hostsConfigs.each {
            deployer.deployApp(it.key, it.value, hostsTemplates.get(it.key))
        }
    }

    protected CommonSettings resolveConfig(Host host, Environment environment) {
        if (host.config) {
            return host.config
        }
        if (environment.config) {
            return environment.config
        }
        return project.deployConfig.config
    }

    protected SupervisorTemplate resolveTemplate(Host host, Environment environment) {
        if (host.template) {
            return host.template
        }
        if (environment.template) {
            return environment.template
        }
        return project.deployConfig.template
    }
}
