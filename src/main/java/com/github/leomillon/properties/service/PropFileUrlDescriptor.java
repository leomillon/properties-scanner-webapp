package com.github.leomillon.properties.service;

import com.github.leomillon.properties.scanner.descriptor.PropFileDescriptor;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Function;

import static java.util.Objects.*;

class PropFileUrlDescriptor implements PropFileDescriptor {

    @Nonnull
    private final String url;
    @Nonnull
    private final Function<String, InputStream> urlToInputStreamFunction;

    PropFileUrlDescriptor(@Nonnull String url, Function<String, InputStream> urlToInputStreamFunction) {
        this.url = requireNonNull(url);
        this.urlToInputStreamFunction = requireNonNull(urlToInputStreamFunction);
    }

    @Nonnull
    @Override
    public String getId(int index) {
        return String.valueOf(index);
    }

    @Nonnull
    @Override
    public String getName(int index) {
        try {
            return getLastUrlPathPart();
        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException("Unable to read last url path part", e);
        }
    }

    @Nonnull
    private String getLastUrlPathPart() throws URISyntaxException {
        String[] segments = new URI(url).getPath().split("/");
        return segments[segments.length - 1];
    }

    @Nonnull
    @Override
    public InputStream getInputStream() throws IOException {
        return urlToInputStreamFunction.apply(url);
    }
}
