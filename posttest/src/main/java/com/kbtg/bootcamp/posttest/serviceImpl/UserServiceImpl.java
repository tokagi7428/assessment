package com.kbtg.bootcamp.posttest.serviceImpl;

import com.kbtg.bootcamp.posttest.dto.ResponseDto;
import com.kbtg.bootcamp.posttest.dto.UserDto;
import com.kbtg.bootcamp.posttest.exception.BadRequestException;
import com.kbtg.bootcamp.posttest.exception.NotFoundException;
import com.kbtg.bootcamp.posttest.model.UserModel;
import com.kbtg.bootcamp.posttest.repository.TicketRepository;
import com.kbtg.bootcamp.posttest.repository.UserRepository;
import com.kbtg.bootcamp.posttest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Optional<UserModel> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public ResponseDto createUser(UserDto userDto) {

        Optional<UserModel> userModel = userRepository.findByUsername(userDto.getUsername());

        if(userModel.isPresent()) {
            throw new BadRequestException("ไม่สามารถใช้ username นี้ได้ เนื่องจากผู้ใช้คนนี้มีข้อมูลในระบบแล้วครับ");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(userDto.getPassword());
        UserModel createUser = new UserModel();

        createUser.setPassword(encodedPassword);
        createUser.setUsername(userDto.getUsername());
        createUser.setPermissions(List.of("USER"));
        createUser.setRoles(List.of("USER"));
        createUser.setUserId(UUID.randomUUID().toString().replaceAll("-","").substring(0,10));
        userRepository.save(createUser);
        System.out.println(encodedPassword);
        return new ResponseDto(LocalDateTime.now(),
                    HttpStatus.CREATED.value(),
                    userDto.getUsername(),
                "create user successfully"
                );
    }

    @Override
    @Transactional
    public ResponseDto editUser(UserDto user, String userId) {
        Optional<UserModel> userModelOpt = userRepository.findByUserId(userId);
        if(userModelOpt.isPresent()) {
            UserModel userModel = userModelOpt.get();
//            userModel.setPassword(user.getPassword());
            userModel.setRoles(user.getRoles());
            userModel.setPermissions(user.getPermissions());
            userRepository.save(userModel);
        }else{
            throw new NotFoundException("User not found");
        }
        return new ResponseDto(LocalDateTime.now(),
                HttpStatus.CREATED.value(),
                null,
                "update username : " + user.getUsername() + " successfully!"
        );
    }

}
