package PP03;

public class Employee extends Person {

    private int eID;
    private Status empStatus;

    // 1- The Employee class extends superclass Person
    // 2- add the subclass Employee constructor that calls the supper Person class constructor, you should provide input data for all parent class data fields
    // 3- add setters/getters methods
    // 4- add override toString() method that overrides toString() in the superclass Person

    public Employee(String fName, String lName, Address address, int eID, Status empStatus) {
        super(fName, lName, address);
        this.eID = eID;
        this.empStatus = empStatus;
    }

    public int geteID() {
        return eID;
    }

    public void seteID(int eID) {
        this.eID = eID;
    }

    public Status getEmpStatus() {
        return empStatus;
    }

    public void setEmpStatus(Status empStatus) {
        this.empStatus = empStatus;
    }

    public String empStatus() {
        return this.empStatus.name();
    }

    @Override
    public String toString() {
        return "EmployeeID=" + eID +
                ", Status=" + empStatus + ", " +
                super.toString();
    }
}
