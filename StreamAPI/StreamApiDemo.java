// ASSIGNMENT:
// Find the highest salary paid employee
// Find how many male & female employees working in company (numbers)
// Total expense for the company department wise
// Who is the top 5 senior employees in the company
// Find only the names who all are managers
// Hike the salary by 20% for everyone except manager
// Find the total number of employees

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.*;

class Employee {
    String name;
    int age;
    String gender;
    double salary;
    String designation;
    String department;
    public Employee(String n, int a, String g, double s, String des, String dep) {
        name = n;
        age = a;
        gender = g;
        salary = s;
        designation = des;
        department = dep;
    }
    public Double getSalary() {
        return salary;
    }
    public String getGender() {
        return gender;
    }
    public String toString() {
        return name+", "+age+", "+gender+", "+salary+", "+designation+", "+department+"\n";
    }
}

public class StreamApiDemo {
    public static void main(String args[]) {
        List<Employee> ls = new ArrayList<Employee>();

        ls.add(new Employee("Amit",   28, "Male",   45000.00, "Software Engineer", "IT"));
        ls.add(new Employee("Neha",   32, "Female", 60000.00, "Team Lead",          "IT"));
        ls.add(new Employee("Rahul",  26, "Male",   38000.00, "HR Executive",       "HR"));
        ls.add(new Employee("Sneha",  35, "Female", 72000.00, "Project Manager",    "Operations"));
        ls.add(new Employee("Karan",  40, "Male",   85000.00, "Product Owner",      "Product"));
        ls.add(new Employee("Anita",  29, "Female", 49000.00, "Manager",       "HR"));
        ls.add(new Employee("Vikram", 34, "Male",   68000.00, "Team Lead",          "Operations"));
        ls.add(new Employee("Pooja",  27, "Female", 46000.00, "Software Engineer",  "IT"));
        ls.add(new Employee("Rohit",  31, "Male",   65000.00, "Manager",  "Product"));
        ls.add(new Employee("Meena",  38, "Female", 77000.00, "Project Manager",    "Operations"));
        ls.add(new Employee("Suresh", 45, "Male",   90000.00, "Manager",      "IT"));
        ls.add(new Employee("Kavya",  24, "Female", 36000.00, "HR Executive",       "HR"));
        ls.add(new Employee("Arjun",  33, "Male",   64000.00, "Team Lead",          "IT"));
        ls.add(new Employee("Nina",   28, "Female", 48000.00, "Software Engineer",  "Operations"));
        ls.add(new Employee("Manoj",  41, "Male",   82000.00, "Project Manager",    "Product"));
        ls.add(new Employee("Isha",   26, "Female", 39000.00, "HR Executive",       "HR"));
        ls.add(new Employee("Deepak", 36, "Male",   71000.00, "Team Lead",          "Operations"));
        ls.add(new Employee("Rina",   30, "Female", 53000.00, "Software Engineer",  "IT"));
        ls.add(new Employee("Ajay",   39, "Male",   88000.00, "Product Owner",      "Product"));
        ls.add(new Employee("Tina",   25, "Female", 37000.00, "HR Executive",       "HR"));

        Employee result = ls.stream().max(Comparator.comparingDouble(Employee::getSalary)).orElse(null);
        System.out.println("\n1. Highest paid Employee: "+result);

        // Map<Boolean, Long> result1 = ls.stream().collect(Collectors.partitioningBy(e-> e.gender.equals("Male"), Collectors.counting()));
        // System.out.println("\n2. Gender wise Counting: "+result1);

        Map<String, Long> result2 = ls.stream().collect(Collectors.groupingBy(e->e.gender, Collectors.counting()));
        System.out.println("2. Gender wise Counting: "+result2);

        Map<String, Double> result3 = ls.stream().collect(Collectors.groupingBy(e->e.department, Collectors.summingDouble(e->e.salary)));
        System.out.println("\n3. Department wise expense: "+result3);

        List<Employee> result4 = ls.stream().sorted((e1, e2) -> e2.age - e1.age).limit(5).toList();
        System.out.println("\n4. Top 5 senior employees: \n"+result4);

        Predicate<Employee> p1 = (e) -> e.designation.equals("Manager");

        List<Employee> result5 = ls.stream().filter(p1).toList();
        System.out.println("\n5. All Managers: "+result5);

        List<Employee> result6 = ls.stream().filter(p1.negate()).toList();
        
        result6.forEach(e -> e.salary += (e.salary*0.2));
        System.out.println("\n6. Raise 20% of Employees except Managers: \n"+result6);
        
        // Long result7 = ls.stream().collect(Collectors.counting());
        int s = ls.size();
        System.out.println("\n7. Total Number of Employees: "+s);
    }
}