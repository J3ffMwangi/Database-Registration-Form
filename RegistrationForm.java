import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegistrationForm extends JFrame {
    JTextField nameField, mobileField;
    JTextArea addressArea, displayArea;
    JRadioButton maleBtn, femaleBtn;
    JComboBox<String> dayBox, monthBox, yearBox;
    JCheckBox terms;
    JButton submitBtn, resetBtn;
    ButtonGroup genderGroup;

    public RegistrationForm() {
        setTitle("Registration Form");
        setSize(750, 400);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Form Labels and Fields
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(30, 40, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(140, 40, 150, 25);
        add(nameField);

        JLabel mobileLabel = new JLabel("Mobile:");
        mobileLabel.setBounds(30, 80, 100, 25);
        add(mobileLabel);

     
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(30, 120, 100, 25);
        add(genderLabel);

        maleBtn = new JRadioButton("Male");
        femaleBtn = new JRadioButton("Female");
        maleBtn.setBounds(140, 120, 70, 25);
        femaleBtn.setBounds(210, 120, 80, 25);
        genderGroup = new ButtonGroup();
        genderGroup.add(maleBtn);
        genderGroup.add(femaleBtn);
        add(maleBtn);
        add(femaleBtn);

        JLabel dobLabel = new JLabel("DOB:");
        dobLabel.setBounds(30, 160, 100, 25);
        add(dobLabel);

        dayBox = new JComboBox<>();
        monthBox = new JComboBox<>();
        yearBox = new JComboBox<>();
        for (int i = 1; i <= 31; i++) dayBox.addItem(String.valueOf(i));
        for (String m : new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"})
            monthBox.addItem(m)

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(30, 200, 100, 25);
        add(addressLabel);

        addressArea = new JTextArea();
        addressArea.setBounds(140, 200, 190, 50);
        add(addressArea);

    
        submitBtn = new JButton("Submit");
        submitBtn.setBounds(30, 300, 100, 25);
        resetBtn = new JButton("Reset");
        resetBtn.setBounds(150, 300, 100, 25);
        add(submitBtn); add(resetBtn);

        displayArea = new JTextArea();
        displayArea.setBounds(370, 40, 340, 250);
        displayArea.setEditable(false);
        add(displayArea);

        // Action Listeners
        submitBtn.addActionListener(e -> saveData());
        resetBtn.addActionListener(e -> clearForm());

        setVisible(true);
    }

    private void saveData() {
        if (!terms.isSelected()) {
            JOptionPane.showMessageDialog(this, "Please accept the terms and conditions.");
            return;
        }

        String name = nameField.getText();
        String mobile = mobileField.getText();
        String gender = maleBtn.isSelected() ? "Male" : femaleBtn.isSelected() ? "Female" : "";
        String dob = dayBox.getSelectedItem() + "-" + monthBox.getSelectedItem() + "-" + yearBox.getSelectedItem();
        String address = addressArea.getText();

        if (name.isEmpty() || mobile.isEmpty() || gender.isEmpty() || address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all the fields.");
            return;
        }

        try (Connection conn = DBConnection.connect()) {
            String sql = "INSERT INTO users(name, mobile, gender, dob, address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, mobile);
            pstmt.setString(3, gender);
            pstmt.setString(4, dob);
            pstmt.setString(5, address);
            pstmt.executeUpdate();

            displayArea.append("Saved: " + name + " | " + mobile + " | " + gender + " | " + dob + "\n");
            JOptionPane.showMessageDialog(this, "Data saved successfully.");
            clearForm();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void clearForm() {
        nameField.setText("");
        mobileField.setText("");
        genderGroup.clearSelection();
        addressArea.setText("");
        terms.setSelected(false);
        dayBox.setSelectedIndex(0);
        monthBox.setSelectedIndex(0);
        yearBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        new RegistrationForm();
    }
}
