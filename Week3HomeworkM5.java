public class Week3HomeworkM5 {

    // ==========================================
    // M5. INSTANCE VS STATIC
    // ==========================================

    static class Employee {

        // Instance fields
        String empName;
        double salary;

        // Static field shared by all employees
        static String companyName =
                "Bright Horizon Technologies";

        // Static counter
        static int employeeCount = 0;


        // Constructor
        Employee(
                String empName,
                double salary) {

            this.empName = empName;
            this.salary = salary;

            employeeCount++;
        }


        // Static method
        static void printCompanyInfo() {

            System.out.println(companyName);

            System.out.println(
                    "Employees on record: "
                            + employeeCount
            );
        }
    }


    // ==========================================
    // MAIN METHOD
    // ==========================================

    public static void main(String[] args) {

        System.out.println(
                "========== M5: INSTANCE VS STATIC =========="
        );

        Employee employee1 =
                new Employee(
                        "Ravi",
                        50000
                );

        Employee employee2 =
                new Employee(
                        "Priya",
                        60000
                );

        Employee employee3 =
                new Employee(
                        "Karthik",
                        55000
                );

        // Call static method using CLASS NAME
        Employee.printCompanyInfo();
    }
}