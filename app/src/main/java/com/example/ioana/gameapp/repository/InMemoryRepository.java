package com.example.ioana.gameapp.repository;

import com.example.ioana.gameapp.domain.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ioana on 1/31/2018.
 */

public class InMemoryRepository implements IRepository<Item>{
    private List<Item> items;

    public InMemoryRepository()
    {
        items = new ArrayList<>();
    }

    @Override
    public void add(Item item)
    {
        items.add(item);
    }

    @Override
    public void delete(Item item)
    {
        items.remove(item);
    }

    @Override
    public void update(Item item, int position)
    {
        items.set(position, item);
    }

    @Override
    public List<Item> getAll()
    {
        return items;
    }

    public void set(List<Item> items)
    {
        this.items.clear();
        this.items.addAll(items);
    }
}
