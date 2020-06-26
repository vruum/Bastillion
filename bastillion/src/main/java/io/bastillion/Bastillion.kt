package io.bastillion

import io.bastillion.manage.db.SchemaDao
import io.bastillion.manage.controllers.*
import io.bastillion.manage.services.AuthenticationService
import io.bastillion.manage.services.SchemaService
import io.javalin.Javalin
import io.javalin.http.Handler
import io.javalin.plugin.rendering.JavalinRenderer
import io.javalin.plugin.rendering.template.JavalinThymeleaf
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin
import org.jdbi.v3.sqlobject.kotlin.onDemand
import org.thymeleaf.TemplateEngine
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver


fun main(args: Array<String>) {

    val jdbi = jdbi();
    schemaService(jdbi)
            .initialiseSchema(true)
    registerThymeleaf()
    registerControllers(app())
}

fun app(): Javalin {
    return Javalin.create { config ->
        config.addStaticFiles("/res")
        config.accessManager(AuthenticationService())
    }.start(7000)
}

fun jdbi(): Jdbi {
    System.setProperty("h2.baseDir", SchemaService::class.java.getClassLoader().getResource(".").getPath())
    val jdbi = Jdbi.create("jdbc:h2:keydb/bastillion2;CIPHER=AES", "test", "filepwd test")
    jdbi.installPlugin(SqlObjectPlugin())
    jdbi.installPlugin(KotlinSqlObjectPlugin())
    return jdbi;
}

fun registerThymeleaf() {
    val tl = JavalinThymeleaf
    val templateEngine = TemplateEngine()
    val fileTemplateResolver = ClassLoaderTemplateResolver()
    fileTemplateResolver.prefix = "/thymeleaf/"
    val templateResolvers = templateEngine.templateResolvers
    templateEngine.setTemplateResolver(fileTemplateResolver)
    JavalinRenderer.register(JavalinThymeleaf, ".html");
    JavalinThymeleaf.configure(templateEngine)
}

fun schemaService(jdbi: Jdbi): SchemaService {
    return SchemaService(jdbi.onDemand<SchemaDao>());
}

fun registerControllers(app: Javalin) {
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
    app.before(Handler {
        it.attribute("errors", ArrayList<String>())
        it.attribute("fieldErrors", HashMap<String, String>())
    })
}