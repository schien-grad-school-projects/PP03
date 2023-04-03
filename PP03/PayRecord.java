package PP03;


public class PayRecord {
	
	private int rID;
    private Employee employee;
    private PayPeriod payPeriod;
    private TaxIncome payTax;
    
    private double payHours;
    private double payRate;
    
    private double montlyIncome;
    private int numMonths;
    
       
    
    public static final int REG_HOURS = 40;
    public static final double OT_RATE = 1.25;
    
    // pay record constructor for hourly employee
    public PayRecord(int id, Employee e, PayPeriod period, double hours, double rate){
    	
    	this.rID = id;
    	this.employee = e;
    	this.payPeriod = period;
    	this.payHours = hours;
    	this.payRate = rate;
    	this.montlyIncome = 0;
    	this.numMonths = 0;
    	this.payTax = new TaxIncome();
  
    }
    
    // pay record constructor for full time employee
    public PayRecord(int id, Employee e, PayPeriod period, double mIncome, int mNum){
 	
 	this.rID = id;
 	this.employee = e;
 	this.payPeriod = period;
 	this.payHours = 0;
 	this.payRate = 0;
 	this.montlyIncome = mIncome;
 	this.numMonths = mNum;
 	this.payTax = new TaxIncome();

 }


  // 1- add setters and getters methods
  // 2- add override method toString()
  // 3- complete the code in the following methods: grossPay() and netPay()
    
    // complete the code to compute the gross pay for the employee based on the employee status
	public double grossPay(){
		return 0;
	}
    
  // complete the code in this method to compute the net pay of the employee after taxes (state and federal)
     public double netPay(){
		  return 0;
  }
  
 

}
