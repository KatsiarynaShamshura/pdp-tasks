import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        Stream.iterate(1, integer -> integer += 1)
            .limit(100)
            .forEach(i -> System.out.print(i + " "));

        System.out.println();

        IntStream.rangeClosed(1, 100)
            .forEach(i -> System.out.print(i + " "));

        System.out.println();

        Stream
            .iterate(2, integer -> integer += 2)
            .limit(50)
            .forEach(i -> System.out.print(i + " "));

        System.out.println();

        IntStream.rangeClosed(1, 100)
            .filter(integer -> integer % 2 == 0)
            .forEach(i -> System.out.print(i + " "));

        System.out.println();

        Stream.generate(UUID::randomUUID)
            .limit(50)
            .collect(Collectors.toList())
            .forEach(i -> System.out.print(i + " "));

        /**
         * Task with *: choose from uuid parts that consist only of numbers.
         */
        Stream.generate(UUID::randomUUID)
            .limit(50)
            .flatMap(i -> Arrays.stream(i.toString().split("-")))
            .filter(Main::isInteger)
            .collect(Collectors.toList())
            .forEach(i -> System.out.print(i + " "));
    }

    private static boolean isInteger(String s){
        try{
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
}

