package com.github.mkopylec.supervisordeploy.services

import com.github.mkopylec.supervisordeploy.ext.SupervisorTemplate

import static org.apache.commons.lang3.StringUtils.isNotBlank

class SupervisorConfGenerator {

    String generate(SupervisorTemplate template) {
        def program = "[program:$template.program]\n"
        def command = "command=java $template.jvm_args -jar $template.jar_name $template.program_args\n"
        def settings = ''
        template.properties.each {
            if (!['class', 'program', 'jvm_args', 'jar_name', 'program_args'].contains(it.key)) {
                if (isNotBlank((String) it.value)) {
                    settings += "$it.key=$it.value\n"
                }
            }
        }
        return program + command + settings
    }
}
