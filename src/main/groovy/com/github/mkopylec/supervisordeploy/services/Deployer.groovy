package com.github.mkopylec.supervisordeploy.services

import com.github.mkopylec.supervisordeploy.ext.CommonSettings
import com.github.mkopylec.supervisordeploy.ext.SupervisorTemplate
import org.hidetake.groovy.ssh.Ssh
import org.slf4j.Logger

import static org.slf4j.LoggerFactory.getLogger

class Deployer {

    private static Logger log = getLogger(Deployer)

    private Validator validator = new Validator()
    private SupervisorConfGenerator confGenerator = new SupervisorConfGenerator()

    def deployApp(String hostname, CommonSettings config, SupervisorTemplate template) {
        log.info("Deploying on host $hostname")

        validator.validateConfig(config)
        validator.validateTemplate(template)

        template.directory = config.targetDir

        def ssh = Ssh.newService()

        ssh.remotes {
            target {
                host = hostname
                port = config.sshPort
                user = config.username
                identity = new File(config.identity)
                knownHosts = allowAnyHosts
            }
        }

        ssh.run {
            session(ssh.remotes.target) {
                def jarName = config.jarPath.tokenize(File.separator).last()
                execute "mkdir -p $config.targetDir"
                put from: config.jarPath, into: "$config.targetDir/$jarName"

                execute "sudo echo \"${confGenerator.generate(template)}\" > $config.targetSupervisorConfigDir/${jarName}.conf"

                execute 'sudo service supervisor restart'
            }
        }
    }
}
