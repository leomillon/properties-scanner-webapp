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
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
