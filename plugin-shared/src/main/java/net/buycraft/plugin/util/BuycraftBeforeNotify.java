package net.buycraft.plugin.util;

import com.bugsnag.BeforeNotify;
import com.bugsnag.Error;
import net.buycraft.plugin.client.ApiException;

public class BuycraftBeforeNotify implements BeforeNotify {
    @Override
    public boolean run(Error error) {
        boolean found = false;
        for (StackTraceElement element : error.getStackTrace()) {
            // Send only errors that mention a BuycraftX stack trace.
            if (element.getClassName().startsWith("net.buycraft.plugin")) {
                found = true;
                break;
            }
        }

        if (!found) {
            return false;
        }

        if (error.getException() instanceof ApiException) {
            ApiException exception = (ApiException) error.getException();
            if (exception.getSentRequest() != null) {
                error.addToTab("http", "request_sent", exception.getSentRequest().toString());
            }
            if (exception.getReceivedResponse() != null) {
                error.addToTab("http", "received_response", exception.getReceivedResponse().toString());
                error.addToTab("http", "received_headers", exception.getReceivedResponse().headers().toString());
            }
            if (exception.getResponseBody() != null) {
                error.addToTab("http", "received_body", exception.getResponseBody());
            }
        }

        return true;
    }
}
