/*
 * Copyright 2015 Stephan Knitelius.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.knitelius.jsfwarn.processor;

import com.knitelius.jsfwarn.validator.ValidationResult;
import com.knitelius.jsfwarn.validator.WarningValidator;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class ValidationProcessor {
    public static ValidationResult execute(Object validator, FacesContext context, UIInput parent) {
        ValidationResult validationResult = new ValidationResult();
        if(validator instanceof WarningValidator) {
            handleWarningValidator(validator, context, parent, validationResult);
        } else if(validator instanceof Validator) {
            handleJsfValidator(validator, context, parent, validationResult); 
        } else {
            throw new IllegalArgumentException("Validator of type " + validator.getClass() + "is not supported.");
        }
        return validationResult;
    }

    private static void handleWarningValidator(Object validator, FacesContext context, UIInput parent, ValidationResult validationResult) {
        ((WarningValidator)validator).process(context, parent, validationResult);
    }

    private static void handleJsfValidator(Object validator, FacesContext context, UIInput parent, ValidationResult validationResult) {
        try{
            ((Validator)validator).validate(context, parent, parent.getValue());
        } catch(ValidatorException ve) {
            validationResult.setFacesMessage(ve.getFacesMessage());
        }
    }
}
