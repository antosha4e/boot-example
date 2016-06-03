package com.bootexample.repository;

import com.bootexample.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE LOWER(u.name) = LOWER(:name) and LOWER(u.password) = LOWER(:password)")
    User find(@Param("name") String name, @Param("password") String password);

    List<User> findAll(Specification<User> spec);

    @Query("SELECT u FROM User u")
    Iterable<User> findAll();

    @Query("SELECT count(id) FROM User")
    long count();

    User findById(Long id);

    User findByEmail(String email);
}