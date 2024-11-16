package com.jasho.gtps.repository;

import com.jasho.gtps.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author Jashobanta Patra
 * crated on 11-11-2024
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Modifying
    @Query(value = "UPDATE User u set u.firstName =:firstName, u.lastName =:lastName, u.email =:email where u.id =:id ")
    void update(String firstName, String lastName, String email, Long id);

    List<User> findByIdIn(List<Long> ids);

    @Query("SELECT u FROM User u where u.email=:email")
    Optional<User> isEmailExist(String email);
}
