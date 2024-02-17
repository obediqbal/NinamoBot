package dev.rezapu.dao;

import dev.rezapu.hooks.HookType;
import dev.rezapu.model.Hook;
import org.hibernate.Session;

import java.util.List;

public class HookDAO extends BaseDAO<Hook> {
    public Hook getByMessageId(String message_id){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            return session.createQuery("FROM Hook WHERE message_id = :id", Hook.class)
                    .setParameter("id", message_id)
                    .uniqueResult();
        }
    }

    public List<Hook> getByType(HookType hookType){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            return session.createQuery("FROM Hook WHERE type = :type", Hook.class)
                    .setParameter("type", hookType)
                    .getResultList();
        }
    }

    public List<Hook> getAll(){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            return session.createQuery("FROM Hook", Hook.class)
                    .getResultList();
        }
    }
}
