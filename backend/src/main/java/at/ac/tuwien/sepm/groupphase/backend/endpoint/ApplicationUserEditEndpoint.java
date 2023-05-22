package at.ac.tuwien.sepm.groupphase.backend.endpoint;

import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserDetailDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.dto.UserRegisterDto;
import at.ac.tuwien.sepm.groupphase.backend.endpoint.mapper.UserMapper;
import at.ac.tuwien.sepm.groupphase.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.lang.invoke.MethodHandles;

@RestController
@RequestMapping(value = ApplicationUserEditEndpoint.BASE_PATH)
public class ApplicationUserEditEndpoint {

    static final String BASE_PATH = "/api/v1/edit";

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public ApplicationUserEditEndpoint(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void delete(@RequestParam(value = "id") Long id) {
        LOGGER.info("DELETE " + BASE_PATH);
        userService.delete(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    @PermitAll
    @Operation(summary = "Edit a user")
    public void update(@Valid @RequestBody UserDetailDto userDetailDto, @RequestParam(value = "token") String token) {
        LOGGER.info("EDIT USER " + BASE_PATH + "with TOKEN " + token, userDetailDto);
        userMapper.entityToUserDetailDto(userService.edit(userMapper.userDetailDtoToEntity(userDetailDto), token));
    }

    @GetMapping
    public UserDetailDto getUser(@RequestParam(value = "token") String token) {
        LOGGER.info("GET USER " + BASE_PATH + "with TOKEN" + token);
        return userMapper.entityToUserDetailDto(userService.getUser(token));
    }
}