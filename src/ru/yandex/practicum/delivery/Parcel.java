package ru.yandex.practicum.delivery;

public abstract class Parcel {
    protected String description;
    protected int weight;
    protected String deliveryAddress;
    protected int sendDay;

    public Parcel(String description, int weight, String deliveryAddress, int sendDay) {
        this.description = description;
        this.weight = weight;
        this.deliveryAddress = deliveryAddress;
        this.sendDay = sendDay;
    }

    // Метод упаковки — базовая часть "Посылка <<XXX>> упакована"
    protected void packBase() {
        System.out.println("Посылка <<" + description + ">> упакована");
    }

    // Будет реализован по-разному в наследниках
    public abstract void packageItem();

    public void deliver() {
        System.out.println("Посылка <<" + description + ">> доставлена по адресу " + deliveryAddress);
    }

    // Абстрактная базовая стоимость для типов
    protected abstract int getBaseCost();

    public int calculateDeliveryCost() {
        return weight * getBaseCost();
    }
}

