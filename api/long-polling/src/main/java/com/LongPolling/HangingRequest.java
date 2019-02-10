package com.LongPolling;

import javax.servlet.http.HttpSession;

public interface HangingRequest { //subscriber
    boolean update();
    void killSession();
    void setSession(HttpSession session);
}
