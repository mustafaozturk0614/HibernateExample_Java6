package com.bilgeadam.repository.entity;

import java.util.List;
import java.util.Optional;

public interface ICrud <T>{

    List<T> findAll();

    Optional<T> findbyId(Long id);

    Optional<T> findByUsername(String username);


}
