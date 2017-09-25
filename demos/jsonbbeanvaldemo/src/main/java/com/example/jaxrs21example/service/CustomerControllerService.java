package com.example.jaxrs21example.service;

import com.example.jaxrs21example.dto.Customer;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/customercontroller")
public class CustomerControllerService {

    private static final Logger LOG = Logger.getLogger(CustomerControllerService.class.getName());

    @Inject
    private Validator validator;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCustomer(String customerJson) {
        LOG.log(Level.INFO, String.format("addCustomer() invoked with argument %s", customerJson));
        Response response;

        Jsonb jsonb = JsonbBuilder.create();

        Customer customer = jsonb.fromJson(customerJson, Customer.class);
        LOG.log(Level.INFO, "Customer object populated from JSON");
        LOG.log(Level.INFO, String.format("%s %s %s %s %s", customer.getSalutation(),
                customer.getFirstName(),
                customer.getMiddleName(),
                customer.getLastName(),
                customer.getDateOfBirth()));

        Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(customer);

        if (constraintViolations != null && !constraintViolations.isEmpty()) {
            constraintViolations.forEach(
                    constraintViolation -> LOG.log(Level.SEVERE, constraintViolation.getMessage()));
            response = Response.serverError().build();
        } else {
            response = Response.ok("{}").build();
        }

        return response;
    }

}
