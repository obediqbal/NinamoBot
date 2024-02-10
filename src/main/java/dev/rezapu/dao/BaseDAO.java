package dev.rezapu.dao;

import dev.rezapu.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class BaseDAO<T> {
    protected final SessionFactory sessionFactory;

    public BaseDAO(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void addData(T data){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(data);
            session.getTransaction().commit();
        }
    }

    public void deleteData(T data){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.remove(data);
            session.getTransaction().commit();
        }
    }

    public void updateData(T data){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.merge(data);
            session.getTransaction().commit();
        }
    }
}
