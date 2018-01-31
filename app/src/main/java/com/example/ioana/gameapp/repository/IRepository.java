package com.example.ioana.gameapp.repository;

import java.util.List;
import com.example.ioana.gameapp.domain.Item;

/**
 * Created by Ioana on 1/31/2018.
 */

public interface IRepository<TElem> {

    void add(TElem item);

    void delete(TElem item);

    void update(TElem item, int position);

    List<TElem> getAll();

    void set(List<Item> items);
}
