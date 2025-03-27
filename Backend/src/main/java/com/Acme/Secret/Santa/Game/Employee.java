package com.Acme.Secret.Santa.Game;

/**
 * Represents an employee participating in the Secret Santa game.
 * This class holds the name and email of the employee.
 */
public class Employee {

    /**
     * The name of the employee.
     */
    private String name;

    /**
     * The email address of the employee.
     */
    private String email;

    /**
     * Constructs an Employee instance with the given name and email.
     * 
     * @param name  The name of the employee.
     * @param email The email address of the employee.
     */
    public Employee(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Gets the name of the employee.
     * 
     * @return The name of the employee.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the employee.
     * 
     * @param name The name of the employee.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email address of the employee.
     * 
     * @return The email address of the employee.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the employee.
     * 
     * @param email The email address of the employee.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
