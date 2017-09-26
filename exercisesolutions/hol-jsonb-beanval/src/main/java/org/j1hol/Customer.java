package org.j1hol;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

public class Customer {

    private String salutation;

    @NotBlank(message = "first name must not be empty")
    private String firstName;
    private String middleName;

    @NotBlank(message = "last name must not be empty")
    private String lastName;

    @PositiveOrZero
    private Integer age;

    public Customer() {
    }

    public Customer(String salutation, String firstName, String middleName, String lastName, Integer age) {
        this.salutation = salutation;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
