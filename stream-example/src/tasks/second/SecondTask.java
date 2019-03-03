package tasks.second;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SecondTask {

    public static void main(String[] args) {

        List<User> items = new ArrayList<>();

        items.add(new User("KatsiarynaShamshura", "Katyuha", "12345"));
        items.add(new User("AliaksandrShynkevich", "Sanya", "54321"));

        Field[] fields = User.class.getDeclaredFields();
        Method[] methods = User.class.getMethods();

        IntStream.range(0, fields.length)
            .boxed()
            .collect(Collectors.toMap(index -> fields[index].getName(),
                index -> {
                    List<String> val = new ArrayList<>();
                    for (User user : items) {
                        for (Method method : methods) {
                            if (method.toString()
                                .toLowerCase()
                                .contains(fields[index].getName().toLowerCase())) {
                                try {
                                    val.add((String) method.invoke(user));
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    return val;
                }))
            .forEach((s, method) -> System.out.println(s + " " + method));

        IntStream.range(0, fields.length)
            .boxed()
            .peek(index -> fields[index].setAccessible(true))
            .collect(Collectors.toMap(index -> fields[index].getName(),
                index -> {
                    List<String> val = new ArrayList<>();
                    for (User user : items) {
                        try {
                            val.add((String) fields[index].get(user));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    return val;
                }))
            .forEach((s, method) -> System.out.println(s + " " + method));
    }
}
