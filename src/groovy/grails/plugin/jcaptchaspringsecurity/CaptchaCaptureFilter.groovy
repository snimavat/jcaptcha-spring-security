package grails.plugin.jcaptchaspringsecurity

import org.apache.log4j.Logger
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.grails.plugin.jcaptcha.JcaptchaService
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.filter.OncePerRequestFilter

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

public class CaptchaCaptureFilter extends OncePerRequestFilter {

    private final Logger log = Logger.getLogger(getClass());

    String failureUrl
    JcaptchaService jcaptchaService
    GrailsApplication grailsApplication

    private SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler()

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        HttpSession session = ((GrailsWebRequest) RequestContextHolder.currentRequestAttributes()).getSession();

        try {

            if(req.isPost() && req.getRequestURI().endsWith("/j_spring_security_check") && session.getAttribute("jcaptchaForLogin") && isEnabled()) {
                String jcaptchaChallenge = req.getParameter("jcaptchaChallenge")

                boolean verified = jcaptchaService.validateResponse("login", session.id, jcaptchaChallenge)

                if(!verified) {
                    // Redirect user to login page
                    failureHandler.setDefaultFailureUrl(failureUrl)
                    failureHandler.onAuthenticationFailure(req, res, new CaptchaVerificationFailedException("Captcha invalid"))
                    return;
                }

            }

            chain.doFilter(req, res)

        } catch(Exception e){
            log.error(e.getMessage(), e)
        }
    }

    boolean isEnabled() {
        return (grailsApplication.config.grails.plugin.jcaptchaspringsecurity.enabled == true)
    }

}
