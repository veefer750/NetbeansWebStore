/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package music.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import music.data.DBUtil;
import music.business.Product;

/**
 *
 * @author Matt
 */
public class ProductDB {
    
public static List<Product> selectProducts() {
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
    List<Product> products = new ArrayList<>();
    
    String query = "SELECT p FROM Product p";
    TypedQuery<Product> q = em.createQuery(query, Product.class);
    try {
            products = q.getResultList();
            return products;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
}

    public static Product selectProduct(String code) {
    EntityManager em = DBUtil.getEmFactory().createEntityManager();
    Product p = new Product();
    String query = "SELECT p FROM Product p WHERE p.code = :code";
    
    TypedQuery<Product> q = em.createQuery(query, Product.class);
        q.setParameter("code", code);
        try {
            p = q.getSingleResult();
            return p;
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
    
    
    public static void deleteProduct(String code) {
       EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        Product p = new Product();
        p = selectProduct(code);
        trans.begin();        
        try {
            em.remove(em.merge(p));
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }       
    }  
    
public static void insertProduct(Product product)
{
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();        
        try {
            em.persist(product);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
    
}

public static boolean exists(String productCode)
{
    Product u = selectProduct(productCode);   
        return u != null;
    
}

 public static void updateProduct(Product product) {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        trans.begin();       
        try {
            em.merge(product);
            trans.commit();
        } catch (Exception e) {
            System.out.println(e);
            trans.rollback();
        } finally {
            em.close();
        }
            }
}