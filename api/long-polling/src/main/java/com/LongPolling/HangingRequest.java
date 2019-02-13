package com.LongPolling;

import javax.servlet.http.HttpSession;

public interface HangingRequest { //subscriber

    void update(Resolvable resolved);
    void checkForTimeout();
    void setSession(HttpSession session);
    boolean checkIfResolved();
}
