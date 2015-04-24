package com.github.mkopylec.supervisordeploy.ext

import groovy.transform.ToString
import org.gradle.api.InvalidUserDataException

import static org.apache.commons.lang3.StringUtils.isBlank
import static org.apache.commons.lang3.StringUtils.removeEnd
import static org.apache.commons.lang3.StringUtils.trimToEmpty

@ToString(includeNames = true)
class SupervisorTemplate {

    String program
    String jvm_args = ''
    String jar_name
    String program_args = ''
    String process_name
    Integer numprocs
    String numprocs_start
    Integer priority
    Boolean autostart
    String autorestart
    Integer startsecs
    Integer startretries
    String exitcodes
    String stopsignal
    Integer stopwaitsecs
    Boolean stopasgroup
    Boolean killasgroup
    String user
    Boolean redirect_stderr
    String stdout_logfile
    String stdout_logfile_maxbytes
    Integer stdout_logfile_backups
    String stdout_capture_maxbytes
    Boolean stdout_events_enabled
    String stderr_logfile
    String stderr_logfile_maxbytes
    Integer stderr_logfile_backups
    String stderr_capture_maxbytes
    Boolean stderr_events_enabled
    String environment
    String directory
    String umask
    String serverurl

    void setJar_name(String jar_name) {
        if (isBlank(jar_name)) {
            throw new InvalidUserDataException('Empty supervisor template jar name')
        }
        this.jar_name = jar_name
        program = removeEnd(jar_name, '.jar')
    }

    String getJvm_args() {
        return trimToEmpty(jvm_args)
    }

    String getProgram_args() {
        return trimToEmpty(program_args)
    }
}
