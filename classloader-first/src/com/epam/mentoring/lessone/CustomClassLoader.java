package com.epam.mentoring.lessone;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class CustomClassLoader extends ClassLoader implements Runnable {

    private static final String CLASS_EXTENSION = ".class";

    private final File rootFile;
    private final Consumer<Class<?>> consumer;
    private final List<File> loadedFiles = new ArrayList<>();

    public CustomClassLoader(String filePath, Consumer<Class<?>> consumer) {
        this.rootFile = new File(filePath);
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
        return loadClassesFromJar(jarFile);
    }

    private Class<?> loadClassesFromJar(File file) {
        try (JarFile jarFile = new JarFile(file)) {
            Optional<? extends Class<?>> clazz = Collections.list(jarFile.entries()).stream()
                .filter(entry -> entry.getName().endsWith(CLASS_EXTENSION))
                .map(entry -> proceedEntry(jarFile, entry))
                .filter(Objects::nonNull)
                .filter(this::filterClasses)
                .findFirst();
            return clazz.orElse(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Class<?> proceedEntry(JarFile jarFile, JarEntry entry) {
        byte[] bytes = getBytes(jarFile, entry);
        String className = replaceName(entry.getName());
        if (findLoadedClass(className) == null) {
            Class<?> newClass = defineClass(className, bytes, 0, bytes.length);
            System.out.println("class with name: " + className + " loaded");
            return newClass;
        } else {
            System.out.println("class with name: " + className + " already loaded");
        }
        return null;
    }

    private boolean filterClasses(Class<?> clazz) {
        Class<?>[] interfaces = clazz.getInterfaces();
        for (Class<?> interf : interfaces) {
            if (ICommon.class.getName().equals(interf.getName())) {
                return true;
            }
        }
        return false;
    }

    private byte[] getBytes(JarFile jarFile, JarEntry entry) {
        try (InputStream is = jarFile.getInputStream(entry)) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int readByte;
            while ((readByte = is.read()) != -1) {
                byteArrayOutputStream.write(readByte);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private String replaceName(String name) {
        return name.replace('/', '.').substring(0, name.lastIndexOf('.'));
    }
}
