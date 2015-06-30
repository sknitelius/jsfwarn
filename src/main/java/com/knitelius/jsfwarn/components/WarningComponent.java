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

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import com.knitelius.jsfwarn.validator.ValidationResult;
import com.knitelius.jsfwarn.validator.WarningValidator;

@FacesComponent("warning")
public class WarningComponent extends UIComponentBase {

    /**
     * Default Warning style applied to parent.
     */
    private static final String WARNING_STYLE = "border-color: #FF6E01;";

    @Override
    public String getFamily() {
        return "warningComponent";
    }

    public void executeWarning(FacesContext context) {
        UIInput parent = (UIInput) getParent();
        if (isValueSetOrValidateEmptyValues(parent)) {
            ValidationResult validationResult = executeValidator(context, parent);
            if (validationResult.validationFailed()) {
                context.addMessage(parent.getClientId(), validationResult.getFacesMessage());
                setWarningStyle(parent, context, validationResult);
            } else {
                removeWarningStyle(parent, context);
            }
        } else {
            removeWarningStyle(parent, context);
        }
    }

    private boolean isValueSetOrValidateEmptyValues(UIInput parent) {
        Boolean ignoreNullOrBlank = Boolean.TRUE;
        if (getAttributes().get("ignoreNullOrBlank") != null) {
            ignoreNullOrBlank = (Boolean) getAttributes().get("ignoreNullOrBlank");
        }
        return !(ignoreNullOrBlank && (parent.getValue() == null || "".equals(parent.getValue().toString())));
    }

    private ValidationResult executeValidator(FacesContext context, UIInput parent) {
        WarningValidator warningValidator = (WarningValidator) getAttributes().get("validator");
        final ValidationResult validationResult = new ValidationResult();
        warningValidator.process(context, parent, validationResult);
        return validationResult;
    }

    private void setWarningStyle(UIInput input, FacesContext context, ValidationResult validationResult) {
        removeWarningStyle(input, context);
        if(validationResult.isApplyStyle()) {
            Object style = input.getAttributes().get("style");
            if(style == null || style instanceof String) {
                style = style == null ? getWarningStyle() : getWarningStyle() + ((String)style);
                input.getAttributes().put("style", style);
            }
        }
    }

    private void removeWarningStyle(UIInput input, FacesContext context) {
        Object style = input.getAttributes().get("style");
        if(style instanceof String) {
            style = ((String)style).replaceAll(getWarningStyle(), "");
        }
        input.getAttributes().put("style", style);
    }

    private String getWarningStyle() {
        String style = (String) getAttributes().get("style");
        if (style == null || "".equals(style)) {
            style = WARNING_STYLE;
        }
        return style + ";";
    }
}
