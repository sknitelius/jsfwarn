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

public class StyleProcessor {

    public static void applyJsfWarnStyling(WarningComponent component, UIInput parent, ValidationResult validationResult) {
        StylableUIComponent stylableUIComponent = new StylableUIComponent(parent);

        removeJsfWarningStyling(component, stylableUIComponent);
        if (validationResult.isApplyStyle()) {
            applyJsfWarnStyleClass(component, stylableUIComponent, validationResult);
            applyJsfWarnInlineStyle(component, stylableUIComponent);
        }
    }

    public static void removeJsfWarningStyling(WarningComponent component, StylableUIComponent parent) {
        String style = parent.getStyle();
        String styleClass = parent.getStyleClass();
        style = style == null ? "" : style;
        styleClass = styleClass == null ? "" : styleClass;
        parent.setStyle(style.replaceAll(component.getWarningStyle(), "").trim());
        parent.setStyleClass(removeJsfWarnStyleClasses(component, styleClass));
    }

    private static void applyJsfWarnInlineStyle(WarningComponent component, StylableUIComponent parent) {
        parent.setStyle(component.getWarningStyle() + parent.getStyle());
    }

    private static void applyJsfWarnStyleClass(WarningComponent component, StylableUIComponent parent, ValidationResult validationResult) {
        String jsfWarnStyleClass = component.getJsfWarnStyleClass(validationResult.getSeverity());
        parent.applyStyleClass(jsfWarnStyleClass);
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
