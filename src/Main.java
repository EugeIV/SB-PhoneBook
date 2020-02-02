import java.security.KeyStore;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Main
{
    private static Scanner scanner = new Scanner(System.in);
    private static TreeMap<String, String> phoneBook = new TreeMap<>();
    private static final String REGEX_NUM = "^\\+?[78][-\\(]?\\d{3}\\)?-?\\d{3}-?\\d{2}-?\\d{2}$";
    private static final String REGEX_NAME = "[A-Za-zА-Яа-яЁё]+(\\s+[A-Za-zА-Яа-яЁё]+)?";
    private static final String REGEX_CORRECT_NUM = "[^0-9\\+]";

    public static void main(String[] args)
    {
        while (true)
        {
            String nextStr = scanner.nextLine().toLowerCase();

            if (nextStr.equalsIgnoreCase("LIST"))
            {
                printMap(phoneBook);
                continue;
            }

            if (nextStr.matches(REGEX_NUM))
            {
                searchNum(phoneBook, nextStr);
            }

            else if (nextStr.matches(REGEX_NAME))
            {
                searchName(phoneBook, nextStr);
            }

            else
            {
                System.out.println("Проверьте корректность ввода");
            }
        }
    }

    private static void searchName(Map<String, String> phoneBook, String nextStr) // ищем номер по имени, если нет - добавляем
    {
        if (phoneBook.containsKey(nextStr.toLowerCase()))
        {
            System.out.println(nextStr + " => +7" + phoneBook.get(nextStr));
        }
        else
        {
            String phoneNumber = requestInput(REGEX_NUM, "Введите номер");
            String pN = phoneNumber.replaceAll(REGEX_CORRECT_NUM, "");
            if (numberInBook(phoneBook, correctEntry(pN)))
            {
                System.out.println("В базе есть данный номер: " + outputNumber(phoneBook, correctEntry(pN)));
            }
            else
            {
                phoneBook.put(nextStr, correctEntry(pN));
                System.out.println("Контакт добавлен");
            }
        }
    }

    private static void searchNum(Map<String, String> phoneBook, String phoneNumber) // проверяем наличие номере в базе, если нет, то вводим Имя и сохраняем
    {
        String pN = phoneNumber.replaceAll(REGEX_CORRECT_NUM, "");
        if (numberInBook(phoneBook, correctEntry(pN)))
        {
            System.out.println("В базе есть данный номер: " + outputNumber(phoneBook, correctEntry(pN)));
        }
        else
        {
            String name = requestInput(REGEX_NAME, "Введите имя");
            phoneBook.put(name, correctEntry(pN));
            System.out.println("Контакт добавлен");
        }
    }

    private static void printMap(Map<String, String> map) // вывод всей телефонной книги
    {
        for (String key : map.keySet())
        {
            System.out.println(key + " => +7" + map.get(key));
        }
    }

    private static Boolean numberInBook(Map<String, String> map, String phoneNumber) // наличие номера в базе
    {
        for (String name : map.keySet())
        {
            if (map.get(name).equals(phoneNumber))
            {
                return true;
            }
        }
        return false;
    }

    private static String correctEntry(String phoneNumber) // метод корректного ввода номера
    {
        if (phoneNumber.length() == 11)
        {
            return phoneNumber.substring(1);
        }

        else if (phoneNumber.length() == 12)
        {
            return phoneNumber.substring(2);
        }
        else
        {
            return "Проверьте правильность телефонного номера";
        }
    }

    private static String requestInput(String regex, String invitation) // общий метод ввода имя/номер
    {
        String input = "";
        while (!input.matches(regex)) {
            System.out.println(invitation);
            input = new Scanner(System.in).nextLine();
        }
        return input;
    }

    private static String outputNumber(Map<String, String> phoneBook, String phoneNumber) // метод вывода имя => номер из базы
    {
        for (String name : phoneBook.keySet())
        {
            if (phoneBook.get(name).equals(phoneNumber))
            {
                return name + " => +7" + phoneNumber;
            }
        }
        return "";
    }
}
