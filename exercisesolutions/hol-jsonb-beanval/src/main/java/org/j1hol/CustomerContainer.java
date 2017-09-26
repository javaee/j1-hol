package org.j1hol;

import java.util.List;
import javax.validation.Valid;

public class CustomerContainer {

    private List<@Valid Customer> customerList;

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

}
