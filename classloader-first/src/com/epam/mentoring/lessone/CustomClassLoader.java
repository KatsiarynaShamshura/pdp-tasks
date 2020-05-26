package com.epam.mentoring.lessone;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class CustomClassLoader extends ClassLoader implements Runnable {

    private static final String CLASS_EXTENSION = ".class";

    private final File rootFile;
    private final Consumer<Class<?>> consumer;
    private final List<File> loadedFiles = new ArrayList<>();

    public CustomClassLoader(String filepath, Consumer<Class<?>> consumer) {
        this.rootFile = new File(filepath);
        this.consumer = consumer;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Try to find new jar file");
            File[] matchingFiles = rootFile.listFiles((dir, name) -> name.endsWith("jar"));
            if (matchingFiles != null) {
                for (File file : matchingFiles) {
                    Class<?> aClass = null;
                    if (!loadedFiles.contains(file)) {
                        System.out.println("Start loading class");
                        aClass = this.findClass(file);
                        loadedFiles.add(file);
                    }
                    consumer.accept(aClass);
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public Class<?> findClass(File jarFile) {
        return filterClasses(loadClassesFromJar(jarFile));
    }

    private List<Class<?>> loadClassesFromJar(File file) {
        try (JarFile jarFile = new JarFile(file)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            List<Class<?>> classes = new ArrayList<>();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().endsWith(CLASS_EXTENSION)) {
                    InputStream is = jarFile.getInputStream(entry);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    int readByte;
                    while ((readByte = is.read()) != -1) {
                        byteArrayOutputStream.write(readByte);
                    }
                    byte[] bytes = byteArrayOutputStream.toByteArray();
                    String className = replaceName(entry.getName());
                    if (findLoadedClass(className) == null) {
                        Class<?> newClass = defineClass(className, bytes, 0, bytes.length);
                        System.out.println("Class with name: " + className + " loaded");
                        classes.add(newClass);
                    } else {
                        System.out.println("Class with name: " + className + " already loaded");
                    }
                }
            }
            return classes;
        } catch (IOException e) {
            System.out.println("Something wrong!");
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private Class<?> filterClasses(List<Class<?>> classes) {
        for (Class<?> clazz : classes) {
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> interf : interfaces) {
                if (ICommon.class.getName().equals(interf.getName())) {
                    return clazz;
                }
            }
        }
        return null;
    }

    private String replaceName(String name) {
        return name.replace('/', '.').substring(0, name.lastIndexOf('.'));
    }
}
