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

import com.knitelius.jsfwarn.components.WarningComponent;
import com.knitelius.jsfwarn.components.wrapper.StylableUIComponent;
import com.knitelius.jsfwarn.validator.ValidationResult;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

public class WarningProcessor {

    public static void process(FacesContext context, WarningComponent warningComponent) {
        final UIInput parent = (UIInput) warningComponent.getParent();
        if (proceedWithValidation(warningComponent, parent)) {
            ValidationResult validationResult = ValidationProcessor.execute(warningComponent.getWarningValidator(), context, parent);
            if (validationResult.validationFailed()) {
                context.addMessage(parent.getClientId(), validationResult.getFacesMessage());
                StyleProcessor.applyJsfWarnStyling(warningComponent, parent, validationResult);
            } else {
                StyleProcessor.removeJsfWarningStyling(warningComponent, new StylableUIComponent(parent));
            }
        } else {
            StyleProcessor.removeJsfWarningStyling(warningComponent, new StylableUIComponent(parent));
        }
    }

    private static boolean proceedWithValidation(WarningComponent warningComponent, UIInput parent) {
        Boolean ignoreNullOrBlank = Boolean.TRUE;
        if (warningComponent.getAttributes().get("ignoreNullOrBlank") != null) {
            ignoreNullOrBlank = (Boolean) warningComponent.getAttributes().get("ignoreNullOrBlank");
        }
        return !(ignoreNullOrBlank && (parent.getValue() == null || "".equals(parent.getValue().toString())));
    }
}
