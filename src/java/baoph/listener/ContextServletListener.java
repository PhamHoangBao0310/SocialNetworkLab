/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package baoph.listener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Web application lifecycle listener.
 *
 * @author DELL
 */
public class ContextServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        System.out.println("Deploying........");
        ServletContext context = sce.getServletContext();
//        System.out.println(context.getRealPath("/"));
//        context.setAttribute("realPath", context.getRealPath("/"));
        // create pattern layout
        PatternLayout layout = new PatternLayout();
        // Create coversion pattern 
        String conversionPattern = "%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n";
        // set conversion pattern 
        layout.setConversionPattern(conversionPattern);

        // create console appender
        ConsoleAppender consoleAppender = new ConsoleAppender();
        // set layout for consoleAppender
        consoleAppender.setLayout(layout);
        // activate console appender
        consoleAppender.activateOptions();

        // create file appender
        FileAppender fileAppender = new FileAppender();
        // get curent date
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        // set log file name 
        fileAppender.setFile("D:/socialnetwork-" + timeStamp + ".log");
        // set layout for file appender
        fileAppender.setLayout(layout);
        // activate file appender
        fileAppender.activateOptions();
        
         // configures the root logger
        Logger rootLogger = Logger.getRootLogger();
        // set level is debug
        rootLogger.setLevel(Level.DEBUG);
        // add console appender
        rootLogger.addAppender(consoleAppender);
        // add file appender
        rootLogger.addAppender(fileAppender);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
//        System.out.println("context destroyed ....");
    }
}
