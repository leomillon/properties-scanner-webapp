package com.github.leomillon.properties.service;

import com.github.leomillon.properties.config.FileRepositoryConfig;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class FileRetrieverService {

    public static final UrlValidator URL_VALIDATOR = new UrlValidator();
    public static final String PRIVATE_TOKEN_NAME = "private_token";

    @Resource
    private FileRepositoryConfig fileRepositoryConfig;

    public boolean isValidUrl(String url) {
        return URL_VALIDATOR.isValid(url);
    }

    @Nonnull
    public String getContentFromUrl(String url) throws URISyntaxException, IOException {
        return Request.Get(createUriWithPrivateToken(url))
                .execute()
                .returnContent()
                .asString();
    }

    @Nonnull
    private URI createUriWithPrivateToken(String url) throws URISyntaxException {
        return new URIBuilder(url)
                .addParameter(PRIVATE_TOKEN_NAME, fileRepositoryConfig.getPrivateToken())
                .build();
    }

}
