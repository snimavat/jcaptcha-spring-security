import grails.plugin.jcaptchaspringsecurity.AuthenticationFailureListener
import grails.plugin.jcaptchaspringsecurity.AuthenticationSuccessListener
import grails.plugin.jcaptchaspringsecurity.CaptchaCaptureFilter
import grails.plugin.springsecurity.SecurityFilterPosition
import grails.plugin.springsecurity.SpringSecurityUtils

class JcaptchaSpringSecurityGrailsPlugin {

    def version = "0.1"
    def grailsVersion = "2.0 > *"

    def title = "Jcaptcha for Spring Security Plugin"
    def author = "Sudhir Nimavat"
    def authorEmail = "sudhir@nimavat.me"
    def description = "Jcaptcha for spring security"

    def documentation = "https://github.com/snimavat/jcaptcha-spring-security"
    def scm = [ url: "https://github.com/snimavat/jcaptcha-spring-security" ]
	def issueManagement = [ system: "Github", url: "https://github.com/snimavat/jcaptcha-spring-security/issues" ]   

    def loadAfter = ['springSecurityCore']

	def license = "APACHE"
	
    def doWithSpring = {
        authenticationFailureListener(AuthenticationFailureListener) {
            loginAttemptCacheService = ref('loginAttemptCacheService')
        }

        authenticationSuccessEventListener(AuthenticationSuccessListener) {
            loginAttemptCacheService = ref('loginAttemptCacheService')
        }

        jcaptchaCaptureFilter(CaptchaCaptureFilter) {
            failureUrl = SpringSecurityUtils.securityConfig.failureHandler.defaultFailureUrl
            jcaptchaService = ref('jcaptchaService')
            grailsApplication = ref("grailsApplication")
        }

        SpringSecurityUtils.registerFilter 'jcaptchaCaptureFilter', SecurityFilterPosition.SECURITY_CONTEXT_FILTER.order + 10

    }

}
