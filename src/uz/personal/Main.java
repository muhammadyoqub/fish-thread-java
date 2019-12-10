package uz.personal;

import java.util.*;


public class Main {
    //console colored
    static final String RED = "\u001B[31m";
    static final String GREEN = "\u001B[32m";
    static final String BLUE = "\u001B[34m";
    private static final String WHITE = "\u001B[37m";

    //initial fishes in the aquarium;
    static List<Fish> aquarium = new ArrayList<>(Arrays.asList(
            new Fish(Gender.MALE),
            new Fish(Gender.FEMALE)
    ));

    // start point
    public static void main(String[] args) {
        Meeting meeting = new Meeting();
        meeting.start();
    }

    // prints fishes count
    static void countFish() {
        System.out.printf("%sTotal fishes: %d\n%s", WHITE, Main.aquarium.size(), WHITE);
    }

}

class Meeting extends Thread {
    @Override
    public void run() {
        // timer time
        int timer = (int) (Math.random() * 6000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                int first;
                int second;
                do {
                    first = new Random().nextInt(Main.aquarium.size());
                    second = new Random().nextInt(Main.aquarium.size());
                } while (Main.aquarium.get(first).getGender().equals(Main.aquarium.get(second).getGender()));
                System.out.printf("%s%s fish named %d  met with %s fish named %d\n%s",
                        Main.GREEN, Main.aquarium.get(first).getGender().label, first,
                        Main.aquarium.get(second).getGender().label, second, Main.GREEN);
                new Thread(new Born()).start();

            }
        }, timer, timer);


    }
}

class Born implements Runnable {

    public void run() {
        Fish fish = new Fish();
        Main.aquarium.add(fish);
        System.out.printf("%s%s fish named %d was born%s\n",
                Main.BLUE, fish.getGender().label, fish.getNumber(), Main.BLUE);
        Main.countFish();
        new DeadThread(fish).start();
    }
}

class DeadThread extends Thread {
    private Fish fish;

    DeadThread(Fish fish) {
        this.fish = fish;
    }

    @Override
    public void run() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.printf("%s%s fish named %d dead%s\n",
                        Main.RED, fish.getGender().label, fish.getNumber(), Main.RED);
                Main.aquarium.remove(fish);
                Main.countFish();
            }
        }, fish.getLifetime());
    }
}

class Fish {
    private Integer number;

    private Gender gender;

    private Integer lifetime;

    private static int count = 0;


    Fish(Gender gender) {
        number = count++;
        this.gender = gender;
        lifetime = (int) (Math.random() * 6500);
    }

    Fish() {
        number = count++;
        gender = Math.random() < 0.5 ? Gender.MALE : Gender.FEMALE;
        lifetime = (int) (Math.random() * 6500);
    }

    Integer getNumber() {
        return number;
    }

    Gender getGender() {
        return gender;
    }


    Integer getLifetime() {
        return lifetime;
    }
}

enum Gender {
    MALE("male"),
    FEMALE("female");

    public final String label;

    Gender(String label) {
        this.label = label;
    }
}

