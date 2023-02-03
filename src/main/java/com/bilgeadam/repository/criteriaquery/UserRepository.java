package com.bilgeadam.repository.criteriaquery;
/*
   1- findall fonksiyonu yazalım

 */


import com.bilgeadam.repository.entity.*;
import com.bilgeadam.utility.HibernateUtils;

import javax.naming.event.ObjectChangeListener;
import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

/*

1-find all
2-findallUserName
3-findbyid;--> dısaridan bir id alacagız bu idye ait useri donecegız
4-findbyusername-->dışarıdan bir username alacagız
5- ismi M ile başlayanları getiren sorgu
6- ismi M ile baslayıp postcountu 6 nın uzerinde olanları getiren metotoları yazalım
7- toplam post sayısını donen metodu yazalım
8- en az post atan kullanıcı
9- en cok post atan kullanıcı
10- kullanıcların username gender ve post count donelim
11- erkek kullanıcı sayısı ve kadın kullanıcı sayısını bulalım
12-

 */
public class UserRepository implements ICrud<User> {
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;

    public UserRepository() {
        entityManager = HibernateUtils.getSessionFactory().createEntityManager();
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @Override
    public List<User> findAll() {
        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);
        List<User> users = entityManager.createQuery(criteria).getResultList();
        return users;
    }

    @Override
    public Optional<User> findbyId(Long id) {
        User user = null;
        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root).where(criteriaBuilder.equal(root.get("id"), id));


        try {
            user = entityManager.createQuery(criteria).getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(user);
    }


    @Override
    public Optional<User> findByUsername(String username) {
        User user = null;
        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root).where(criteriaBuilder.equal(root.get("username"), username));
        try {
            user = entityManager.createQuery(criteria).getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(user);
    }

    public void findAllUserName() {
        CriteriaQuery<Name> criteria = criteriaBuilder.createQuery(Name.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root.get("name"));
        List<Name> nameList = entityManager.createQuery(criteria).getResultList();
        nameList.forEach(System.out::println);
    }

    public void findAllUserFirstName() {
        CriteriaQuery<String> criteria = criteriaBuilder.createQuery(String.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root.get("name").get("name"));
        List<String> nameList = entityManager.createQuery(criteria).getResultList();
        nameList.forEach(System.out::println);
    }

    public List<User> findByUserStartWithValue(String value) {
        CriteriaQuery<User> criteira = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteira.from(User.class);
        //1.yontem
     /*   Predicate predicate=criteriaBuilder.like(root.get("name").get("name"),value+"%");
        criteira.where(predicate);*/
        //2.yontem
        criteira.select(root).where(criteriaBuilder.like(root.get("name").get("name"), value + "%"));
        return entityManager.createQuery(criteira).getResultList();
    }
    public List<User> findByUserStartWithValueAndPostCount() {
        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);

        criteria.select(root).where(criteriaBuilder.and
                (criteriaBuilder.like(root.get("name").get("name"), "M%"),
                        criteriaBuilder.greaterThan(root.get("postCount"), 6)));
        return entityManager.createQuery(criteria).getResultList();

    }
    public List<User> findByUserStartWithValueAndPostCount2() {
        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);

        //  Path<String> path=root.get("name").get("name");

        Predicate like = criteriaBuilder.like(root.get("name").get("name"), "M%");
        Predicate gt = criteriaBuilder.greaterThan(root.get("postCount"), 6);
      /*  Predicate gtandLike=criteriaBuilder.and(criteriaBuilder.like(root.get("name").get("name"),"M%"),
                criteriaBuilder.greaterThan(root.get("postCount"),6));*/
        criteria.select(root).where(criteriaBuilder.and(like, gt));
        return entityManager.createQuery(criteria).getResultList();

    }

    public int sumPost() {
        CriteriaQuery<Integer> criteria = criteriaBuilder.createQuery(Integer.class);
        Root<User> root = criteria.from(User.class);
        //  Expression<Integer> sum=criteriaBuilder.sum(root.get("postCount"));
        criteria.select(criteriaBuilder.sum(root.get("postCount")));
        return entityManager.createQuery(criteria).getSingleResult();
    }

    public User findUserWithMostPost() {
        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root).orderBy(criteriaBuilder.desc(root.get("postCount")));
        return entityManager.createQuery(criteria).getResultList().get(0);
    }

    public User findUserWithMostPost2() {
        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        CriteriaQuery<Integer> criteria2 = criteriaBuilder.createQuery(Integer.class);
        Root<User> root2= criteria2.from(User.class);
        criteria2.select(criteriaBuilder.max(root2.get("postCount")));
        int max = entityManager.createQuery(criteria2).getSingleResult();
        criteria.select(root).where(criteriaBuilder.equal(root.get("postCount"), max));
        return entityManager.createQuery(criteria).getSingleResult();
    }
    public List<Object[]> getUsernameGenderPostCount(){
        CriteriaQuery<Object[]> criteria= criteriaBuilder.createQuery(Object[].class);
        Root<User> root= criteria.from(User.class);
        criteria.multiselect(root.get("username"),root.get("gender"),root.get("postCount"));
        return  entityManager.createQuery(criteria).getResultList();
    }
    public List<Tuple> getUsernameGenderPostCount2(){
        CriteriaQuery<Tuple> criteria= criteriaBuilder.createQuery(Tuple.class);
        Root<User> root= criteria.from(User.class);
        Path<String> username=root.get("username");
        Path<EGender> gender=root.get("gender");
        Path<Integer> postCount=root.get("postCount");
        criteria.multiselect(username,gender,postCount);


       List<Tuple> tuple=  entityManager.createQuery(criteria).getResultList();
       tuple.forEach(x->{
           System.out.println( "username= "+x.get(username));
           System.out.println( "gender= "+x.get(gender));
           System.out.println( "postcount= "+x.get(postCount));
           System.out.println("---------------------------");
       });
       return  tuple;
    }

    public List<Object[]> userCountByGender(){
        CriteriaQuery<Object[]> criteria = criteriaBuilder.createQuery(Object[].class);
        Root<User> root = criteria.from(User.class);
        // select  gender,count(*) from user group by gender
        criteria.multiselect(root.get("gender"),criteriaBuilder.count(root))
                .groupBy(root.get("gender"));
        return entityManager.createQuery(criteria).getResultList();
    }


}