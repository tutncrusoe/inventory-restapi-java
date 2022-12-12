package com.group.inventory.user.boundary;

import com.group.inventory.common.dto.BaseResponse;
import com.group.inventory.common.util.DateTimeUtils;
import com.group.inventory.common.util.ResponseHelper;
import com.group.inventory.user.dto.UserDTORequest;
import com.group.inventory.user.dto.UserDTOResponse;
import com.group.inventory.user.dto.UserWithRolesDTOResponse;
import com.group.inventory.user.model.UserStatus;
import com.group.inventory.user.repository.UserRepository;
import com.group.inventory.user.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRestResourceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserRestResource controller;

    MockedStatic<ResponseHelper> helperMocked;

    @BeforeAll
    void init() {
        helperMocked = Mockito.mockStatic(ResponseHelper.class);
    }

    @Mock
    private HttpServletRequest request;

    @Test
    void shouldFindAllUserWorkCorrectly() {
        UserDTOResponse userDTOResponse = UserDTOResponse.builder()
                .id(UUID.randomUUID())
                .username("user")
                .email("email123@gmail.com")
                .status(UserStatus.ACTIVE)
                .build();
        List<UserDTOResponse> users = List.of(userDTOResponse);

        helperMocked.when(() -> ResponseHelper.getResponse(anyList(), any(HttpStatus.class)))
                .thenReturn(new ResponseEntity<>(
                        BaseResponse
                                .builder()
                                .content(users)
                                .hasError(false)
                                .errors(Collections.emptyList())
                                .timestamp(DateTimeUtils.now())
                                .status(HttpStatus.OK.value())
                                .build(),
                        HttpStatus.OK
                ));

        ResponseEntity<BaseResponse> response = (ResponseEntity<BaseResponse>) controller.findAllUser(request);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(200);
        assertThat(response.getBody().getContent()).isInstanceOf(List.class);
        assertThat(response.getBody().getContent()).isEqualTo(users);
        assertThat(new ArrayList<>((Collection<?>) response.getBody().getContent())).hasSize(1);
        assertThat(response.getBody().isHasError()).isFalse();
        assertThat(response.getBody().getErrors()).isEmpty();
    }

    @Test
    void shouldFindUserByIdWorkCorrectly() {
        UUID id = UUID.randomUUID();
        UserDTOResponse userDTOResponse = UserDTOResponse.builder()
                .id(id)
                .username("user")
                .email("email123@gmail.com")
                .status(UserStatus.ACTIVE)
                .build();

        when(userService.findUserById(id, request)).thenReturn(userDTOResponse);
        helperMocked.when(() -> ResponseHelper.getResponse(any(Object.class), any(HttpStatus.class)))
                .thenReturn(new ResponseEntity<>(
                        BaseResponse
                                .builder()
                                .content(userDTOResponse)
                                .hasError(false)
                                .errors(Collections.emptyList())
                                .timestamp(DateTimeUtils.now())
                                .status(HttpStatus.OK.value())
                                .build(),
                        HttpStatus.OK
                ));

        ResponseEntity<BaseResponse> response = (ResponseEntity<BaseResponse>) controller.findUserById(id, request);
        assertThat(response).isNull();
    }

    @Test
    void shouldFindUserWithRolesWorkCorrectly() {
        UUID id = UUID.randomUUID();
        UserWithRolesDTOResponse userWithRolesDTOResponse = UserWithRolesDTOResponse.builder()
                .id(id)
                .username("user")
                .email("email123@gmail.com")
                .status(UserStatus.ACTIVE)
                .build();
        List<UserWithRolesDTOResponse> users = List.of(userWithRolesDTOResponse);


        helperMocked.when(() -> ResponseHelper.getResponse(anyList(), any(HttpStatus.class)))
                .thenReturn(new ResponseEntity<>(
                        BaseResponse
                                .builder()
                                .content(users)
                                .hasError(false)
                                .errors(Collections.emptyList())
                                .timestamp(DateTimeUtils.now())
                                .status(HttpStatus.OK.value())
                                .build(),
                        HttpStatus.OK
                ));

        ResponseEntity<BaseResponse> response = (ResponseEntity<BaseResponse>) controller.findUserWithRoles(request);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(200);
        assertThat(response.getBody().getContent()).isInstanceOf(List.class);
        assertThat(response.getBody().getContent()).isEqualTo(users);
        assertThat(response.getBody().isHasError()).isFalse();
        assertThat(response.getBody().getErrors()).isEmpty();
    }

    @Test
    void shouldFindUserWithRolesByIdWorkCorrectly() {
        UUID id = UUID.randomUUID();
        UserWithRolesDTOResponse userWithRolesDTOResponse = UserWithRolesDTOResponse.builder()
                .id(id)
                .username("user")
                .email("email123@gmail.com")
                .status(UserStatus.ACTIVE)
                .build();

        when(userService.findUserWithRolesById(id, request)).thenReturn(userWithRolesDTOResponse);
        helperMocked.when(() -> ResponseHelper.getResponse(any(UserWithRolesDTOResponse.class), any(HttpStatus.class)))
                .thenReturn(new ResponseEntity<>(
                        BaseResponse
                                .builder()
                                .content(userWithRolesDTOResponse)
                                .hasError(false)
                                .errors(Collections.emptyList())
                                .timestamp(DateTimeUtils.now())
                                .status(HttpStatus.OK.value())
                                .build(),
                        HttpStatus.OK
                ));

        ResponseEntity<BaseResponse> response = (ResponseEntity<BaseResponse>) controller.findUserWithRolesById(id, request);
        assertThat(response).isNull();
    }

    @Test
    void shouldSignUpWorkCorrectly() {
        UserDTORequest userDTORequest = UserDTORequest.builder()
                .username("user")
                .email("email123@gmail.com")
                .status(UserStatus.ACTIVE)
                .build();

        UUID id = UUID.randomUUID();
        UserWithRolesDTOResponse userWithRolesDTOResponse = UserWithRolesDTOResponse.builder()
                .id(id)
                .username("user")
                .email("email123@gmail.com")
                .status(UserStatus.ACTIVE)
                .build();

        when(userService.createNewUser(userDTORequest, request)).thenReturn(userWithRolesDTOResponse);
        helperMocked.when(() -> ResponseHelper.getResponse(any(UserWithRolesDTOResponse.class), any(HttpStatus.class)))
                .thenReturn(new ResponseEntity<>(
                        BaseResponse
                                .builder()
                                .content(userWithRolesDTOResponse)
                                .hasError(false)
                                .errors(Collections.emptyList())
                                .timestamp(DateTimeUtils.now())
                                .status(HttpStatus.OK.value())
                                .build(),
                        HttpStatus.OK
                ));

        ResponseEntity<BaseResponse> response = (ResponseEntity<BaseResponse>) controller.signUp(userDTORequest, request);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStatus()).isEqualTo(200);
        assertThat(response.getBody().getContent()).isInstanceOf(UserWithRolesDTOResponse.class);
        assertThat(response.getBody().getContent()).isEqualTo(userWithRolesDTOResponse);
        assertThat(response.getBody().isHasError()).isFalse();
        assertThat(response.getBody().getErrors()).isEmpty();
    }
}