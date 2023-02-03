package com.bilgeadam.repository.criteriaquery;

import com.bilgeadam.repository.entity.Post;
import com.bilgeadam.utility.HibernateUtils;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.awt.*;
import java.util.List;

/*
    12- her kullanıcı kac post atmıs onu bulalım
    13- 3den fazla post atmıs kullanıcı idlerini donelim;

 */
public class PostRepository  {



    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    public PostRepository() {
        entityManager = HibernateUtils.getSessionFactory().createEntityManager();
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }


    public void  usersPostCount(){
        CriteriaQuery<Tuple> criteria=criteriaBuilder.createQuery(Tuple.class);
        Root<Post> root= criteria.from(Post.class);
        criteria.multiselect(root.get("userId"),criteriaBuilder.count(root))
                .groupBy(root.get("userId"));
        List<Tuple> tuples=entityManager.createQuery(criteria).getResultList();
        tuples.forEach(x->{
            System.out.println(x.get(0)+"-"+x.get(1));
        });
    }
    public void  usersPostCountGt3(){
        CriteriaQuery<Tuple> criteria=criteriaBuilder.createQuery(Tuple.class);
        Root<Post> root= criteria.from(Post.class);
        // select userId,count(userId) from tblpost gruop by userId having count(userId)>3
        Path<Long> userId=root.get("userId");
        Expression<Long> count=criteriaBuilder.count(root.get("userId"));

        criteria.multiselect(userId,count).groupBy(userId)
                .having(criteriaBuilder.greaterThan(count,3L));


        List<Tuple> tuples=entityManager.createQuery(criteria).getResultList();
        tuples.forEach(x->{
            System.out.println(x.get(0)+"-"+x.get(1));
        });

    }



}
