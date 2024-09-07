import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() ->
            {
                String rlrfr = generateRoute("RLRFR", 100);
                int count = (int) rlrfr.chars().filter(ch -> ch == 'R').count();

                synchronized (sizeToFreq) {
                    if (!sizeToFreq.containsKey(count)) {
                        sizeToFreq.put(count, 1);
                    } else {
                        sizeToFreq.put(count, sizeToFreq.get(count) + 1);
                    }
                }
            }
            );
            thread.start();
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.join(); // зависаем, ждём когда поток объект которого лежит в thread завершится
        }

        Integer keyMax = sizeToFreq.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
        System.out.println("Самое частое количество повторений " + keyMax + " (встретилось " + sizeToFreq.get(keyMax) + " раз)");
        sizeToFreq.forEach((key, value) -> System.out.println(key + " (" + value + " раз)"));
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}