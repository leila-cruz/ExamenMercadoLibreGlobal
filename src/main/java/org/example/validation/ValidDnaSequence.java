package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidDnaSequenceValidator.class) // Vincula con el validador
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDnaSequence {
    String message() default "La secuencia de ADN no es válida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}