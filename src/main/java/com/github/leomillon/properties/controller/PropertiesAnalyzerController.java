package com.github.leomillon.properties.controller;

import com.github.leomillon.properties.model.AnalyzedProperties;
import com.github.leomillon.properties.service.PropertiesLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class PropertiesAnalyzerController {

    @Resource
    private PropertiesLoader propertiesLoader;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index() {
        return new ModelAndView("index", "analyzedProperties", loadProperties());
    }

    private AnalyzedProperties loadProperties() {
        try {
            return propertiesLoader.loadPropertiesFromFiles(
                    "/Users/leomillon/Downloads/properties/file1.properties",
                    "/Users/leomillon/Downloads/properties/file2.properties"
                    /*"/Users/leomillon/repos/ekino/apps/frontoffice/src/main/resources/config/common/common.properties",
                    "/Users/leomillon/repos/ekino/apps/frontoffice/src/main/resources/config/common/services.properties",
                    "/Users/leomillon/repos/ekino/apps/frontoffice/src/main/resources/config/users/ezy-sc.properties",
                    "/Users/leomillon/repos/ekino/apps/frontoffice/src/main/resources/config/users/cms-valid-1-psw1.properties"*/
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
