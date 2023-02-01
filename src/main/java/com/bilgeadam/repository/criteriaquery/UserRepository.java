package com.bilgeadam.repository.criteriaquery;
/*
   1- findall fonksiyonu yazalım

 */


import com.bilgeadam.repository.entity.ICrud;
import com.bilgeadam.repository.entity.Name;
import com.bilgeadam.repository.entity.User;
import com.bilgeadam.utility.HibernateUtils;
import org.hibernate.Criteria;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
/*

1-find all
2-findallUserName
3-findbyid;--> dısaridan bir id alacagız bu idye ait useri donecegız
 */
public class UserRepository implements ICrud<User> {
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    public UserRepository() {
        entityManager= HibernateUtils.getSessionFactory().createEntityManager();
        criteriaBuilder= entityManager.getCriteriaBuilder();
    }

    @Override
    public List<User> findAll() {
        CriteriaQuery<User> criteria=criteriaBuilder.createQuery(User.class);
        Root<User> root=criteria.from(User.class);
        criteria.select(root);
        List<User> users=entityManager.createQuery(criteria).getResultList();
        return users;
    }

    @Override
    public Optional<User> findbyId(Long id) {
        User user=null;
        CriteriaQuery<User> criteria=criteriaBuilder.createQuery(User.class);
        Root<User> root=criteria.from(User.class);
        criteria.select(root).where(criteriaBuilder.equal(root.get("id"),id));
        try {
          user=entityManager.createQuery(criteria).getSingleResult();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(user);
    }

    public void findAllUserName(){
        CriteriaQuery<Name> criteria=criteriaBuilder.createQuery(Name.class);
        Root<User> root=criteria.from(User.class);
        criteria.select(root.get("name"));
        List<Name> nameList=entityManager.createQuery(criteria).getResultList();
        nameList.forEach(System.out::println);
    }

    public void findAllUserFirstName(){
        CriteriaQuery<String> criteria=criteriaBuilder.createQuery(String.class);
        Root<User> root=criteria.from(User.class);
        criteria.select(root.get("name").get("name"));
        List<String> nameList=entityManager.createQuery(criteria).getResultList();
        nameList.forEach(System.out::println);
    }


}
