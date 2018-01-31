package ro.ni.bbs.service;

import org.springframework.stereotype.Service;
import ro.ni.bbs.dto.UserDTO;
import ro.ni.bbs.dtoconverter.UserConverter;
import ro.ni.bbs.model.UserEntity;
import ro.ni.bbs.repository.UserRepository;

import java.util.List;

/**
 * *  Created by rares on 1/10/2018.
 */

@Service
public class UserService
{
    private UserRepository userRepository;
    private UserConverter userConverter;

    public UserService(UserRepository userRepository, UserConverter userConverter)
    {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    public String findByUsernameAndPassword(String username, String password)
    {
        System.out.println("Try to find user...");

        UserEntity userEntity = userRepository.findByUsernameAndPassword(username, password);

        UserDTO userDTO;
        if (userEntity != null)
             userDTO = userConverter.toDTO(userEntity);
        else return "LoginFailed";

        if (userDTO != null) return "LoginSuccessful";
        else return "LoginFailed";
    }


    public List<UserDTO> getAll()
    {
        return userConverter.toDTOs(userRepository.findAll());
    }

    public String register(UserDTO userDTO)
    {
        if (userRepository.findByUsernameAndPassword(userDTO.getUsername(), userDTO.getPassword()) != null)
            return "UserExists";

        userRepository.save(userConverter.toEntity(userDTO));

        return "UserDoesNotExist";
    }
}
