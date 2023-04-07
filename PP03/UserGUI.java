package PP03;

import javax.swing.*;
import java.awt.*;

public class UserGUI extends JPanel {
    private static JTextField employeeIdTextField;
    private static JTextField firstNameTextField;
    private static JTextField lastNameTextField;
    private static JRadioButton hourlyButton;
    private static JRadioButton weeklyButton;
    private static ButtonGroup employeeStatus;
    private static JTextField streetTextField;
    private static JTextField houseNumberTextField;
    private static JTextField cityTextField;
    private static JTextField stateTextField;
    private static JTextField zipCodeTextField;
    private static JTextField payPeriodIdTextField;
    private static JTextField startDateTextField;
    private static JTextField endDateTextField;
    private static JTextField monthlyIncomeTextField;
    private static JTextField numberOfMonthsTextField;
    private static JTextField payRecordIdTextField;
    private static JTextField payHoursTextField;
    private static JTextField payRateTextField;
    private static JButton addEmployeeButton;
    private PayRoll payRoll;
    private String fileName = "PayRoll.txt";
    private JPanel employeePanel;
    private JPanel mainPanel;
    private GridBagConstraints mainGBC;
    private JButton addPayRecordButton;
    private JTextArea recordsTextArea = new JTextArea(4, 80);


    public UserGUI() {

        // prompt the user to input the number of pay records
        int n = 6; // is the number of pay records for employees
        payRoll = new PayRoll(fileName, n);


        addEmployeeButton = new JButton("Add Employee");
        addEmployeeButton.addActionListener(e -> {
            if (hourlyButton.isSelected() || weeklyButton.isSelected()) {
                String empStatusString = hourlyButton.isSelected() ? "HOURLY" : "FULLTIME";
                payRoll.addEmployee("employee", employeeIdTextField.getText(), firstNameTextField.getText(),
                        lastNameTextField.getText(), empStatusString,
                        streetTextField.getText(), houseNumberTextField.getText(),
                        cityTextField.getText(), stateTextField.getText(), zipCodeTextField.getText());
                setPayRecordFieldsEditability();
            }
        });

        addPayRecordButton = new JButton("Add Pay Record");
        addPayRecordButton.addActionListener(e -> {

            if (hourlyButton.isSelected()) {
                payRoll.addPayRecordHourly("payRecord", payRecordIdTextField.getText(), employeeIdTextField.getText(),
                        payHoursTextField.getText(), payRateTextField.getText(), payPeriodIdTextField.getText(),
                        startDateTextField.getText(), endDateTextField.getText());
            } else {
                payRoll.addPayRecordFullTime("payRecord", payRecordIdTextField.getText(), employeeIdTextField.getText(),
                        monthlyIncomeTextField.getText(), numberOfMonthsTextField.getText(), payPeriodIdTextField.getText(),
                        startDateTextField.getText(), endDateTextField.getText());
            }
            payRoll.displayPayRecord(recordsTextArea, payRecordIdTextField.getText());
        });

        initGUI();
        doTheLayout();

    } // end of constructor

    public static void main(String[] args) {

        JFrame f = new JFrame("Pay Roll");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = f.getContentPane();
        contentPane.add(new UserGUI());
    }

