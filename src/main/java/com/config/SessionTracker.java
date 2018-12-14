package com.config;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.LinkedList;
import java.util.List;


@WebListener
public class SessionTracker implements HttpSessionListener {

    private final Counter counterOfActiveSessions;
    private final List<HttpSession> runningSessions;

    public SessionTracker() {
        super();
        counterOfActiveSessions = new MetricRegistry()
                .counter("web.sessions.active.count");
        runningSessions = new LinkedList<>();
    }

    public void sessionCreated(final HttpSessionEvent event) {
        System.out.println("session created: "+ event.getSession().getId());
        this.runningSessions.add(event.getSession());
                counterOfActiveSessions.inc();
        System.out.println(("TOTAL: " + this.counterOfActiveSessions.getCount()));
    }
    public void sessionDestroyed(final HttpSessionEvent event) {
        counterOfActiveSessions.dec();
        System.out.println("session destroyed " + event.getSession().getId() +
                " TOTAL: " + counterOfActiveSessions.getCount());
        this.runningSessions.remove(event.getSession());
        System.out.println("current open sessions: ");
        runningSessions.forEach((System.out::println));
        System.out.println();
    }
}