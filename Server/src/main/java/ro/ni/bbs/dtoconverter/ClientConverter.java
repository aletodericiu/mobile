package ro.ni.bbs.dtoconverter;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import ro.ni.bbs.dto.ClientDTO;
import ro.ni.bbs.model.ClientEntity;

/**
 * Created by Rares Abrudan 19.09.2017.
 */
@Component
public class ClientConverter implements Converter<ClientEntity, ClientDTO>
{
    @Override
    public ClientDTO toDTO(ClientEntity element)
    {
        ClientDTO clientDTO = new ClientDTO();

        clientDTO.setUuid(element.getUuid());
        clientDTO.setName(element.getName());
        clientDTO.setEmail(element.getEmail());
        clientDTO.setCarModel(element.getCarModel());
        clientDTO.setCarName(element.getCarName());

        return clientDTO;
    }

    @Override
    public List<ClientDTO> toDTOs(List<ClientEntity> elements)
    {
        List<ClientDTO> dtos = new ArrayList<>();
        for (ClientEntity element : elements)
        {
            dtos.add(toDTO(element));
        }
        return dtos;
    }

    @Override
    public ClientEntity toEntity(ClientDTO dtoElement)
    {
        return new ClientEntity(dtoElement.getUuid(), dtoElement.getName(), dtoElement.getEmail(), dtoElement.getCarModel(), dtoElement.getCarName());

    }

    @Override
    public List<ClientEntity> toEntitys(List<ClientDTO> dtoElements)
    {
        List<ClientEntity> entities = new ArrayList<>();
        for (ClientDTO dto : dtoElements)
        {
            entities.add(toEntity(dto));
        }
        return entities;
    }

}
