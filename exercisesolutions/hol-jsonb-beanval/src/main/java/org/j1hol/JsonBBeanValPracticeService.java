package org.j1hol;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("jsonbbeanval")
public class JsonBBeanValPracticeService {

    private List<@Valid Customer> customerList;

    @Inject
    private Validator validator;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson() {
        Response response;
        populateCustomerList();
        Jsonb jsonb = JsonbBuilder.create();
        CustomerContainer customerContainer = new CustomerContainer();
        
        customerContainer.setCustomerList(customerList);

        Set<ConstraintViolation<CustomerContainer>> constraintViolations = validator.validate(customerContainer);

        if (!constraintViolations.isEmpty()) {
            constraintViolations.forEach(
                    constraintViolation -> System.out.println(constraintViolation.getMessage()));

            response = Response.serverError().build();
        } else {
            String json = jsonb.toJson(customerList);
            response = Response.ok(json).build();
        }

        return response;
    }

    private void populateCustomerList() {
        customerList = new ArrayList<>();

        customerList.add(new Customer("Mrs", "Trisha", null, "Gee", 20));
        customerList.add(new Customer("Dr", "James", null, "Gosling", 30));
        customerList.add(new Customer("Mr", "Don", null, "Smith", 30));
    }
}
