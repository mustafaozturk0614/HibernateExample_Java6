package com.bilgeadam.controller;

import com.bilgeadam.repository.criteriaquery.PostRepository;
import com.bilgeadam.repository.entity.Post;
import com.bilgeadam.utility.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.Date;

public class PostController {

    public static void main(String[] args) {
    //createPost();
        PostRepository postRepository=new PostRepository();
        postRepository.usersPostCount();
        System.out.println("-----------------");
        postRepository.usersPostCountGt3();

    }


    public  static void  createPost(){
        Session session= HibernateUtils.getSessionFactory().openSession();
        Transaction transaction= session.beginTransaction();
        Post post= Post.builder().content("içerik1").userId(1L).build();
        Post post2= Post.builder().content("içerik2").userId(2L).build();
        Post post3= Post.builder().content("içerik3").userId(3L).build();
        Post post4= Post.builder().content("içerik4").userId(4L).build();
        Post post5= Post.builder().content("içerik5").userId(5L).build();
        Post post6= Post.builder().content("içerik6").userId(4L).build();
        Post post7= Post.builder().content("içerik7").userId(4L).build();
        Post post8= Post.builder().content("içerik8").userId(4L).build();
        Post post9= Post.builder().content("içerik9").userId(5L).build();
        Post post10= Post.builder().content("içerik10").userId(5L).build();
        Post post11= Post.builder().content("içerik11").userId(5L).build();
        Post post12= Post.builder().content("içerik12").userId(1L).build();
        session.save(post);
        session.save(post2);
        session.save(post3);
        session.save(post4);
        session.save(post5);
        session.save(post6);
        session.save(post7);
        session.save(post8);
        session.save(post9);
        session.save(post10);
        session.save(post11);
        session.save(post12);
        transaction.commit();
        session.close();
    }
}
