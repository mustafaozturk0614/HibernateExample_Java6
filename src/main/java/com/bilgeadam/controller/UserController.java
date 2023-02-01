package com.bilgeadam.controller;

import com.bilgeadam.repository.criteriaquery.UserRepository;
import com.bilgeadam.repository.entity.*;
import com.bilgeadam.repository.hql.UserDao;
import com.bilgeadam.utility.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.*;

/*
    UserDao-->hql sorgularım için
    UserRepository-->criteriaQuery sorgularım için

 */
public class UserController {

    public static void main(String[] args) {
        //createUser();
        UserDao userDao=new UserDao();
        UserRepository userRepository=new UserRepository();
        // userRepository.findAll().forEach(x-> System.out.println(x.getUsername()));
       // userDao.findAll().forEach(x-> System.out.println(x.getUsername()));
       // userRepository.findAllUserName();
      //  userRepository.findAllUserFirstName();
    //    userDao.findAllUserName();
     //   userDao.findAllUserUsername();
/*    Optional<User> user=  userRepository.findbyId(10L);
     System.out.println(user);*/
        Optional<User> user2=  userDao.findbyId(3L);
        System.out.println(user2);
      /*  Optional<User> user3=  userDao.findbyId2(20L);
        System.out.println(user3);*/
    }

    public  static  void createUser(){
        Session session= HibernateUtils.getSessionFactory().openSession();
        Transaction transaction= session.beginTransaction();
        //  Name name=Name.builder().name("Zeliha").surName("Ünlü").build();
        List<String> list1= Arrays.asList("Astroloji","Sinema");
        List<String> list2= Arrays.asList("Dans","Müzik");

        Map<AddressType, Address> map= new HashMap<>();
        map.put(AddressType.HOME,Address.builder().street("Fatih cad").country("Türkiye").city("Ankara").build());
        map.put(AddressType.BUSINESS,Address.builder().street("Atatürk cad").country("Türkiye").city("Ankara").build());

        Map<AddressType, Address> map2= new HashMap<>();
        map2.put(AddressType.HOME,Address.builder().street("Fatih cad").country("Türkiye").city("İstanbul").build());

        User user=User.builder()
                .name(Name.builder().name("Zeliha").middleName("Oznur").surName("Ünlü").build())
                .username("zlh")
                .password("12345")
                .gender(EGender.WOMAN)
                .interests(list2)
                .address(map)
                .postCount(20)
                .build();
        User user2=User.builder()
                .name(Name.builder().name("Mustafa").surName("Ozturk").build())
                .username("musty")
                .password("12345")
                .gender(EGender.MAN)
                .interests(list2)
                .address(map2)
                .postCount(10)
                .build();
        User user3=User.builder()
                .name(Name.builder().name("Merve").surName("Birtane").build())
                .username("merve")
                .password("12345")
                .gender(EGender.WOMAN)
                .postCount(5)
                .build();
        User user4=User.builder()
                .name(Name.builder().name("Gokhan").surName("Kaya").build())
                .username("gokhan")
                .password("12345")
                .gender(EGender.MAN)
                .postCount(4)
                .build();
        User user5=User.builder()
                .name(Name.builder().name("Mert").surName("Gurel").build())
                .username("mert")
                .password("12345")
                .gender(EGender.MAN)
                .postCount(7)
                .build();
        session.save(user);
        session.save(user2);
        session.save(user3);
        session.save(user4);
        session.save(user5);
        transaction.commit();
        session.close();
    }
}
