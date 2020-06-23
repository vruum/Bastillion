package io.bastillion

import io.bastillion.manage.controllers.*
import io.javalin.Javalin
import io.javalin.http.Handler
import io.javalin.plugin.rendering.JavalinRenderer
import io.javalin.plugin.rendering.template.JavalinThymeleaf
import org.thymeleaf.TemplateEngine
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver


fun main(args: Array<String>) {
    val tl = JavalinThymeleaf
    val templateEngine = TemplateEngine()
    val fileTemplateResolver = ClassLoaderTemplateResolver()
    fileTemplateResolver.prefix = "/thymeleaf/"
    val templateResolvers = templateEngine.templateResolvers
    templateEngine.setTemplateResolver(fileTemplateResolver)
    val app = Javalin.create { config ->
        config.addStaticFiles("/res")
    }
            .start(7000)
    JavalinRenderer.register(JavalinThymeleaf, ".html");
    JavalinThymeleaf.configure(templateEngine)
    app.before(Handler {
        it.attribute("errors", ArrayList<String>())
        it.attribute("fieldErrors", HashMap<String, String>())
    })
    IndexController(app)
    AuthKeysController(app)
    LoginController(app)
    OTPController(app)
    ProfileController(app)
    ScriptController(app)
    SecureShellController(app)
    SessionAuditController(app)
    SystemController(app)
    UploadAndPushController(app)
    UsersController(app)
    UserSettingsController(app)


}