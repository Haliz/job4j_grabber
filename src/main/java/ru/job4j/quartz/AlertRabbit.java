package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    private static final Properties PROPERTIES = new Properties();

    public static void main(String[] args) {
            AlertRabbit.loadProperties();
        try (Connection connection = getConnection()) {
            int interval = Integer.parseInt(PROPERTIES.getProperty("rabbit.interval"));
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("cn", connection);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(interval)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
            System.out.println(" Здесь запись после завершения.");
        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    public static void loadProperties() {
        try (InputStream in = AlertRabbit.class.getClassLoader()
                .getResourceAsStream("rabbit.properties")) {
            PROPERTIES.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws Exception {
        Class.forName(PROPERTIES.getProperty("driver-class-name"));
        return DriverManager.getConnection(
                PROPERTIES.getProperty("url"),
                PROPERTIES.getProperty("username"),
                PROPERTIES.getProperty("password")
        );
    }

    public static class Rabbit implements Job {

        @Override
        public void execute(JobExecutionContext context) {
            System.out.println("Rabbit runs here ...");
            Connection connection = (Connection) context.getJobDetail().getJobDataMap().get("cn");
            try (PreparedStatement statement = connection
                    .prepareStatement(
                            "insert into rabbit (created_date) values(current_timestamp);")) {
                statement.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
