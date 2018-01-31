package com.fresh.rares.android_api.model;

/**
 * Created by Rares Abrudan on 1/18/2018.
 *
 * @mail - raresabr@gmail.com
 */

public class Client
{
    private String id;
    private String field1;
    private String field2;
    private String field3;
    private String field4;

    public Client(String id, String field1, String field2, String field3, String field4)
    {
        this.id = id;
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
        this.field4 = field4;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getField1()
    {
        return field1;
    }

    public void setField1(String field1)
    {
        this.field1 = field1;
    }

    public String getField2()
    {
        return field2;
    }

    public void setField2(String field2)
    {
        this.field2 = field2;
    }

    public String getField3()
    {
        return field3;
    }

    public void setField3(String field3)
    {
        this.field3 = field3;
    }

    public String getField4()
    {
        return field4;
    }

    public void setField4(String field4)
    {
        this.field4 = field4;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (id != null ? !id.equals(client.id) : client.id != null) return false;
        if (field1 != null ? !field1.equals(client.field1) : client.field1 != null) return false;
        if (field2 != null ? !field2.equals(client.field2) : client.field2 != null) return false;
        if (field3 != null ? !field3.equals(client.field3) : client.field3 != null) return false;
        return field4 != null ? field4.equals(client.field4) : client.field4 == null;
    }

    @Override
    public int hashCode()
    {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (field1 != null ? field1.hashCode() : 0);
        result = 31 * result + (field2 != null ? field2.hashCode() : 0);
        result = 31 * result + (field3 != null ? field3.hashCode() : 0);
        result = 31 * result + (field4 != null ? field4.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "Client{" +
                "id='" + id + '\'' +
                ", field1='" + field1 + '\'' +
                ", field2='" + field2 + '\'' +
                ", field3='" + field3 + '\'' +
                ", field4='" + field4 + '\'' +
                '}';
    }
}
