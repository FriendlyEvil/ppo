package ru.friendlyevil.application;


import ru.friendlyevil.application.a.A;

public class Application {
    public void run() {
        subMethod();
    }

    public void subMethod() {
        new A().a();
    }
}
