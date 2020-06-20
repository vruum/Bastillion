package io.bastillion;

import io.bastillion.common.db.DbInit;
import loophole.mvc.base.DispatcherServlet;
import loophole.mvc.base.TemplateServlet;
import loophole.mvc.config.TemplateConfig;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.*;

public class ServerRunner {

    public static void main(String[] args) throws Exception {
        Server server = new Server(3456);

        String rootPath = ServerRunner.class.getClassLoader().getResource(".").toString();
        WebAppContext webapp = new WebAppContext(rootPath + "../../src/main/webapp", "/");
        webapp.addServlet(DispatcherServlet.class, "*" + DispatcherServlet.CTR_EXT);
        webapp.addServlet(TemplateServlet.class, "*" + TemplateServlet.VIEW_EXT);
        AnnotationConfiguration annotationConfiguration = new AnnotationConfiguration();
        new TemplateConfig().init(webapp.getServletContext());
        new DbInit().init();
        webapp.setConfigurations(new Configuration[]
                {
                        annotationConfiguration,
                        new WebInfConfiguration(),
                        new WebXmlConfiguration(),
                        new MetaInfConfiguration(),
                        new FragmentConfiguration(),
                        new EnvConfiguration(),
                        new PlusConfiguration(),
                        new JettyWebXmlConfiguration()
                });
        server.setHandler(webapp);
        server.start();

        server.join();
    }
}
