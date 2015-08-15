package grails.plugin.jcaptchaspringsecurity

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.web.pages.discovery.GrailsConventionGroovyPageLocator

/**
 * Created by sudhir on 8/14/2015.
 */
class JcaptchaSpringSecurityTagLib {

    GrailsApplication grailsApplication
    GrailsConventionGroovyPageLocator groovyPageLocator

    def jcaptchaLogin = { attrs, body ->
        boolean enabled = (grailsApplication.config.grails.plugin.jcaptchaspringsecurity.enabled == true)

        if(session.jcaptchaForLogin == true && enabled){
            out << render(template: template.path, plugin: template.plugin)
        }

    }

    /**
     * See if template is overriden in host project, then use it.
     * @return
     */
    private Map getTemplate() {
        String path = "/templates/jcaptchaLogin"
        Map template = [path:path]
        def override = groovyPageLocator.findTemplateInBinding(path, pageScope)
        if(!override) {
            template.plugin = "jcaptcha-spring-security"
        }

        return  template
    }

}
