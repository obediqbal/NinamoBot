package dev.rezapu.utils;

import io.github.cdimascio.dotenv.Dotenv;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory(){
        try{
            Configuration configuration = new Configuration().configure();
            configuration.setProperty("hibernate.connection.url", System.getProperty("DB_CONNECTION_URL"));
            configuration.setProperty("hibernate.connection.username", System.getProperty("DB_USERNAME"));
            configuration.setProperty("hibernate.connection.password", System.getProperty("DB_PASSWORD"));
            return configuration.buildSessionFactory();
        }
        catch (Throwable ex){
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
