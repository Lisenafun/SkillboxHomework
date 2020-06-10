import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    private static String staffFile = "data/staff.txt";
    private static String dateFormat = "dd.MM.yyyy";

    public static void main(String[] args) {

        ArrayList<Employee> staff = loadStaffFromFile();

        //Сортировка по алфавиту и зарплате
        staff.stream().sorted(Comparator.comparing(Employee::getName).thenComparing(Employee::getSalary)).forEach(System.out::println);

        //Вывод сотрудника с самой высокой зарплатой, получившего работу в 2017 году
        staff.stream().filter(employee -> {
            Calendar cal = Calendar.getInstance();
            cal.setTime(employee.getWorkStart());
            return cal.get(Calendar.YEAR) == 2017;
        }).max(Comparator.comparing(Employee::getSalary)).ifPresent(System.out::println);
    }

    private static ArrayList<Employee> loadStaffFromFile() {
        ArrayList<Employee> staff = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(staffFile));
            for(String line : lines) {
                String[] fragments = line.split("\t");
                if(fragments.length != 3) {
                    System.out.println("Wrong line: " + line);
                    continue;
                }
                staff.add(new Employee(fragments[0], Integer.parseInt(fragments[1]), (new SimpleDateFormat(dateFormat)).parse(fragments[2])));
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return staff;
    }
}