package com.Acme.Secret.Santa.Game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Controller responsible for handling the Secret Santa assignment requests.
 * Provides an endpoint for uploading employee data and previous assignments to generate new Secret Santa assignments.
 */
@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/api/secretsanta")
public class SecretSantaController {

    @Autowired
    private ExcelService excelService;

    @Autowired
    private ObjectMapper objectMapper; 

    /**
     * Endpoint for assigning Secret Santa by processing employee and previous assignment data.
     * Accepts two files: one for employees and another for previous assignments.
     *
     * @param employeesFile          The file containing employee data.
     * @param previousAssignmentsFile The file containing previous assignments.
     * @return ResponseEntity containing the generated assignments as an Excel file.
     */
    @PostMapping("/assign")
    public ResponseEntity<?> assignSecretSanta(@RequestParam("employeesFile") MultipartFile employeesFile,
                                               @RequestParam("previousAssignmentsFile") MultipartFile previousAssignmentsFile) {
        try {
            // Validate file types
            if (!employeesFile.getOriginalFilename().endsWith(".xlsx") || !previousAssignmentsFile.getOriginalFilename().endsWith(".xlsx")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Both files must be in .xlsx format.");
            }

            // Parse the input XLSX files
            List<Employee> employees = excelService.readEmployeesFromXlsx(employeesFile.getInputStream());
            List<SecretSantaAssignment> previousAssignments = excelService.readPreviousAssignmentsFromXlsx(previousAssignmentsFile.getInputStream());

            // check if employees list and assignments list are non-empty
            if (employees.isEmpty() || previousAssignments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("The input files must contain valid employee and previous assignment data.");
            }

            // Generate new assignments
            List<SecretSantaAssignment> assignments = excelService.generateAssignments(employees, previousAssignments);

            // JSON output prints
            String assignmentsJson = objectMapper.writeValueAsString(assignments);
            System.out.println("Generated Secret Santa Assignments"+assignmentsJson); // Log the assignments as JSON

            // Write the assignments to a new XLSX file
            ByteArrayInputStream xlsxOutput = excelService.writeAssignmentsToXlsx(assignments);

            // Log the content of the generated XLSX (for debugging purposes)
            byte[] xlsxBytes = xlsxOutput.readAllBytes();
            String base64Xlsx = java.util.Base64.getEncoder().encodeToString(xlsxBytes);
            System.out.println("Generated XLSX file (Base64):");
            System.out.println(base64Xlsx); // Print the XLSX as Base64 string for debugging

            // Set headers to indicate that the response is an Excel file and should be downloaded
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=secret_santa_assignments.xlsx");

            // Return the generated XLSX file as a byte array
            return ResponseEntity.ok()
                    .headers(headers)  // Set the response headers
                    .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(xlsxBytes);  // Set the body with the file data

        } catch (IOException e) {
            // Handle IOException (e.g., issues with file reading/writing)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the files: " + e.getMessage());
        } catch (Exception e) {
            // General exception handler
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }
}
