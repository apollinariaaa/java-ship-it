package ru.yandex.practicum.delivery;


public class PerishableParcel extends Parcel {

    private static final int BASE_COST = 3;
    private int timeToLive;

    public PerishableParcel(String description, int weight, String deliveryAddress, int sendDay, int timeToLive) {
        super(description, weight, deliveryAddress, sendDay);
        this.timeToLive = timeToLive;
    }

    @Override
    public void packageItem() {
        packBase();
    }

    @Override
    protected int getBaseCost() {
        return BASE_COST;
    }

    public boolean isExpired(int currentDay) {
        return sendDay + timeToLive < currentDay;
    }


}

