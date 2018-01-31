package com.fresh.rares.android_api.repository;

import com.fresh.rares.android_api.model.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rares Abrudan on 1/19/2018.
 *
 * @email - raresabr@gmail.com
 */

public class InMemoryRepository implements IRepository<Client>
{
    private List<Client> clients;

    public InMemoryRepository()
    {
        clients = new ArrayList<>();
    }

    @Override
    public void add(Client client)
    {
        clients.add(client);
    }

    @Override
    public void delete(Client client)
    {
        clients.remove(client);
    }

    @Override
    public void update(Client client, int position)
    {
        clients.set(position, client);
    }

    @Override
    public List<Client> getAll()
    {
        return clients;
    }

    public void set(List<Client> clients)
    {
//        this.clients.clear();
//        this.clients = clients;
        this.clients.clear();
        this.clients.addAll(clients);
    }
}
