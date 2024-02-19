package com.kbtg.bootcamp.posttest.config;

import com.kbtg.bootcamp.posttest.model.UserModel;
import com.kbtg.bootcamp.posttest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class InitialUserSetup implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {

        if(userRepository.findAll().isEmpty()) {
            UserModel userModel = new UserModel();

            userModel.setUsername("admin");
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            userModel.setPassword(encoder.encode("password"));
            userModel.setPermissions(List.of("ADMIN"));
            userModel.setRoles(List.of("ADMIN"));
            userModel.setUserId(UUID.randomUUID().toString().replaceAll("-","").substring(0,10));

            userRepository.save(userModel);
        }

    }
}
