# jcaptcha-spring-security
Grails Jpcatcha spring security plugin

Jcaptcha spring security plugin integrates [Jcaptch plugin](https://grails.org/plugin/jcaptcha) with [spring security core plugin](http://grails.org/plugin/spring-security-core)

## Installing the plugin

```
plugins {
    compile ':jcaptcha-spring-security:0.1'
}
```


## Configuration
Jcaptcha spring security plugin needs a catcha with name 'login' to be defined in configuration.
See [Jcaptch plugin](https://grails.org/plugin/jcaptcha) for how to configure jcaptchas

**Example**

Config.groovy
```
jcaptchas {
	login = new GenericManageableCaptchaService(
		new GenericCaptchaEngine(
			new GimpyFactory(
				new RandomWordGenerator(
					"abcdefghijklmnopqrstuvwxyz1234567890"
				),
				new ComposedWordToImage(
					new RandomFontGenerator(
						20, // min font size
						30, // max font size
						[new Font("Arial", 0, 10)] as Font[]
					),
					new GradientBackgroundGenerator(
						140, // width
						35, // height
						new SingleColorGenerator(new Color(0, 60, 0)),
						new SingleColorGenerator(new Color(20, 20, 20))
					),
					new NonLinearTextPaster(
						6, // minimal length of text
						6, // maximal length of text
						new Color(0, 255, 0)
					)
				)
			)
		),
		180, // minGuarantedStorageDelayInSeconds
		100000, // maxCaptchaStoreSize
		75000
	)
}
```

Add following lines to your config.groovy

```
grails {
	plugin {
		jcaptchaspringsecurity {
			enabled = true
			time = 5
			allowedNumberOfAttempts = 3
		}
	}
}
```

**time** - Time in minutes to remember the failed attempts (eg. 3 failed attempts in last 5 minutes)
**allowedNumberOfAttempts** - Allowed number of attempts after which captcha should be displayed.



## Add captcha to the login form.
Plugin provides a tag jcaptchaLogin which can be used to render the captcha for login form.

Add following lines to loginform.

```
<g:jcaptchaLogin/>
```

It will display the image captcha along with the input text field after allowed number of failed login attempts.

## Customize the captcha style / markup

You can override the template by copying /templates/_jcaptchaLogin.gsp to your application at path grails-app/views/templates/_jcaptchaLogin.gsp. And customize it as per your need.
