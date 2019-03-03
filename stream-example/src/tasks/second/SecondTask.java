package tasks.second;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class SecondTask {

    public static void main(String[] args)
        throws NoSuchFieldException, InvocationTargetException, IllegalAccessException {

        Map<String, DayOfWeek> map = new HashMap<>();

        for (int i = 0; i < 60; i++) {
            map.put(UUID.randomUUID().toString(), LocalDate.now().plusDays(i).getDayOfWeek());
        }

        List<DayOfWeek> dayOfScrum = map.values().stream()
            .limit(60)
            .filter(
                objectObjectHashMap -> objectObjectHashMap.getValue() == DayOfWeek.MONDAY.getValue() ||
                    objectObjectHashMap.getValue() == DayOfWeek.WEDNESDAY.getValue() ||
                    objectObjectHashMap.getValue() == DayOfWeek.FRIDAY.getValue())
            .collect(Collectors.toList());

        System.out.println(dayOfScrum);

        List<DayOfWeek> dayOfTea = map.values().stream()
            .limit(60)
            .filter(
                objectObjectHashMap -> objectObjectHashMap.getValue() == DayOfWeek.MONDAY.getValue() ||
                    objectObjectHashMap.getValue() == DayOfWeek.TUESDAY.getValue() ||
                    objectObjectHashMap.getValue() == DayOfWeek.WEDNESDAY.getValue() ||
                    objectObjectHashMap.getValue() == DayOfWeek.THURSDAY.getValue() ||
                    objectObjectHashMap.getValue() == DayOfWeek.FRIDAY.getValue())
            .collect(Collectors.toList());

        System.out.println(dayOfTea);


        List<User> items = new ArrayList<>();

        items.add(new User("KatsiarynaShamshura", "Katyuha", "12345"));
        items.add(new User("AliaksandrShynkevich", "Sanya", "54321"));

        Map<String, List<String>> res = new HashMap<>();
        Field[] fields = User.class.getDeclaredFields();
        Method[] methods = User.class.getMethods();

        items.stream().collect(Collectors.groupingBy(o ->
                Arrays.toString(o.getClass().getDeclaredFields()),
            Collectors.mapping(User::getUsername, Collectors.toList())));

        //System.out.println(map);

        for (Field f : fields) {
            List<String> val = new ArrayList<>();
            for (User user : items) {
                for (Method method : methods) {
                    if (method.toString().contains(f.getName())) {
                        method.invoke(user);
                        val.add((String) method.invoke(user));
                    }
                }
            }
            res.put(f.getName(), val);
        }

        System.out.println(res);
    }
}
