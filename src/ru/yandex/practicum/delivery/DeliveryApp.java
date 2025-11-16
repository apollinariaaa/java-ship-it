package ru.yandex.practicum.delivery;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);

    private static List<Parcel> allParcels = new ArrayList<>();
    private static List<Trackable> trackableParcels = new ArrayList<>();

    // Три коробки
    private static final int MAX_STANDARD_BOX_WEIGHT = 50;
    private static final int MAX_FRAGILE_BOX_WEIGHT = 30;
    private static final int MAX_PERISHABLE_BOX_WEIGHT = 40;
    private static final ParcelBox<StandardParcel> standardBox = new ParcelBox<>(MAX_STANDARD_BOX_WEIGHT);

    private static final ParcelBox<FragileParcel> fragileBox = new ParcelBox<>(MAX_FRAGILE_BOX_WEIGHT);

    private static final ParcelBox<PerishableParcel> perishableBox = new ParcelBox<>(MAX_PERISHABLE_BOX_WEIGHT);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: addParcel();            break;
                case 2: sendParcels();          break;
                case 3: calculateCosts();       break;
                case 4: reportTrackingStatus(); break;
                case 5: showBoxContents();      break;
                case 0: running = false;        break;
                default: System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\nВыберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Отчитаться о местоположении отслеживаемых посылок");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }

    private static void addParcel() {
        System.out.println("Выберите тип посылки:");
        System.out.println("1 — Стандартная");
        System.out.println("2 — Хрупкая");
        System.out.println("3 — Скоропортящаяся");
        int type = Integer.parseInt(scanner.nextLine());

        System.out.print("Описание: ");
        String desc = scanner.nextLine();

        System.out.print("Вес: ");
        int weight = Integer.parseInt(scanner.nextLine());

        System.out.print("Адрес доставки: ");
        String address = scanner.nextLine();

        System.out.print("День отправки: ");
        int day = Integer.parseInt(scanner.nextLine());

        Parcel parcel = null;

        switch (type) {
            case 1: {
                StandardParcel sp = new StandardParcel(desc, weight, address, day);
                parcel = sp;
                standardBox.addParcel(sp);
                break;
            }
            case 2: {
                FragileParcel fp = new FragileParcel(desc, weight, address, day);
                parcel = fp;
                fragileBox.addParcel(fp);
                trackableParcels.add(fp);
                break;
            }
            case 3: {
                System.out.print("Срок годности (timeToLive): ");
                int ttl = Integer.parseInt(scanner.nextLine());
                PerishableParcel pp = new PerishableParcel(desc, weight, address, day, ttl);
                parcel = pp;
                perishableBox.addParcel(pp);
                break;
            }
            default:
                System.out.println("Неверный тип.");
                return;
        }

        allParcels.add(parcel);
        System.out.println("Посылка успешно добавлена!");
    }

    private static void sendParcels() {
        for (Parcel p : allParcels) {
            p.packageItem();
            p.deliver();
        }
    }

    private static void calculateCosts() {
        int total = 0;
        for (Parcel p : allParcels) {
            total += p.calculateDeliveryCost();
        }
        System.out.println("Общая стоимость доставки: " + total);
    }

    private static void reportTrackingStatus() {
        if (trackableParcels.isEmpty()) {
            System.out.println("Нет отслеживаемых посылок.");
            return;
        }

        System.out.print("Введите новое местоположение: ");
        String location = scanner.nextLine();

        for (Trackable t : trackableParcels) {
            t.reportStatus(location);
        }
    }

    private static void showBoxContents() {
        System.out.println("Выберите коробку для просмотра:");
        System.out.println("1 — Стандартные посылки");
        System.out.println("2 — Хрупкие посылки");
        System.out.println("3 — Скоропортящиеся посылки");

        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1:
                printBoxContents(standardBox.getAllParcels());
                break;
            case 2:
                printBoxContents(fragileBox.getAllParcels());
                break;
            case 3:
                printBoxContents(perishableBox.getAllParcels());
                break;
            default:
                System.out.println("Неверный выбор.");
        }
    }

    private static void printBoxContents(List<? extends Parcel> list) {
        if (list.isEmpty()) {
            System.out.println("Коробка пуста.");
            return;
        }

        System.out.println("Содержимое коробки:");
        for (Parcel p : list) {
            System.out.println("- " + p.description + " (вес: " + p.weight + ")");
        }
    }
}
