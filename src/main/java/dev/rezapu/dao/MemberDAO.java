package dev.rezapu.dao;

import dev.rezapu.model.Member;
import dev.rezapu.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MemberDAO {
    private final SessionFactory sessionFactory;

    public MemberDAO(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    public void addMember(Member member){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(member);
            session.getTransaction().commit();
        }
    }

    public void deleteMember(Member member){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.remove(member);
            session.getTransaction().commit();
        }
    }

    public void updateMember(Member member){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.merge(member);
            session.getTransaction().commit();
        }
    }

    public Member getByDiscordId(String discord_id){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            return session.createQuery("FROM Member WHERE discord_id = :discord_id", Member.class)
                    .setParameter("discord_id", discord_id)
                    .uniqueResult();
        }
    }

    public Member getByIGN(String ign){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            return session.createQuery("FROM Member WHERE ign = :ign", Member.class)
                    .setParameter("discord_id", ign)
                    .uniqueResult();
        }
    }
}
