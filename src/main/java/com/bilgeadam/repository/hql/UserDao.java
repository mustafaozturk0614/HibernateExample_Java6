package com.bilgeadam.repository.hql;

import com.bilgeadam.repository.entity.ICrud;
import com.bilgeadam.repository.entity.Name;
import com.bilgeadam.repository.entity.User;
import com.bilgeadam.utility.HibernateUtils;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class UserDao implements ICrud<User> {

    @Override
    public List<User> findAll() {
        String hql="select u from User as u";
        Session session= HibernateUtils.getSessionFactory().openSession();
        TypedQuery<User> typedQuery=session.createQuery(hql, User.class);
        List<User> users=typedQuery.getResultList();
        return users;
    }
    @Override
    public Optional<User> findbyId(Long id) {
        User user=null;
        String hql="select u from User as u where id="+id;
        Session session= HibernateUtils.getSessionFactory().openSession();
        TypedQuery<User> typedQuery=session.createQuery(hql, User.class);
       try {
          user =typedQuery.getSingleResult();
       }catch (Exception e){
           System.out.println(e.getMessage());
       }
        return Optional.ofNullable(user);
    }


    public Optional<User> findbyId2(Long id) {
        User user=null;
        try {
            Session session= HibernateUtils.getSessionFactory().openSession();
            user =session.find(User.class,id);
            if (user==null){
                System.out.println("Kullanıcı bulunamadı");
                return Optional.empty();
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(user);
    }

    public void findAllUserName(){
        String hql="select name from User";
        Session session= HibernateUtils.getSessionFactory().openSession();
        TypedQuery<Name> typedQuery=session.createQuery(hql, Name.class);
        List<Name> users=typedQuery.getResultList();
        users.forEach(System.out::println);
    }

    public void findAllUserFirstName(){
        String hql="select name.name from User";
        Session session= HibernateUtils.getSessionFactory().openSession();
        TypedQuery<String> typedQuery=session.createQuery(hql, String.class);
        List<String> users=typedQuery.getResultList();
        users.forEach(System.out::println);
    }
    public void findAllUserUsername(){
        String hql="select username from User";
        Session session= HibernateUtils.getSessionFactory().openSession();
        TypedQuery<String> typedQuery=session.createQuery(hql, String.class);
        List<String> users=typedQuery.getResultList();
        users.forEach(System.out::println);
    }
}
