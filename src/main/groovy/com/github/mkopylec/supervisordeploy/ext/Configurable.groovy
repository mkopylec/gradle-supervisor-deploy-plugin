package com.github.mkopylec.supervisordeploy.ext

import static groovy.lang.Closure.DELEGATE_FIRST

abstract class Configurable {

    CommonSettings config
    SupervisorTemplate template

    def config(Closure<CommonSettings> closure) {
        if (!config) {
            config = new CommonSettings()
        }
        closure.resolveStrategy = DELEGATE_FIRST
        closure.delegate = config
        closure()
    }

    def template(Closure<SupervisorTemplate> closure) {
        if (!template) {
            template = new SupervisorTemplate()
        }
        closure.resolveStrategy = DELEGATE_FIRST
        closure.delegate = template
        closure()
    }
}
