package com.github.mkopylec.supervisordeploy.ext

import org.gradle.api.NamedDomainObjectContainer

class Environment extends Configurable {

    final String name
    NamedDomainObjectContainer<Host> hosts

    Environment(String name) {
        this.name = name
    }

    def hosts(Closure<NamedDomainObjectContainer<Host>> closure) {
        hosts.configure(closure)
    }
}
