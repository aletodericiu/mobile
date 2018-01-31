package com.fresh.rares.android_api.model;

/**
 * Created by Rares Abrudan on 1/15/2018.
 *
 * @email - raresabr@gmail.com
 */

public class Operation
{
    private Client client;
    private String operation;

    public Operation(String operation, Client client)
    {
        this.client = client;
        this.operation = operation;
    }

    public Client getClient()
    {
        return client;
    }

    public String getOperation()
    {
        return operation;
    }

    @Override
    public String toString()
    {
        return "Operation{" +
                "client=" + client +
                ", operation='" + operation + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operation operation1 = (Operation) o;

        if (client != null ? !client.equals(operation1.client) : operation1.client != null)
            return false;
        return operation != null ? operation.equals(operation1.operation) : operation1.operation == null;
    }

    @Override
    public int hashCode()
    {
        int result = client != null ? client.hashCode() : 0;
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        return result;
    }
}
