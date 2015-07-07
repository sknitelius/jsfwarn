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

import com.knitelius.jsfwarn.validator.ValidationResult;

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

    public void setWarningStyle(UIInput input, ValidationResult validationResult) {
        removeWarningStyle(input);
        if(validationResult.isApplyStyle()) {
            Object style = input.getAttributes().get("style");
            if(style == null || style instanceof String) {
                style = style == null ? getWarningStyle() : getWarningStyle() + ((String)style);
                input.getAttributes().put("style", style);
            }
        }
    }

    public void removeWarningStyle(UIInput input) {
        Object style = input.getAttributes().get("style");
        if(style instanceof String) {
            style = ((String)style).replaceAll(getWarningStyle(), "").trim();
        }
        input.getAttributes().put("style", style);
    }

    public String getWarningStyle() {
        String style = (String) getAttributes().get("style");
        if (style == null || "".equals(style)) {
            style = WARNING_STYLE;
        }
        return style.contains(";") ? style : style + ";";
    }

    public Object getWarningValidator() {
        return getAttributes().get("validator");
    }
}
