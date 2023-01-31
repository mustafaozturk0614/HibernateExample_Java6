package com.bilgeadam;

import com.bilgeadam.repository.entity.*;
import com.bilgeadam.utility.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*

    bir user olustrup databse e kayıt edelim
 */
public class Main {
    public static void main(String[] args) {
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
                           .build();
        User user2=User.builder()
                .name(Name.builder().name("Mustafa").surName("Ozturk").build())
                .username("musty")
                .password("12345")
                .gender(EGender.MAN)
                .interests(list2)
                .address(map2)
                .build();
        session.save(user);
        session.save(user2);
        transaction.commit();
        session.close();

    }
}