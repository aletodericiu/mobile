package com.fresh.rares.android_api.repository;

import com.fresh.rares.android_api.model.Client;
import com.fresh.rares.android_api.model.Operation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rares Abrudan on 1/19/2018.
 * @email - raresabr@gmail.com
 */

public class OperationRepository
{
    private List<Operation> operations;

    public OperationRepository()
    {
        this.operations = new ArrayList<>();
    }

    public void add(Client client)
    {
        operations.add(new Operation("add", client));
    }

    public void delete(Client client)
    {
        operations.add(new Operation("remove", client));
    }

    public void update(Client client, int position)
    {
        operations.add(new Operation("update", client));
    }

    public List<Operation> getOperations()
    {
        return operations;
    }
}
