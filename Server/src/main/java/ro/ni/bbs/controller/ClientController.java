package ro.ni.bbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.ni.bbs.dto.ClientDTO;
import ro.ni.bbs.service.ClientService;

import java.util.List;

/**
 * Created by Rares Abrudan 20.09.2017.
 */

@CrossOrigin
@RestController
@RequestMapping(value = "/client")
public class ClientController
{
    private ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService)
    {
        this.clientService = clientService;
    }

    @RequestMapping(value="/create", method = RequestMethod.POST)
    public void create(@RequestBody ClientDTO clientDTO)
    {
        System.out.println("Try to add...");
        clientService.create(clientDTO);
    }

    @RequestMapping(value="/", method = RequestMethod.GET)
    public List<ClientDTO> getAll()
    {
        System.out.println("Get all clients");
        return clientService.getAll();
    }

    @RequestMapping(value = "/delete/{uuid}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String uuid)
    {
        System.out.println("Try to delete...");
        clientService.delete(uuid);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public void update(@RequestBody ClientDTO clientDTO)
    {
        System.out.println("Try to update...");
        clientService.update(clientDTO);
    }

    @RequestMapping(value="getByUuid/{uuid}", method = RequestMethod.GET)
    public ClientDTO getByUuid(@PathVariable String uuid)
    {
        // we get the continent dto
        // we return the continent dto
        return clientService.getByUuid(uuid);
    }

    @RequestMapping(value = "/deleteAll/", method = RequestMethod.DELETE)
    public void delete()
    {
        clientService.deleteAll();
    }


}
