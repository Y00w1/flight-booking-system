import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PrinciplesDRYAndKISS {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static final Admin admin = new Admin();

    private static final List<Employee> employees = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the Employees Salary Calculator!");

        System.out.println("How many employees do you want to add?");
        try{
        int numberOfEmployees = Integer.parseInt(reader.readLine());

        for (int i = 0; i < numberOfEmployees; i++) {
            Employee employee = createEmployee();
            admin.addEmployee(employees, employee);
            System.out.println("Employee added successfully!");
            System.out.println(employee);
        }
        }catch (NumberFormatException e){
            System.out.println("Error : " + e.getMessage());
        }
        double sumOfSalaries = admin.sumAllEmployeesNetMonthlySalary(employees);
        admin.showAllEmployeesInfo(employees);
        System.out.println("The sum of all employees net monthly salaries is: " + sumOfSalaries);
    }

    private static Employee createEmployee() throws IOException {
        System.out.print("Enter employee name: ");
        String name = reader.readLine();

        System.out.print("Enter "+name+"'s age: ");
        int age = Integer.parseInt(reader.readLine());

        System.out.print("Enter "+name+"'s monthly base salary: ");
        Double monthlyBaseSalary = Double.parseDouble(reader.readLine());

        List<Double> adjustments = getInputAdjustments("bonuses");
        adjustments.addAll(getInputAdjustments("deductions"));

        Employee employee = new Employee(name, age, monthlyBaseSalary);

        employee.calculateMonthlySalary(adjustments);
        return employee;
    }

    public static List<Double> getInputAdjustments(String adjustmentType) throws IOException {
        List<Double> adjustments = new ArrayList<>();
        System.out.print("Enter " + adjustmentType + " (separated by commas): ");
        String input = reader.readLine();
        if (!input.isEmpty()) {
            for (String value : input.split(",")) {
                double adjustment = Double.parseDouble(value.trim());
                if (adjustmentType.equals("deductions")) {
                    adjustment = -adjustment; // negate deductions
                }
                adjustments.add(adjustment);
            }
        }
        return adjustments;
    }

    //Employee Class
    public static class Employee {
        private String name;
        private int age;
        private Double monthlyBaseSalary;
        private Double monthlyNetSalary;
        public void calculateMonthlySalary(List<Double> adjustments){
            for (Double adjustment : adjustments) {
                monthlyNetSalary += adjustment;
            }
        }
        @Override
        public String toString() {
            return "Employee: \n" +
                    "Name: " + name + "\n" +
                    "Age: " + age + "\n"+
                    "Monthly Base Salary: " + monthlyBaseSalary + "\n" +
                    "Monthly Net Salary: " + monthlyNetSalary;
        }
        public Employee(String name, int age, Double monthlyBaseSalary) {
            this.name = name;
            this.age = age;
            this.monthlyBaseSalary = monthlyBaseSalary;
            this.monthlyNetSalary = monthlyBaseSalary;
        }
        public Double getMonthlyNetSalary() {
            return monthlyNetSalary;
        }
    }

    //Admin Class
    private static class Admin {
        public void addEmployee(List<Employee> employees, Employee employee) {
            employees.add(employee);
        }
        public double sumAllEmployeesNetMonthlySalary(List<Employee> employees) {
            double sumOfSalaries = 0;
            for (Employee employee : employees) {
                sumOfSalaries += employee.getMonthlyNetSalary();
            }
            return sumOfSalaries;
        }
        public void showAllEmployeesInfo(List<Employee> employees) {
            System.out.println("---------------Employees Information---------------");
            for (Employee employee : employees) {
                System.out.println(employee);
                System.out.println("--------------------------------------------------");
            }
        }
    }
}