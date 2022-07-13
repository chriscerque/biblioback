package net.ent.etrs.biblioback.utils.exceptions;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ValidException extends Exception {

    public Map<String, ArrayList<String>> mapViolations = new HashMap<>();

    public ValidException(Set<ConstraintViolation<Object>> violations) {
        super();

        for (ConstraintViolation<Object> violation : violations) {
            String property = violation.getPropertyPath().toString();
            if (!this.mapViolations.containsKey(property)) {
                this.mapViolations.put(property, new ArrayList<>());
            }
            this.mapViolations.get(property).add(violation.getMessage());
            System.out.println(property + " - " + violation.getMessage());
        }
    }

}
