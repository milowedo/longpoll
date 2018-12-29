package com.LongPolling;

import javax.servlet.http.HttpSession;

public interface HangingRequest {
    boolean execute();
    void killSession();
    void setSession(HttpSession session);
}
