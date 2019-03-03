package tasks.second;

import static java.util.stream.Collectors.toList;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FirstTask {

    public static void main(String[] args) {

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
            .collect(toList());

        System.out.println(dayOfScrum);

        List<DayOfWeek> dayOfTea = map.values().stream()
            .limit(60)
            .filter(
                objectObjectHashMap -> objectObjectHashMap.getValue() == DayOfWeek.MONDAY.getValue() ||
                    objectObjectHashMap.getValue() == DayOfWeek.TUESDAY.getValue() ||
                    objectObjectHashMap.getValue() == DayOfWeek.WEDNESDAY.getValue() ||
                    objectObjectHashMap.getValue() == DayOfWeek.THURSDAY.getValue() ||
                    objectObjectHashMap.getValue() == DayOfWeek.FRIDAY.getValue())
            .collect(toList());

        System.out.println(dayOfTea);
    }
}
