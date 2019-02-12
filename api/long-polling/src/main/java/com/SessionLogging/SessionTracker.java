package com.SessionLogging;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.LinkedList;
import java.util.List;


@WebListener
public class SessionTracker implements HttpSessionListener {

    private final Counter activeSessionsCount;
    private final List<HttpSession> runningSessions;

    private final Logger logger = LoggerFactory.getLogger(HttpSessionListener.class);


    public SessionTracker() {
        super();
        activeSessionsCount = new MetricRegistry()
                .counter("web.sessions.active.count");
        runningSessions = new LinkedList<>();
    }

    public void sessionCreated(final HttpSessionEvent event) {
        logger.info("session employee_id "+ event.getSession().getId());
        this.runningSessions.add(event.getSession());
                activeSessionsCount.inc();
        logger.info(("\nTOTAL SESSION: " + this.activeSessionsCount.getCount()));
    }
    public void sessionDestroyed(final HttpSessionEvent event) {
        activeSessionsCount.dec();
        logger.info("session destroyed " + event.getSession().getId() +
                "TOTAL SESSIONS: " + activeSessionsCount.getCount());
        this.runningSessions.remove(event.getSession());
        logger.info("currently open sessions: \n");
        runningSessions.forEach((element) -> logger.info(String.valueOf(element)));
    }
}