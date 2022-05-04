package com.ltp.globalsuperstore.service;

import com.ltp.globalsuperstore.pojo.Item;
import com.ltp.globalsuperstore.repository.StoreRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;
    @InjectMocks
    private StoreService storeService;

    @Test
    public void getIndexFromId() {
        List<Item> items = Arrays.asList(
                new Item("Furniture", "Lama", 500.00, 40.05, null),
                new Item("Office Supps", "Alpaka", 350.00, 25.55, null));
        when(storeRepository.getItems()).thenReturn(items);
        int actualIndex = storeService.getIndexFromId(items.get(0).getId());
        assertEquals(0, actualIndex);
    }

    @Test
    public void getItemFromId() {
        List<Item> items = Arrays.asList(
                new Item("Furniture", "Lama", 500.00, 40.05, null),
                new Item("Office Supps", "Alpaka", 350.00, 25.55, null));
        when(storeRepository.getItems()).thenReturn(items);
        when(storeRepository.getItem(0)).thenReturn(items.get(0));
        Item actualItem = storeService.getItemFromId(items.get(0).getId());
        assertEquals(items.get(0), actualItem);
    }
}