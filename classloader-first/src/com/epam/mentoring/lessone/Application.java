package com.epam.mentoring.lessone;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Application {

    public static void main(String[] args) {
        String path = args[0];
        new Thread(new CustomClassLoader(path, Application::invokeMethod)).start();
        System.out.println("runned");
    }

    private static void invokeMethod(Class<?> cla$$) {
        if (cla$$ == null) {
            return;
        }
        try {
            System.out.println("Invoke method");
            Object instance = cla$$.getConstructor().newInstance();
            Method[] methods = cla$$.getMethods();
            System.out.println("------");
            methods[0].invoke(instance);
            System.out.println("------");
            System.out.println("Finish! You are umnichka=)");
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            System.out.println("Something wrong!");
            e.printStackTrace();
        }
    }
}
