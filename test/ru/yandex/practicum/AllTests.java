package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.FragileParcel;
import ru.yandex.practicum.delivery.ParcelBox;
import ru.yandex.practicum.delivery.PerishableParcel;
import ru.yandex.practicum.delivery.StandardParcel;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AllTests {

    @Test
    public void testStandardParcelCost() {
        StandardParcel p = new StandardParcel("doc", 10, "A", 1);
        assertEquals(20, p.calculateDeliveryCost());
    }

    @Test
    public void testFragileParcelCost() {
        FragileParcel p = new FragileParcel("glass", 5, "B", 2);
        assertEquals(20, p.calculateDeliveryCost());
    }

    @Test
    public void testPerishableParcelCost() {
        PerishableParcel p = new PerishableParcel("milk", 8, "C", 3, 2);
        assertEquals(24, p.calculateDeliveryCost());
    }

    @Test
    public void testZeroWeightCost() {
        StandardParcel p = new StandardParcel("zero", 0, "A", 1);
        assertEquals(0, p.calculateDeliveryCost());
    }

    @Test
    public void testNotExpired() {
        PerishableParcel p = new PerishableParcel("fruit", 5, "A", 5, 3);
        assertFalse(p.isExpired(7));  // 5+3 >= 7 → не испорчена
    }

    @Test
    public void testExpired() {
        PerishableParcel p = new PerishableParcel("meat", 5, "B", 2, 1);
        assertTrue(p.isExpired(5)); // 2+1 < 5 → испорчена
    }

    @Test
    public void testBoundaryNotExpired() {
        PerishableParcel p = new PerishableParcel("cheese", 5, "C", 10, 5);
        assertFalse(p.isExpired(15)); // 10+5 == 15 → не испорчена
    }

    @Test
    public void testAddParcelNormal() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(10);
        StandardParcel p = new StandardParcel("book", 4, "A", 1);

        box.addParcel(p);

        List<StandardParcel> items = box.getAllParcels();
        assertEquals(1, items.size());
        assertEquals("book", items.get(0).description);
    }

    @Test
    public void testAddParcelExceedsLimit() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(5);

        StandardParcel p1 = new StandardParcel("toy", 3, "A", 1);
        StandardParcel p2 = new StandardParcel("heavy", 4, "B", 2);

        box.addParcel(p1);
        box.addParcel(p2); // превышение веса — добавиться не должен

        assertEquals(1, box.getAllParcels().size());
    }

    @Test
    public void testAddParcelAtBoundary() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(7);

        StandardParcel p1 = new StandardParcel("item1", 3, "A", 1);
        StandardParcel p2 = new StandardParcel("item2", 4, "B", 2);

        box.addParcel(p1);
        box.addParcel(p2); // 3+4 == 7 → допустимо

        assertEquals(2, box.getAllParcels().size());
    }
}

