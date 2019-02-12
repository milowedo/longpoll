package com.LongPolling;

import com.entity.Resolvable;

import javax.servlet.http.HttpSession;

public interface HangingRequest { //subscriber

    boolean update(Resolvable resolved);
    void checkForTimeout();
    void setSession(HttpSession session);
    boolean checkState();
}
