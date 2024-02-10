package dev.rezapu.dao;

import dev.rezapu.enums.FoodBuffType;
import dev.rezapu.model.FoodBuff;
import dev.rezapu.model.Member;
import dev.rezapu.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class FoodBuffDAO extends BaseDAO<FoodBuff> {
    public List<FoodBuff> getByAddress(String address){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            return session.createQuery("FROM FoodBuff WHERE address = :address", FoodBuff.class)
                    .setParameter("address", address)
                    .getResultList();
        }
    }
    public List<FoodBuff> getByType(FoodBuffType type){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            return session.createQuery("FROM FoodBuff WHERE type = :type", FoodBuff.class)
                    .setParameter("type", type)
                    .getResultList();
        }
    }
}
