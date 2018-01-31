package ro.ni.bbs.dtoconverter;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import ro.ni.bbs.dto.UserDTO;
import ro.ni.bbs.model.UserEntity;

/**
 * Created by Rares Abrudan 19.09.2017.
 */
@Component
public class UserConverter implements Converter<UserEntity, UserDTO>
{
    @Override
    public UserDTO toDTO(UserEntity element)
    {
        UserDTO userDTO = new UserDTO();

        userDTO.setUsername(element.getUsername());
        userDTO.setPassword(element.getPassword());

        return userDTO;
    }

    @Override
    public List<UserDTO> toDTOs(List<UserEntity> elements)
    {
        List<UserDTO> dtos = new ArrayList<>();
        for (UserEntity element : elements)
        {
            dtos.add(toDTO(element));
        }
        return dtos;
    }

    @Override
    public UserEntity toEntity(UserDTO dtoElement)
    {
        return new UserEntity(dtoElement.getUsername(), dtoElement.getPassword());
    }

    @Override
    public List<UserEntity> toEntitys(List<UserDTO> dtoElements)
    {
        List<UserEntity> entities = new ArrayList<>();
        for (UserDTO dto : dtoElements)
        {
            entities.add(toEntity(dto));
        }
        return entities;
    }
}
