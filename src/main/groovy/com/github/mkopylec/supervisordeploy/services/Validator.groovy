package com.github.mkopylec.supervisordeploy.services

import com.github.mkopylec.supervisordeploy.ext.CommonSettings
import com.github.mkopylec.supervisordeploy.ext.SupervisorTemplate
import org.gradle.api.InvalidUserDataException

import static org.apache.commons.lang3.StringUtils.isBlank

class Validator {

    def validateConfig(CommonSettings config) {
        if (isBlank(config.username)) {
            throw new InvalidUserDataException('Empty config username')
        }
        if (isBlank(config.identity)) {
            throw new InvalidUserDataException('Empty config user identity (private key file path)')
        }
        if (isBlank(config.jarPath)) {
            throw new InvalidUserDataException('Empty config jar file path')
        }
        if (config.sshPort < 1 || config.sshPort > 65535) {
            throw new InvalidUserDataException("Invalid config SSH port: $config.sshPort")
        }
        if (isBlank(config.targetDir)) {
            throw new InvalidUserDataException('Empty config deployment remote target directory')
        }
        if (isBlank(config.targetSupervisorConfigDir)) {
            throw new InvalidUserDataException('Empty config deployment remote target supervisor.conf file path')
        }
    }

    def validateTemplate(SupervisorTemplate template) {
        if (isBlank(template.program)) {
            throw new InvalidUserDataException('Empty supervisor template program name')
        }
        if (isBlank(template.jar_name)) {
            throw new InvalidUserDataException('Empty supervisor template jar file name')
        }
    }
}
