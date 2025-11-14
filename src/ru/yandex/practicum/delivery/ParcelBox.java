package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;

public class ParcelBox<T extends Parcel> {

    private int maxWeight;
    private List<T> parcels = new ArrayList<>();

    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void addParcel(T parcel) {
        int currentWeight = 0;
        for (T p : parcels) currentWeight += p.weight;

        if (currentWeight + parcel.weight > maxWeight) {
            System.out.println("Невозможно добавить посылку <<" + parcel.description +
                    ">>: превышен максимальный вес коробки (" + maxWeight + ").");
            return;
        }

        parcels.add(parcel);
        System.out.println("Посылка <<" + parcel.description + ">> добавлена в коробку.");
    }

    public List<T> getAllParcels() {
        return parcels;
    }
}

