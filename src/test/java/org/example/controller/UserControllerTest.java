package org.example.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

//    @Mock
//    private UserService userService;
//    @InjectMocks
//    @Spy
//    private UserController userController;
//
//    @Test
//    public void testCreate() {
//        UserDTO userToCreate = mock(UserDTO.class);
//        UserDTO userToReturn = mock(UserDTO.class);
//        when(userService.create(userToCreate)).thenReturn(userToReturn);
//
//        UserDTO result = userController.create(userToCreate);
//
//        assertEquals(userToReturn, result);
//        verify(userService).create(userToCreate);
//    }
//
//    @Test
//    public void testRead() {
//        List<UserDTO> users = mock(List.class);
//        when(userService.findAllUsers()).thenReturn(users);
//
//        List<UserDTO> result = userController.read();
//
//        assertEquals(users, result);
//        verify(userService).findAllUsers();
//    }
//
//    @Test
//    public void testReadById() {
//        long id = 1L;
//        UserDTO user = mock(UserDTO.class);
//        when(userService.findUserById(id)).thenReturn(user);
//        ResponseEntity result = userController.read(id);
//
//        assertEquals(user, result.getBody());
//        assertEquals(HttpStatusCode.valueOf(200), result.getStatusCode());
//        verify(userService).findUserById(id);
//    }
//
//    @Test
//    public void testUpdate() {
//        UserDTO userToUpdate = mock(UserDTO.class);
//        UserDTO userToReturn = mock(UserDTO.class);
//        when(userService.update(userToUpdate)).thenReturn(userToReturn);
//
//        UserDTO result = userController.update(userToUpdate);
//
//        assertEquals(userToReturn, result);
//        verify(userService).update(userToUpdate);
//    }
//
//    @Test
//    public void testDelete() {
//        long id = 1L;
//        ResponseEntity result = userController.delete(id);
//
//        verify(userService).deleteUserById(id);
//        assertEquals(ResponseEntity.ok().build(), result);
//    }
//


}
