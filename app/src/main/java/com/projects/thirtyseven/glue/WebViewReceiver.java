package com.projects.thirtyseven.glue;

import com.google.api.client.extensions.java6.auth.oauth2.VerificationCodeReceiver;

import java.io.IOException;

/**
 * Created by admin on 11.06.2017.
 */

public class WebViewReceiver implements VerificationCodeReceiver {

    public WebViewReceiver() {
    }

    @Override
    public String getRedirectUri() throws IOException {
        return "http://localhost/?code=4/d5ivhc77S7fcvM0lkkgxlE7eiAijX7mMJrEYFXcNqek#";
    }

    @Override
    public String waitForCode() throws IOException {
        return null;
    }

    @Override
    public void stop() throws IOException {

    }
}
