package com.Acme.Secret.Santa.Game;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Service responsible for processing Excel files related to Secret Santa assignments.
 * This service reads employee data, previous assignments, generates new assignments, and writes the output to a new Excel file.
 */
@Service
public class ExcelService {

    /**
     * Reads employee data from an Excel file.
     * Assumes the employee data is in the first sheet and the first two columns represent the employee's name and email.
     *
     * @param inputStream Input stream of the Excel file containing employee data.
     * @return List of employees extracted from the file.
     * @throws IOException if there is an error reading the file.
     */
    public List<Employee> readEmployeesFromXlsx(InputStream inputStream) throws IOException {
        List<Employee> employees = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0); // Assume the first sheet contains employee data

            Iterator<Row> rowIterator = sheet.iterator();
            // Skip header row
            if (rowIterator.hasNext()) rowIterator.next();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String employeeName = row.getCell(0).getStringCellValue();
                String employeeEmail = row.getCell(1).getStringCellValue();
                if (employeeName == null || employeeEmail == null) {
                    throw new IllegalArgumentException("Employee name or email is missing.");
                }
                employees.add(new Employee(employeeName, employeeEmail));
            }
        } catch (IOException e) {
            throw new IOException("Error reading the employee data file.", e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid data in employee file: " + e.getMessage(), e);
        }
        return employees;
    }

    /**
     * Reads previous Secret Santa assignments from an Excel file.
     * Assumes the previous assignment data is in the first sheet and contains employee name, email, 
     * their assigned secret child’s name, and email.
     *
     * @param inputStream Input stream of the Excel file containing previous assignments.
     * @return List of previous assignments.
     * @throws IOException if there is an error reading the file.
     */
    public List<SecretSantaAssignment> readPreviousAssignmentsFromXlsx(InputStream inputStream) throws IOException {
        List<SecretSantaAssignment> previousAssignments = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0); // Assume the first sheet contains previous assignments data

            Iterator<Row> rowIterator = sheet.iterator();
            // Skip header row
            if (rowIterator.hasNext()) rowIterator.next();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String employeeName = row.getCell(0).getStringCellValue();
                String employeeEmail = row.getCell(1).getStringCellValue();
                String secretChildName = row.getCell(2).getStringCellValue();
                String secretChildEmail = row.getCell(3).getStringCellValue();
                if (employeeName == null || employeeEmail == null || secretChildName == null || secretChildEmail == null) {
                    throw new IllegalArgumentException("One or more fields in previous assignments are missing.");
                }
                previousAssignments.add(new SecretSantaAssignment(employeeName, employeeEmail, secretChildName, secretChildEmail));
            }
        } catch (IOException e) {
            throw new IOException("Error reading the previous assignment data file.", e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid data in previous assignment file: " + e.getMessage(), e);
        }
        return previousAssignments;
    }

    /**
     * Generates Secret Santa assignments by shuffling employees and ensuring no one is assigned to their previous secret child.
     *
     * @param employees List of employees to be assigned.
     * @param previousAssignments List of previous assignments to avoid conflicts.
     * @return List of new Secret Santa assignments.
     */
    public List<SecretSantaAssignment> generateAssignments(List<Employee> employees, List<SecretSantaAssignment> previousAssignments) {
        Map<String, String> previousAssignmentsMap = new HashMap<>();
        for (SecretSantaAssignment assignment : previousAssignments) {
            previousAssignmentsMap.put(assignment.getEmployeeEmail(), assignment.getSecretChildEmail());
        }

        List<SecretSantaAssignment> assignments = new ArrayList<>();
        List<Employee> remainingEmployees = new ArrayList<>(employees);
        Collections.shuffle(remainingEmployees); // Shuffle the employees to randomize assignment

        for (Employee employee : employees) {
            String assignedChildEmail = null;
            for (Employee candidate : remainingEmployees) {
                if (!employee.getEmail().equals(candidate.getEmail()) &&
                        !previousAssignmentsMap.getOrDefault(employee.getEmail(), "").equals(candidate.getEmail())) {
                    assignedChildEmail = candidate.getEmail();
                    assignments.add(new SecretSantaAssignment(
                            employee.getName(),
                            employee.getEmail(),
                            candidate.getName(),
                            assignedChildEmail
                    ));
                    remainingEmployees.remove(candidate); // Remove the chosen employee from the list
                    break;
                }
            }
        }

        return assignments;
    }

    /**
     * Writes the generated Secret Santa assignments to an Excel file.
     * The file will contain the employee names, emails, their assigned secret child’s name, and email.
     *
     * @param assignments List of Secret Santa assignments to be written to the file.
     * @return A ByteArrayInputStream containing the generated Excel file.
     * @throws IOException if there is an error writing the file.
     */
    public ByteArrayInputStream writeAssignmentsToXlsx(List<SecretSantaAssignment> assignments) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Secret Santa Assignments");

        // Create headers
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Employee Name");
        headerRow.createCell(1).setCellValue("Employee Email");
        headerRow.createCell(2).setCellValue("Secret Child Name");
        headerRow.createCell(3).setCellValue("Secret Child Email");

        // Populate the rows with assignment data
        int rowIndex = 1;
        for (SecretSantaAssignment assignment : assignments) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(assignment.getEmployeeName());
            row.createCell(1).setCellValue(assignment.getEmployeeEmail());
            row.createCell(2).setCellValue(assignment.getSecretChildName());
            row.createCell(3).setCellValue(assignment.getSecretChildEmail());
        }

        // Write the workbook to a ByteArrayInputStream to return as response
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        workbook.close();

        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
}
