package com.github.mkopylec.supervisordeploy.ext

import org.gradle.api.NamedDomainObjectContainer

class SupervisorDeployConfig extends Configurable {

    NamedDomainObjectContainer<Environment> environments

    def environments(Closure<NamedDomainObjectContainer<Environment>> closure) {
        environments.configure(closure)
    }
}
