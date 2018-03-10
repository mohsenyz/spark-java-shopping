package com.mphj.freelancer.utils;

import org.apache.velocity.app.VelocityEngine;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.Map;

public class ViewUtils {

    public static String render(String templatePath) {
        return strictVelocityEngine().render(new ModelAndView(new HashMap<>(), templatePath));
    }

    public static String render(String templatePath, Map map) {
        return strictVelocityEngine().render(new ModelAndView(map, templatePath));
    }

    private static VelocityTemplateEngine strictVelocityEngine() {
        VelocityEngine configuredEngine = new VelocityEngine();
        configuredEngine.setProperty("runtime.references.strict", true);
        configuredEngine.setProperty("resource.loader", "class");
        configuredEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        return new VelocityTemplateEngine(configuredEngine);
    }

}
