package ro.ni.bbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.ni.bbs.dto.ClientDTO;
import ro.ni.bbs.dtoconverter.ClientConverter;
import ro.ni.bbs.model.ClientEntity;
import ro.ni.bbs.repository.ClientRepository;

import java.util.List;

/**
 * Created by Rares Abrudan 20.09.2017.
 */
@Service
public class ClientService
{
    private ClientRepository clientRepository;
    private ClientConverter clientConverter;

    @Autowired
    public ClientService(ClientRepository clientRepository, ClientConverter clientConverter)
    {
        this.clientRepository = clientRepository;
        this.clientConverter = clientConverter;
    }

    public void create(ClientDTO clientDTO)
    {
        System.out.println("Saving client...");

        ClientEntity clientEntity = clientConverter.toEntity(clientDTO);
        clientRepository.save(clientEntity);

        System.out.println("Client saved...");
        System.out.println();
    }

    public ClientDTO getByUuid(String uuid)
    {
        ClientEntity clientEntity = clientRepository.findByUuid(uuid);
        return clientConverter.toDTO(clientEntity);
    }

    public List<ClientDTO> getAll()
    {
        List<ClientEntity> continentEntities = clientRepository.findAll();
        System.out.println("Continents entities: " + continentEntities.toString());
        return clientConverter.toDTOs(continentEntities);
    }

    public void delete(String uuid)
    {
        System.out.println("Find client by id");
        clientRepository.delete(clientRepository.findByUuid(uuid));
        System.out.println("Deleted successfully");
        System.out.println();
    }

    public void update(ClientDTO newClientDTO)
    {
        System.out.println("Find the client...");
        ClientEntity clientEntity = clientRepository.findByUuid(newClientDTO.getUuid());

        clientEntity.setName(newClientDTO.getName());
        clientEntity.setEmail(newClientDTO.getEmail());
        clientEntity.setCarModel(newClientDTO.getCarModel());
        clientEntity.setCarName(newClientDTO.getCarName());

        System.out.println("Try to save the changes...");

        clientRepository.save(clientEntity);

        System.out.println("Client updated...");
        System.out.println();
    }

    public void deleteAll()
    {
        clientRepository.deleteAll();
    }
}
