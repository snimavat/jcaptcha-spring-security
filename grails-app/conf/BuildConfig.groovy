grails.project.work.dir = '.grails'

grails.project.dependency.resolver = "maven"
grails.project.dependency.resolution = {

    inherits("global")
    log "info"

    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()

        //below repositories are copied from jcaptcha plugin's build config. Some how grails dint pick this dependencies otherwise
        mavenRepo 'http://maven.jahia.org/maven2/'
        mavenRepo 'http://maven.it.su.se/nexus/content/groups/public/'
    }

    dependencies {
        compile "com.google.guava:guava:14.0-rc1"
        compile 'net.sf.ehcache:ehcache-core:2.6.9' //spring security needs it, cane be removed when updated to spring-security-core:2.0-RC5
    }

    plugins {
        compile ":jcaptcha:1.5.0"
        compile ":spring-security-core:2.0-RC4"
        build(":release:3.0.1", ":rest-client-builder:1.0.3") {
            export = false
        }
    }
}
