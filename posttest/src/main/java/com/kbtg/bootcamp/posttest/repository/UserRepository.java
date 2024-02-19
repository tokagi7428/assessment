package com.kbtg.bootcamp.posttest.repository;

import com.kbtg.bootcamp.posttest.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<UserModel,Integer> {

    UserModel findUserByUsername(String username);

    Optional<UserModel> findByUsername(String username);

    Optional<UserModel> findByUserId(String userId);
}
