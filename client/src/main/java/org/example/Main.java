package org.example;

import org.example.dto.*;
import org.example.util.CustomPeopleTableModel;
import org.example.util.CustomVisasTableModel;
import org.example.util.DateCellRenderer;
import org.example.util.DateLabelFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;
import java.util.*;

public class Main {

    private JFrame frame;

    private JLabel jLabel, jLabelDirectories;
    private JLabel passportNumber, firstName, lastName, birthDate, nationality;
    private JLabel visaNumber, issueDate, countries, type, validityPeriod, personPassportNumber;
    private JTextField passportNumberText, firstNameText, lastNameText, nationalityText;
    private JTextField visaNumberText, typeText, validityPeriodText;

    private JComboBox passportNumberComboBox;
    private JTextArea countriesText;
    private JTable table = new JTable();
    private JComboBox<String> comboBox;

    private JScrollPane scrollPane;

    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton submitAddingButton;

    UtilDateModel model = new UtilDateModel();
    JDatePickerImpl datePicker;


    public Main() {
        frame = new JFrame("The directory module");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jLabel = new JLabel("Гаркавая А. Ю., 4 курс, 4 группа, 2023 год");
        jLabelDirectories = new JLabel("Select directory");

        comboBox = new JComboBox<>(new String[]{"People", "Visas"});
        comboBox.addActionListener(e -> updateTable());

        passportNumberComboBox = new JComboBox<>(new String[]{});

        table.setAutoCreateRowSorter(true);
        scrollPane = new JScrollPane(table);

        Date date = new Date();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        model.setDate(1900 + date.getYear(), date.getMonth(), date.getDate());
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        submitAddingButton = new JButton("Submit");
        submitAddingButton.addActionListener(e -> {
            if (comboBox.getSelectedIndex() == 0) {
                String passportNumber = passportNumberText.getText();
                String firstName = firstNameText.getText();
                String lastName = lastNameText.getText();
                Date birthDate = (Date) datePicker.getModel().getValue();
                String nationality = nationalityText.getText();
                if (passportNumber != null && passportNumber.length() > 0 &&
                        firstName != null && firstName.length() > 0 &&
                        lastName != null && lastName.length() > 0 &&
                        birthDate != null &&
                        nationality != null && nationality.length() > 0
                ) {
                    createNewPerson(passportNumber, firstName, lastName, birthDate, nationality);
                    updateTable();
                    setPeopleAddComponentsVisibility(false);
                    setEmptyText();
                } else {
                    JOptionPane.showMessageDialog(frame, "Please, fill all fields!", "Dialog",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else {
                String visaNumber = visaNumberText.getText();
                Date issueDate = (Date) datePicker.getModel().getValue();
                String countries = countriesText.getText();
                String type = typeText.getText();
                String validityPeriod = validityPeriodText.getText();
                String passportNumber = (String) passportNumberComboBox.getSelectedItem();
                if (visaNumber != null && !visaNumber.isEmpty() &&
                issueDate != null &&
                countries != null && !countries.isEmpty() &&
                type != null && !type.isEmpty() &&
                validityPeriod != null && !validityPeriod.isEmpty() &&
                passportNumber != null && !passportNumber.isEmpty()) {
                    try {
                        createNewVisa(Long.parseLong(visaNumber), issueDate, countries, type.charAt(0), Double.parseDouble(validityPeriod), passportNumber);
                        updateTable();
                        setVisasAddComponentsVisibility(false);
                        setEmptyText();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Visa number should have long type and validity period - double type.", "Dialog",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Please, fill all fields!", "Dialog",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        passportNumber = new JLabel("Passport number");
        firstName = new JLabel("First name");
        lastName = new JLabel("Last name");
        birthDate = new JLabel("Birth date");
        nationality = new JLabel("Nationality");
        passportNumberText = new JTextField();
        firstNameText = new JTextField();
        lastNameText = new JTextField();
        nationalityText = new JTextField();

        visaNumber = new JLabel("Visa number");
        issueDate = new JLabel("Issue date");
        countries = new JLabel("Countries");
        type = new JLabel("Type");
        validityPeriod = new JLabel("Validity period");
        personPassportNumber = new JLabel("Person passport number");
        visaNumberText = new JTextField();
        countriesText = new JTextArea();
        typeText = new JTextField();
        validityPeriodText = new JTextField();
        setPeopleAddComponentsVisibility(false);
        setVisasAddComponentsVisibility(false);

        addButton = new JButton("Add");

        addButton.addActionListener(e -> {
            Date currDate = new Date();
            if (comboBox.getSelectedIndex() == 0) {
                datePicker.setBounds(160, 460, 150, 30);
                model.setDate(1900 + currDate.getYear(), currDate.getMonth(), currDate.getDate());
                setPeopleAddComponentsVisibility(true);
            } else {
                datePicker.setBounds(230, 380, 150, 30);
                model.setDate(1900 + currDate.getYear(), currDate.getMonth(), currDate.getDate());
                List<String> passportNumbers = Objects.requireNonNull(getPeopleIds()).getPeople();
                passportNumberComboBox.removeAllItems();
                for (String passportNumber : passportNumbers) {
                    passportNumberComboBox.addItem(passportNumber);
                }
                setVisasAddComponentsVisibility(true);
            }
        });
        updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow < 0 || selectedRow > table.getRowCount()) {
                JOptionPane.showMessageDialog(frame, "Please, select the row!", "Dialog",
                        JOptionPane.ERROR_MESSAGE);
            } else if (comboBox.getSelectedIndex() == 0) {
                datePicker.setBounds(160, 460, 150, 30);
                setPeopleAddComponentsVisibility(true);
                String passportNumber = (String) table.getValueAt(selectedRow, 0);
                passportNumberText.setText(passportNumber);
                firstNameText.setText((String) table.getValueAt(selectedRow, 1));
                lastNameText.setText((String) table.getValueAt(selectedRow, 2));
                Date birthDate = (Date) table.getValueAt(selectedRow, 3);
                model.setDate(1900 + birthDate.getYear(), birthDate.getMonth(), birthDate.getDate());
                nationalityText.setText((String)table.getValueAt(selectedRow, 4));
            } else {
                List<String> passportNumbers = Objects.requireNonNull(getPeopleIds()).getPeople();
                passportNumberComboBox.removeAllItems();
                for (String passportNumber : passportNumbers) {
                    passportNumberComboBox.addItem(passportNumber);
                }
                datePicker.setBounds(230, 380, 150, 30);
                setVisasAddComponentsVisibility(true);
                visaNumberText.setText(Long.toString((Long) table.getValueAt(selectedRow, 0)));
                Date issueDate = (Date) table.getValueAt(selectedRow, 1);
                model.setDate(1900 + issueDate.getYear(), issueDate.getMonth(), issueDate.getDate());
                countriesText.setText((String) table.getValueAt(selectedRow, 2));
                typeText.setText(Character.toString((Character)table.getValueAt(selectedRow, 3)));
                validityPeriodText.setText(Double.toString((Double) table.getValueAt(selectedRow, 4)));
                passportNumberComboBox.setSelectedItem(table.getValueAt(selectedRow, 5));
            }
        });
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow < 0 || selectedRow > table.getRowCount()) {
                JOptionPane.showMessageDialog(frame, "Please, select the row!", "Dialog",
                        JOptionPane.ERROR_MESSAGE);
            } else if (comboBox.getSelectedIndex() == 0) {
                deletePerson((String) table.getValueAt(selectedRow, 0));
                updateTable();
            } else {
                deleteVisa((Long) table.getValueAt(selectedRow, 0));
                updateTable();
            }
        });


        jLabel.setBounds(30, 0, 340, 30);
        jLabelDirectories.setBounds(30, 30, 120, 30);
        comboBox.setBounds(150, 30, 150, 30);
        scrollPane.setBounds(30, 70, 1100, 200);

        addButton.setBounds(30, 300, 100, 30);
        updateButton.setBounds(140, 300, 100, 30);
        deleteButton.setBounds(250, 300, 100, 30);

        passportNumber.setBounds(30, 340, 150, 30);
        firstName.setBounds(30, 380, 100, 30);
        lastName.setBounds(30, 420, 100, 30);
        birthDate.setBounds(30, 460, 100, 30);
        nationality.setBounds(30, 500, 100, 30);
        passportNumberText.setBounds(160, 340, 150, 30);
        firstNameText.setBounds(160, 380, 150, 30);
        lastNameText.setBounds(160, 420, 150, 30);
        datePicker.setBounds(160, 460, 150, 30);
        nationalityText.setBounds(160, 500, 150, 30);
        submitAddingButton.setBounds(400, 420, 150, 30);

        visaNumber.setBounds(30, 340, 150, 30);
        issueDate.setBounds(30, 380, 150, 30);
        countries.setBounds(30, 420, 150, 30);
        type.setBounds(30, 500, 150, 30);
        validityPeriod.setBounds(30, 540, 150, 30);
        personPassportNumber.setBounds(30, 580, 180, 30);
        visaNumberText.setBounds(230, 340, 150, 30);
        countriesText.setBounds(230, 420, 150, 70);
        typeText.setBounds(230, 500, 150, 30);
        validityPeriodText.setBounds(230, 540, 150, 30);
        passportNumberComboBox.setBounds(230, 580, 150, 30);
        submitAddingButton.setBounds(400, 420, 150, 30);

        frame.setLayout(null);

        frame.add(jLabel);
        frame.add(jLabelDirectories);
        frame.add(comboBox);

        frame.add(scrollPane);

        frame.add(addButton);
        frame.add(updateButton);
        frame.add(deleteButton);

        frame.add(passportNumber);
        frame.add(firstName);
        frame.add(lastName);
        frame.add(birthDate);
        frame.add(nationality);
        frame.add(passportNumberText);
        frame.add(firstNameText);
        frame.add(lastNameText);
        frame.add(datePicker);
        frame.add(nationalityText);
        frame.add(submitAddingButton);

        frame.add(visaNumber);
        frame.add(issueDate);
        frame.add(countries);
        frame.add(type);
        frame.add(validityPeriod);
        frame.add(personPassportNumber);
        frame.add(visaNumberText);
        frame.add(countriesText);
        frame.add(typeText);
        frame.add(validityPeriodText);
        frame.add(passportNumberComboBox);


        updatePeopleTable();

        frame.setVisible(true);
        frame.setSize(1200,700);
        frame.setResizable(false);
    }

    private void createNewPerson(String passportNumber, String firstName, String lastName, Date birthDate, String nationality) {
        String addPersonUrl = "http://localhost:8080/people/update";
        Map<String, Object> jsonToSend = new HashMap<>();

        jsonToSend.put("passportNumber", passportNumber);
        jsonToSend.put("firstName", firstName);
        jsonToSend.put("lastName", lastName);
        jsonToSend.put("birthDate", birthDate);
        jsonToSend.put("nationality", nationality);

        makePostRequest(addPersonUrl, jsonToSend);
    }

    private void createNewVisa(Long visaNumber, Date issueDate, String countries, Character type, Double validityPeriod, String passportNumber) {
        String addPersonUrl = "http://localhost:8080/visas/update";
        Map<String, Object> jsonToSend = new HashMap<>();

        jsonToSend.put("id", visaNumber);
        jsonToSend.put("issueDate", issueDate);
        jsonToSend.put("countries", countries);
        jsonToSend.put("type", type);
        jsonToSend.put("validityPeriod", validityPeriod);
        jsonToSend.put("person", new PersonDTO(passportNumber));

        makePostRequest(addPersonUrl, jsonToSend);
    }

    private void deleteVisa(Long visaNumber) {
        String deleteVisa = "http://localhost:8080/visas/delete/" + visaNumber.toString();
        Map<String, Object> jsonToSend = new HashMap<>();

        makePostRequest(deleteVisa, jsonToSend);
    }

    private void deletePerson(String passportNumber) {
        String deletePerson = "http://localhost:8080/people/delete/" + passportNumber;
        Map<String, Object> jsonToSend = new HashMap<>();

        makePostRequest(deletePerson, jsonToSend);
    }

    private static void makePostRequest(String url, Map<String, Object> jsonToSend) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(jsonToSend);
        try {
            restTemplate.postForObject(url, request, HttpStatus.class);
            System.out.println("The changes were successful!");
        } catch (RestClientException ex) {
            System.out.println("Something went wrong! " + ex.getMessage());
        }
    }

    private void updateTable() {
        String selectedEntity = (String) comboBox.getSelectedItem();
        if ("People".equals(selectedEntity)) {
            setVisasAddComponentsVisibility(false);
            updatePeopleTable();
        } else if ("Visas".equals(selectedEntity)) {
            setPeopleAddComponentsVisibility(false);
            updateVisasTable();
        }
    }

    private void setPeopleAddComponentsVisibility(boolean flag) {
            passportNumber.setVisible(flag);
            firstName.setVisible(flag);
            lastName.setVisible(flag);
            birthDate.setVisible(flag);
            nationality.setVisible(flag);
            submitAddingButton.setVisible(flag);
            passportNumberText.setVisible(flag);
            firstNameText.setVisible(flag);
            lastNameText.setVisible(flag);
            datePicker.setVisible(flag);
            nationalityText.setVisible(flag);
    }

    private void setEmptyText() {
        passportNumberText.setText("");
        firstNameText.setText("");
        lastNameText.setText("");
        nationalityText.setText("");
        visaNumberText.setText("");
        countriesText.setText("");
        typeText.setText("");
        validityPeriodText.setText("");
    }

    private void setVisasAddComponentsVisibility(boolean flag) {
            visaNumber.setVisible(flag);
            issueDate.setVisible(flag);
            countries.setVisible(flag);
            type.setVisible(flag);
            validityPeriod.setVisible(flag);
            personPassportNumber.setVisible(flag);
            visaNumberText.setVisible(flag);
            datePicker.setVisible(flag);
            countriesText.setVisible(flag);
            typeText.setVisible(flag);
            validityPeriodText.setVisible(flag);
            passportNumberComboBox.setVisible(flag);
            submitAddingButton.setVisible(flag);
    }

    private void updateVisasTable() {
        List<VisaDTO> visas = Objects.requireNonNull(getVisas()).getVisas();

        String[] columnNames = {"visa number", "issue date", "countries", "type", "validity period", "persons passport number"};

        CustomVisasTableModel visasTableModel = new CustomVisasTableModel(visas, columnNames);
        table.setModel(visasTableModel);
        table.getColumnModel().getColumn(1).setCellRenderer(new DateCellRenderer());
    }

    private void updatePeopleTable() {
        List<PersonDTO> people = Objects.requireNonNull(getPeople()).getPeople();

        String[] columnNames = {"passport number", "first name", "last name", "birth date", "nationality"};

        CustomPeopleTableModel peopleTableModel = new CustomPeopleTableModel(people, columnNames);
        table.setModel(peopleTableModel);
        table.getColumnModel().getColumn(3).setCellRenderer(new DateCellRenderer());

    }

    private PeopleResponse getPeople() {
        String getPeopleUrl = "http://localhost:8080/people/all";

        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(getPeopleUrl, PeopleResponse.class);
        } catch (RestClientException ex) {
            System.out.println("Something went wrong! " + ex.getMessage());
        }
        return null;
    }

    private PeopleIdsResponse getPeopleIds() {
        String getPeopleIdsUrl = "http://localhost:8080/people/allIds";

        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(getPeopleIdsUrl, PeopleIdsResponse.class);
        } catch (RestClientException ex) {
            System.out.println("Something went wrong! " + ex.getMessage());
        }
        return null;
    }

    private VisasResponse getVisas() {
        String getVisasUrl = "http://localhost:8080/visas/all";

        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(getVisasUrl, VisasResponse.class);
        } catch (RestClientException ex) {
            System.out.println("Something went wrong! " + ex.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}