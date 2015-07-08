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
import com.knitelius.jsfwarn.validator.ValidationResult;
import javax.faces.component.UIInput;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;

public class StyleProcessor {

    public static void applyJsfWarnStyling(WarningComponent component, UIInput parent, ValidationResult validationResult) {
        removeJsfWarningStyling(component, parent);
        if (validationResult.isApplyStyle()) {
            applyJsfWarnStyleClass(component, validationResult, parent);
            applyJsfWarnInlineStyle(component, parent);
        }
    }

    public static void removeJsfWarningStyling(WarningComponent component, UIInput parent) {
        String style = (String) parent.getAttributes().get("style");
        String styleClass = (String) parent.getAttributes().get("styleClass");
        style = style == null ? "" : style;
        styleClass = styleClass == null ? "" : style;
        parent.getAttributes().put("style", style.replaceAll(component.getWarningStyle(), "").trim());
        parent.getAttributes().put("styleClass", removeJsfWarnStyleClasses(component, styleClass));
    }

    private static void applyJsfWarnInlineStyle(WarningComponent component, UIInput parent) {
        Object style = parent.getAttributes().get("style");
        if (style == null || style instanceof String) {
            style = style == null ? component.getWarningStyle() : component.getWarningStyle() + ((String) style);
            parent.getAttributes().put("style", style);
        }
    }

    private static void applyJsfWarnStyleClass(WarningComponent component, ValidationResult validationResult, UIInput parent) {
        String jsfWarnStyleClass = component.getJsfWarnStyleClass(validationResult.getSeverity());
        if (jsfWarnStyleClass != null) {
            if (parent instanceof HtmlInputText) {
                final HtmlInputText parentType = (HtmlInputText) parent;
                String parentStyleClass = parentType.getStyleClass();
                parentType.setStyleClass(jsfWarnStyleClass + " " + parentStyleClass);
            } else if (parent instanceof HtmlInputSecret) {
                final HtmlInputSecret parentType = (HtmlInputSecret) parent;
                String parentStyleClass = parentType.getStyleClass();
                parentType.setStyleClass(jsfWarnStyleClass + " " + parentStyleClass);
            } else if (parent instanceof HtmlSelectBooleanCheckbox) {
                final HtmlSelectBooleanCheckbox parentType = (HtmlSelectBooleanCheckbox) parent;
                String parentStyleClass = parentType.getStyleClass();
                parentType.setStyleClass(jsfWarnStyleClass + " " + parentStyleClass);
            }
        }
    }

    private static String removeJsfWarnStyleClasses(WarningComponent component, String styleClass) {
        styleClass = styleClass.replaceAll(component.getJsfWarnStyleClass(), "");
        styleClass = styleClass.replaceAll(component.getJsfWarnInfoClass(), "");
        styleClass = styleClass.replaceAll(component.getJsfWarnWarnClass(), "");
        styleClass = styleClass.replaceAll(component.getJsfWarnErrorClass(), "");
        styleClass = styleClass.replaceAll(component.getJsfWarnFatalClass(), "");
        return styleClass.trim();
    }
}
