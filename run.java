import java.time.LocalDate;
import java.util.*;

public class Main {

    public static boolean checkCapacity(int maxCapacity, List<Map<String, String>> guests) {
        List<LocalDate> checkIns = new ArrayList<>();
        List<LocalDate> checkOuts = new ArrayList<>();

        for (Map<String, String> guest : guests) {
            checkIns.add(LocalDate.parse(guest.get("check-in")));
            checkOuts.add(LocalDate.parse(guest.get("check-out")));
        }

        Collections.sort(checkIns);
        Collections.sort(checkOuts);

        int i = 0, j = 0, occupied = 0;

        while (i < guests.size()) {
            if (checkIns.get(i).isBefore(checkOuts.get(j))) {
                occupied++;
                if (occupied > maxCapacity)
                    return false;
                i++;
            } else {
                occupied--;
                j++;
            }
        }

        return true;
    }

    private static Map<String, String> parseJsonToMap(String json) {
        Map<String, String> map = new HashMap<>();
        json = json.substring(1, json.length() - 1);
        String[] pairs = json.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":", 2);
            String key = keyValue[0].trim().replace("\"", "");
            String value = keyValue[1].trim().replace("\"", "");
            map.put(key, value);
        }
        return map;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String line;

        // maxCapacity с пропуском пустых строк
        do {
            line = scanner.nextLine().trim();
        } while (line.isEmpty());
        int maxCapacity = Integer.parseInt(line);

        // n с пропуском пустых строк
        do {
            line = scanner.nextLine().trim();
        } while (line.isEmpty());
        int n = Integer.parseInt(line);

        List<Map<String, String>> guests = new ArrayList<>();

        int readGuests = 0;
        while (readGuests < n) {
            line = scanner.nextLine().trim();
            if (line.isEmpty())
                continue;

            guests.add(parseJsonToMap(line));
            readGuests++;
        }

        boolean result = checkCapacity(maxCapacity, guests);

        System.out.println(result ? "True" : "False");

        scanner.close();
    }
}
