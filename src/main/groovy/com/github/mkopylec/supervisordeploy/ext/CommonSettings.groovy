package com.github.mkopylec.supervisordeploy.ext

import groovy.transform.ToString
import org.gradle.api.InvalidUserDataException

import static org.apache.commons.lang3.StringUtils.isBlank
import static org.apache.commons.lang3.StringUtils.isNotBlank
import static org.apache.commons.lang3.StringUtils.removeEnd

@ToString(includeNames = true)
class CommonSettings {

    String username;
    String identity = '~/.ssh/id_rsa'
    int sshPort = 22
    String jarPath
    String targetDir
    String targetSupervisorConfigDir = '/etc/supervisor/conf.d'

    String getTargetDir() {
        if (isBlank(targetDir) && isNotBlank(username) && isNotBlank(jarPath)) {
            def app = removeEnd(jarPath.tokenize(File.separator).last(), '.jar')
            return "/home/$username/$app"
        }
        return targetDir
    }

    void setUsername(String username) {
        this.username = username
    }

    void setJarPath(String jarPath) {
        if (!(jarPath ==~ /.+.jar/)) {
            throw new InvalidUserDataException("Invalid path to jar: '$jarPath'")
        }
        this.jarPath = jarPath
    }
}