    private static void addLabel(JPanel panel, String text, Color color, GridBagConstraints gbc, int gridx, int gridy) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        JLabel label = new JLabel(text);
        if (color != null) {
            label.setForeground(color);
        }
        panel.add(label, gbc);
    }

    private static JTextField addLabelAndTextField(JPanel panel, String labelText, int textFieldColumns, GridBagConstraints gbc, int gridx, int gridy, boolean editable) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        panel.add(new JLabel(labelText), gbc);

        JTextField textField = new JTextField(textFieldColumns);
        textField.setEditable(editable);
        gbc.gridx++;
        panel.add(textField, gbc);
        return textField;
    }

    private static ButtonGroup addRadioButton(JPanel panel, String text1, String text2, GridBagConstraints gbc, int gridx, int gridy) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        weeklyButton = new JRadioButton(text1);
        panel.add(weeklyButton, gbc);

        gbc.gridx++;
        hourlyButton = new JRadioButton(text2);
        panel.add(hourlyButton, gbc);

        // Create a ButtonGroup to ensure only one radio button is selected at a time
        ButtonGroup radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(weeklyButton);
        radioButtonGroup.add(hourlyButton);
        return radioButtonGroup;
    }

    private void setPayRecordFieldsEditability() {
        if (hourlyButton.isSelected()) {
            payHoursTextField.setEditable(true);
            payHoursTextField.setBackground(Color.white);
            payRateTextField.setEditable(true);
            payRateTextField.setBackground(Color.white);

            monthlyIncomeTextField.setEditable(false);
            monthlyIncomeTextField.setBackground(Color.gray);
            numberOfMonthsTextField.setEditable(false);
            numberOfMonthsTextField.setBackground(Color.gray);
        } else {
            payHoursTextField.setEditable(false);
            payHoursTextField.setBackground(Color.gray);
            payRateTextField.setEditable(false);
            payRateTextField.setBackground(Color.gray);

            monthlyIncomeTextField.setEditable(true);
            monthlyIncomeTextField.setBackground(Color.white);
            numberOfMonthsTextField.setEditable(true);
            numberOfMonthsTextField.setBackground(Color.white);
        }
    }

    private void initGUI() {
        mainPanel = new JPanel();
        mainGBC = new GridBagConstraints();
        employeePanel = new JPanel();

        mainPanel.add(employeePanel, mainGBC);
    }// end of creating objects method

    private void doTheLayout() {


        mainPanel.setLayout(new GridBagLayout());
        mainGBC.gridx = 0;
        mainGBC.gridy = 0;
        mainGBC.anchor = GridBagConstraints.NORTHWEST;
        mainGBC.fill = GridBagConstraints.HORIZONTAL;
        mainGBC.weightx = 1;
        mainGBC.weighty = 1;

        employeePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Employee Information section
        addLabel(employeePanel, "Employee:", Color.BLUE, gbc, 0, 0);
        employeeIdTextField = addLabelAndTextField(employeePanel, "ID", 10, gbc, 0, 1, true);
        firstNameTextField = addLabelAndTextField(employeePanel, "First Name", 10, gbc, 2, 1, true);
        lastNameTextField = addLabelAndTextField(employeePanel, "Last Name", 10, gbc, 4, 1, true);
        addLabel(employeePanel, "Employee Status", null, gbc, 0, 2);

        employeeStatus = addRadioButton(employeePanel, "Full Time", "Hourly", gbc, 0, 3);

        addLabel(employeePanel, "Address", null, gbc, 0, 4);
        streetTextField = addLabelAndTextField(employeePanel, "Street", 20, gbc, 0, 5, true);
        houseNumberTextField = addLabelAndTextField(employeePanel, "H/Apt Number", 5, gbc, 2, 5, true);
        cityTextField = addLabelAndTextField(employeePanel, "City", 10, gbc, 0, 6, true);
        stateTextField = addLabelAndTextField(employeePanel, "State", 2, gbc, 2, 6, true);
        zipCodeTextField = addLabelAndTextField(employeePanel, "Zipcode", 5, gbc, 4, 6, true);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 1;
        employeePanel.add(addEmployeeButton, gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 1;


        // Payroll section
        gbc.gridy = 8;
        addLabel(employeePanel, "Payroll:", Color.BLUE, gbc, 0, gbc.gridy);
        payPeriodIdTextField = addLabelAndTextField(employeePanel, "ID", 10, gbc, 0, ++gbc.gridy, true);
        startDateTextField = addLabelAndTextField(employeePanel, "Start Date", 10, gbc, 2, gbc.gridy, true);
        endDateTextField = addLabelAndTextField(employeePanel, "End Date", 10, gbc, 4, gbc.gridy, true);

        gbc.gridy = 10; // Update this value if you add more elements to the previous sections
        addLabel(employeePanel, "Pay Records:", Color.BLUE, gbc, 0, gbc.gridy);
        monthlyIncomeTextField = addLabelAndTextField(employeePanel, "Monthly Income:", 10, gbc, 0, ++gbc.gridy, false);
        numberOfMonthsTextField = addLabelAndTextField(employeePanel, "Number of Months:", 10, gbc, 2, gbc.gridy, false);
        payRecordIdTextField = addLabelAndTextField(employeePanel, "ID:", 10, gbc, 4, gbc.gridy, true);
        payHoursTextField = addLabelAndTextField(employeePanel, "Pay Hours:", 10, gbc, 0, ++gbc.gridy, false);
        payRateTextField = addLabelAndTextField(employeePanel, "Pay Rate:", 10, gbc, 2, gbc.gridy, false);

        monthlyIncomeTextField.setBackground(Color.gray);
        numberOfMonthsTextField.setBackground(Color.gray);
        payHoursTextField.setBackground(Color.gray);
        payRateTextField.setBackground(Color.gray);

        gbc.gridx = 1;
        gbc.gridy++;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        employeePanel.add(addPayRecordButton, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 6; // Span the text across the panel
        gbc.anchor = GridBagConstraints.WEST;
        addLabel(employeePanel, "Current Employee Records and Stat (Total & Average Pay):", Color.BLUE, gbc, gbc.gridx, gbc.gridy);

        // Add large 4-line, 80-column text box
        recordsTextArea.setEditable(false);
        for (PayRecord record : payRoll.getPayRecords()) {
            if (record != null) {
                recordsTextArea.append(record.toString());
            }
        }
        gbc.gridy++;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JScrollPane scrollPane = new JScrollPane(recordsTextArea);
        employeePanel.add(scrollPane, gbc);

        mainPanel.add(employeePanel, mainGBC);


        int result = JOptionPane.showConfirmDialog(null, mainPanel, "Employee Information", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            System.out.println("User clicked OK");
        } else {
            close();
        }

    }// end of Layout method

    void transfer() {

    }// end of transfer action event method

    void updateTextarea() {

    }

    void close() {
        System.exit(0);
    }// end of transfer action event method

}
