package com.learn.spring.l01_without_spring;

public class WithoutSpringDemo {

    public static void main(String[] args) {
        System.out.println("--- Tight coupling: the door picks its own alarm ---");
        System.out.println(new TightlyCoupledDoor().open());

        System.out.println("\n--- Dependency injection by hand: WE are the container ---");
        // This "wiring" code is exactly the job Spring takes over from lesson 02 onwards.
        Alarm sound = new SoundAlarm();
        Alarm visual = new VisualAlarm();
        Door frontDoor = new Door(sound);
        Door backDoor = new Door(visual);

        System.out.println(frontDoor.open());
        System.out.println(backDoor.open());

        System.out.println("\n--- Testing is easy: pass a fake alarm ---");
        Door testDoor = new Door(new Alarm() {
            public String activate() { return "fake-activate"; }
            public String deactivate() { return "fake-deactivate"; }
        });
        System.out.println(testDoor.open());
    }
}
