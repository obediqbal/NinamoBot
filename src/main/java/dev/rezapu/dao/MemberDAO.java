package dev.rezapu.dao;

import dev.rezapu.model.Member;
import dev.rezapu.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MemberDAO extends BaseDAO<Member>{
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
