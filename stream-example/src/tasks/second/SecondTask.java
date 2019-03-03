package tasks.second;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SecondTask {

    public static void main(String[] args) throws NoSuchFieldException {
        List<User> items = new ArrayList<>();

        items.add(new User("KatsiarynaShamshura", "Katyuha", "12345"));
        items.add(new User("AliaksandrShynkevich", "Sanya", "54321"));

        Map<String, List<String>> map = new HashMap<>();

        items.stream().collect(Collectors.groupingBy(o ->
            Arrays.toString(o.getClass().getDeclaredFields()), Collectors.mapping(User::getUsername, Collectors.toList())));

        //System.out.println(map);

        Field[] fields = User.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field f = fields[i];
            List<String> val = new ArrayList<>();
            for(User user: items) {
                if(f.getName().equals("password")) {
                    val.add(user.getPassword());
                } else if(f.getName().equals("displayName")){
                    val.add(user.getDisplayName());
                } else {
                    val.add(user.getUsername());
                }
            }
            map.put(f.getName(), val);
        }

        System.out.println(map);
    }
}
