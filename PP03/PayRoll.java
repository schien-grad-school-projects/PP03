package PP03;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PayRoll {

    private static int noRecords = 0;
    private String fileName;
    private PayRecord[] payRecords;
    private double totalNetPay;
    private double avgNetPay;

    private ArrayList<Employee> employeeArrayList = new ArrayList<>();

    public PayRoll(String fileName, int n) {
        this.fileName = fileName;
        this.payRecords = new PayRecord[n];

        try {
            readFromFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    public void readFromFile() throws FileNotFoundException, ParseException {

        // read the initial data from PayRoll file to create the full
        // pay records with gross pay, taxes, and net pay, and then store it in PayRecord.txt file
        File file = new File(fileName);
        Scanner input = new Scanner(file);

		int num = 0;
		ArrayList employees = new ArrayList();
        while (input.hasNext()) {
            String line = input.nextLine();
            String[] parts = line.split(", ");
            String type = parts[0].trim();
            if (type.equals("employee")) {
                employees.add(createEmployee(parts));
            } else {

                createPayRecord(parts, employees);
            }
			num++;
        }

        // Close the file
        input.close();
    }


    public void writeToFile() throws FileNotFoundException {
        // write employees' pay records to the PayRecord.txt file, it should add employee pay record to the current file data
		File file = new File("PayRecord.txt");
		PrintWriter output = new PrintWriter(file);

		for (int i = 0; i < payRecords.length; i++)
			output.println(payRecords[i].toString());

		output.close();
		JOptionPane.showMessageDialog(null, "Done Writing to file PayRecord.txt");
    }

    public Employee createEmployee(String[] parts) {
        // creates a new Employee object and add it to the employees array, you need to pass parameters to this method
        String statusString = parts[4].trim();
        Status status = Status.valueOf(getEnumName(statusString));

        int eId = Integer.parseInt(parts[1]);

        return new Employee(parts[2], parts[3], getAddress(parts), eId, status);
    }

    private String getEnumName(String name) {
        switch (name.toUpperCase()) {
            case "FULLTIME":
                return "FullTime";
            case "HOURLY":
                return "Hourly";
            default:
                throw new IllegalArgumentException("Invalid status string: " + name);
        }
    }

    private Address getAddress(String[] parts) {
        String street = parts[5];
        int houseNumber = Integer.parseInt(parts[6]);
        String city = parts[7];
        String state = parts[8];
        int zipCode = Integer.parseInt(parts[9]);

        // Create and return the Address object
        return new Address(street, houseNumber, city, state, zipCode);
    }

    public void createPayRecord(String[] parts, ArrayList<Employee> employees) throws ParseException {
        // creates a new PayRecord for an Employee object and add it to  the payRecords array, you need to pass parameters to this method
        int rID = Integer.parseInt(parts[1]);
        int eID = Integer.parseInt(parts[2]);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date pStartDate = dateFormat.parse(parts[6]);
        Date pEndDate = dateFormat.parse(parts[7]);

		Employee employee = null;
		for (Employee currentEmployee : employees) {
			if (currentEmployee.geteID() == eID) {
				employee = currentEmployee;
			}
		}

        if (parts[3].contains("<m>")) {
            double monthlyIncome = Double.parseDouble(parts[3].replace("<m>", ""));
            int numMonths = Integer.parseInt(parts[4].replace("<n>", ""));
			//TODO not sure if the pay period is using the correct ID
            PayPeriod payPeriod = new PayPeriod(eID, pStartDate, pEndDate);
            payRecords[noRecords] = new PayRecord(rID, employee, payPeriod, monthlyIncome, numMonths);
        } else {
            double hours = Double.parseDouble(parts[3].replace("<h>", ""));
            double rate = Double.parseDouble(parts[4].replace("<r>", ""));
            PayPeriod payPeriod = new PayPeriod(eID, pStartDate, pEndDate);
            payRecords[noRecords] = new PayRecord(rID, employee, payPeriod, hours, rate);
        }
        noRecords++;
    }

    public void displayPayRecord(JTextField j) {
        j.setText(j.getText() + "");
        // it shows all payroll records for all currently added employee and the total net pay and average net pay in the GUI text area
        // at should append data to text area, it must not overwrite data in the GUI text area

    }


    public double avgNetPay() {
        // returns the average of the total net pay of all added employees
		for (PayRecord record : payRecords ) {
			totalNetPay += record.netPay();
		}
		avgNetPay = totalNetPay / payRecords.length;
        return avgNetPay;
    }

    public PayRecord[] getPayRecords() {
        return payRecords;
    }

    public void addEmployee(String employee, String id, String first, String last, String employeeStatus,
                            String streetAddress, String houseNumber, String city, String state, String zip) {
        employeeArrayList.add(createEmployee(new String[]{employee,id,first,last,
                employeeStatus,streetAddress,houseNumber,city,state,zip}));
    }
}


