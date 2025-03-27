package com.Acme.Secret.Santa.Game;

/**
 * Represents a Secret Santa assignment between an employee and their secret child.
 * This class holds information about the employee and the person they are assigned to give a gift to.
 */
public class SecretSantaAssignment {
    
    /**
     * The name of the employee who is assigning a gift.
     */
    private String employeeName;
    
    /**
     * The email of the employee who is assigning a gift.
     */
    private String employeeEmail;
    
    /**
     * The name of the person to whom the employee is assigned to give a gift.
     */
    private String secretChildName;
    
    /**
     * The email of the person to whom the employee is assigned to give a gift.
     */
    private String secretChildEmail;

    /**
     * Constructs a SecretSantaAssignment with the specified details.
     * 
     * @param employeeName    The name of the employee who is assigning a gift.
     * @param employeeEmail   The email address of the employee who is assigning a gift.
     * @param secretChildName The name of the person to whom the employee is assigned to give a gift.
     * @param secretChildEmail The email address of the person to whom the employee is assigned to give a gift.
     */
    public SecretSantaAssignment(String employeeName, String employeeEmail, String secretChildName, String secretChildEmail) {
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.secretChildName = secretChildName;
        this.secretChildEmail = secretChildEmail;
    }

    /**
     * Gets the name of the employee.
     * 
     * @return The name of the employee.
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * Sets the name of the employee.
     * 
     * @param employeeName The name of the employee.
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    /**
     * Gets the email of the employee.
     * 
     * @return The email of the employee.
     */
    public String getEmployeeEmail() {
        return employeeEmail;
    }

    /**
     * Sets the email of the employee.
     * 
     * @param employeeEmail The email of the employee.
     */
    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    /**
     * Gets the name of the secret child (the person to whom the employee is assigned).
     * 
     * @return The name of the secret child.
     */
    public String getSecretChildName() {
        return secretChildName;
    }

    /**
     * Sets the name of the secret child (the person to whom the employee is assigned).
     * 
     * @param secretChildName The name of the secret child.
     */
    public void setSecretChildName(String secretChildName) {
        this.secretChildName = secretChildName;
    }

    /**
     * Gets the email of the secret child (the person to whom the employee is assigned).
     * 
     * @return The email of the secret child.
     */
    public String getSecretChildEmail() {
        return secretChildEmail;
    }

    /**
     * Sets the email of the secret child (the person to whom the employee is assigned).
     * 
     * @param secretChildEmail The email of the secret child.
     */
    public void setSecretChildEmail(String secretChildEmail) {
        this.secretChildEmail = secretChildEmail;
    }
}
