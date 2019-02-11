package com.LongPolling;

import com.entity.Resolvable;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public interface HangingRequest { //subscriber
    boolean update(Resolvable resolved);
    boolean checkForTimeout();
    void killSession();
    void setSession(HttpSession session);
}
