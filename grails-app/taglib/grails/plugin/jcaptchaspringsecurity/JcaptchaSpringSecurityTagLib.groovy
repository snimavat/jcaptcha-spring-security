package grails.plugin.jcaptchaspringsecurity

import org.codehaus.groovy.grails.commons.GrailsApplication

/**
 * Created by sudhir on 8/14/2015.
 */
class JcaptchaSpringSecurityTagLib {

    GrailsApplication grailsApplication

    def jcaptchaLogin = { attrs, body ->
        boolean enabled = (grailsApplication.config.grails.plugin.jcaptchaspringsecurity.enabled == true)
        if(session.jcaptchaForLogin == true && enabled){
            out << render(template: "/templates/jcaptchaLogin", plugin: 'jcaptcha-spring-security')
        }
    }

}
