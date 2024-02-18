package dev.rezapu.dao;

import dev.rezapu.enums.FoodBuffType;
import dev.rezapu.model.FoodBuff;
import dev.rezapu.model.Member;
import dev.rezapu.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<FoodBuffType, List<FoodBuff>> getAll(){
        Map<FoodBuffType, List<FoodBuff>> res = new HashMap<>();
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            List<FoodBuffType> types = session.createQuery("SELECT type FROM FoodBuff GROUP BY type", FoodBuffType.class)
                    .getResultList();

            for(FoodBuffType type: types){
                List<FoodBuff> foodBuffList = session.createQuery("FROM FoodBuff WHERE type=:type GROUP BY id, type ORDER BY level DESC ", FoodBuff.class)
                        .setParameter("type", type)
                        .getResultList();
                res.put(type, foodBuffList);
            }
        }

        return res;
    }
    public Map<FoodBuffType, List<FoodBuff>> getByTypes(FoodBuffType... types){
        Map<FoodBuffType, List<FoodBuff>> res = new HashMap<>();
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();

            for(FoodBuffType type: types){
                List<FoodBuff> foodBuffList = session.createQuery("FROM FoodBuff WHERE type=:type GROUP BY id, type ORDER BY level DESC ", FoodBuff.class)
                        .setParameter("type", type)
                        .getResultList();
                res.put(type, foodBuffList);
            }
        }

        return res;
    }
}
