/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whoscored.listener;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import whoscored.crawler.WhoScoredCrawler;
import whoscored.data.League;
import whoscored.function.LeagueFunction;

/**
 * Web application lifecycle listener.
 *
 * @author haleduykhang
 */
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Context Initialized!");
        ServletContext sc = sce.getServletContext();
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                WhoScoredCrawler crawler = new WhoScoredCrawler();
                List<League> list = LeagueFunction.getLeagues();
                for (League l : list) {
                    try {
                        crawler.crawlMatches(l);
                    } catch (ParseException ex) {
                        Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };

        Timer timer = new Timer();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 3);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MILLISECOND, 00);

        timer.schedule(timerTask, calendar.getTime(), TimeUnit.HOURS.toMillis(5));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Context Destroyed!");
    }
}
