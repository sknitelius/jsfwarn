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
package com.knitelius.jsfwarn.components;

import com.knitelius.jsfwarn.validator.ValidationResult;
import com.knitelius.jsfwarn.validator.WarningValidator;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

public class TestValidator implements WarningValidator {

    private final boolean fail;
    private final boolean applyStyle;
    private String warningMessage = "random message";
    
    
    public TestValidator(boolean fail, boolean applyStyle) {
        this.fail = fail;
        this.applyStyle = applyStyle;
    }

    @Override
    public void process(FacesContext context, UIInput component, ValidationResult validationResult) {
        if (fail) {
            validationResult.setFacesMessage(new FacesMessage(FacesMessage.SEVERITY_WARN, warningMessage, warningMessage));
        }
        validationResult.setApplyStyle(applyStyle);
    }

    public void setWarningMessage(String warningMessage) {
        this.warningMessage = warningMessage;
    }
}
