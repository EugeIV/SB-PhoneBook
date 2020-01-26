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
    private static boolean correct = true;

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
        if (phoneBook.containsKey(nextStr))
        {
            System.out.println(nextStr + " => +7" + phoneBook.get(nextStr));
        }
        else
        {
            System.out.print("Контакт не найден. Добавьте его номер: ");
            while (correct)
            {
                String phoneNumber = scanner.nextLine();
                setInfo(nextStr, phoneNumber);
            }
        }
    }

    private static void searchNum(Map<String, String> map, String phoneNumber) // проверяем наличие номере в базе, если нет, то вводим Имя и сохраняем
    {
        String pN = phoneNumber.replaceAll(REGEX_CORRECT_NUM, "");
        if (pN.length() == 11)
        {
            pN = pN.substring(1);
        }
        else if (pN.length() == 12)
        {
            pN = pN.substring(2);
        }
        else
        {
            System.out.println("Проверьте правильность телефонного номера");
        }
        System.out.println(getNameFromNumber(map, pN));
    }

    private static void printMap(Map<String, String> map) // вывод всей телефонной книги
    {
        for (String key : map.keySet())
        {
            System.out.println(key + " => +7" + map.get(key));
        }
    }

    private static String getNameFromNumber(Map<String, String> map, String phoneNumber) // исключаем дублирование номеров
    {
        for (String name : map.keySet())
        {
            if (map.get(name).equals(phoneNumber))
            {
                return name + " => +7" + phoneNumber;
            }
        }
        System.out.print("В базе нет такого номера. Введите имя, чтобы добавить: ");
        String name = scanner.nextLine();
        phoneBook.put(name, phoneNumber);
        return "Контакт добавлен";
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
            if (numberInBook(phoneBook, phoneNumber.substring(1)))
            {
                System.out.println("В базе есть такой номер: " + getNameFromNumber(phoneBook, phoneNumber.substring(1)));
            }
            else
            {
                return phoneNumber.substring(1);
            }
        }
        else if (phoneNumber.length() == 12)
        {
            if (numberInBook(phoneBook, phoneNumber.substring(2)))
            {
                System.out.println("В базе есть такой номер: " + getNameFromNumber(phoneBook, phoneNumber.substring(2)));
            }
            else
            {
                return phoneNumber.substring(2);
            }
        }
        else
        {
            return "Проверьте правильность телефонного номера";
        }
        return "";
    }

    private static void setInfo(String name, String phoneNumber)
    {
        if (phoneNumber.matches(REGEX_NUM))
        {
            correct = false;
            String pN = phoneNumber.replaceAll(REGEX_CORRECT_NUM, ""); //приводим к единому формату для БД
            phoneBook.put(name, correctEntry(pN));
            System.out.println("Контакт добавлен");
        }
        else
        {
            System.out.println("Номер некорректен. Введите номер заново: ");
        }
    }
}
