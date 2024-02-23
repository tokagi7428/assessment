package com.kbtg.bootcamp.posttest.config;

import static org.mockito.Mockito.*;

import com.kbtg.bootcamp.posttest.model.UserModel;
import com.kbtg.bootcamp.posttest.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;


@ExtendWith(MockitoExtension.class)
class InitialUserSetupTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    InitialUserSetup initialUserSetup;

    @Test
    @DisplayName("finAll for return null")
    public void testRunWhenUserRepositoryIsEmpty() throws Exception {
        when(userRepository.findAll()).thenReturn(List.of());

        initialUserSetup.run(null);

        verify(userRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("finAll for return UserModel is null")
    public void testRunWhenUserRepositoryIsNotEmpty() throws Exception {
        when(userRepository.findAll()).thenReturn(List.of(new UserModel()));

        initialUserSetup.run(null);

        verify(userRepository, never()).save(any());
    }
}