package com.fresh.rares.android_api.repository;

import com.fresh.rares.android_api.model.Client;

import java.util.List;

/**
 * Created by Rares Abrudan on 1/18/2018.
 * @email - raresabr@gmail.com
 */

public interface IRepository<TElem>
{
    void add(TElem client);

    void delete(TElem client);

    void update(TElem client, int position);

    List<TElem> getAll();

    void set(List<Client> clients);
}
