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
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIInput;

public class StyleProcessor {

    public static void applyJsfWarnStyling(WarningComponent component, UIInput parent, ValidationResult validationResult) {
        removeJsfWarningStyling(component, parent);
        if (validationResult.isApplyStyle()) {
            applyJsfWarnStyleClass(component, parent, validationResult);
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

    private static void applyJsfWarnStyleClass(WarningComponent component, UIInput parent, ValidationResult validationResult) {
        String jsfWarnStyleClass = component.getJsfWarnStyleClass(validationResult.getSeverity());

        StyleMethods styleMethods = findStyleMethods(parent);
        if (styleMethods.found()) {
            styleMethods.applyStyle(parent, jsfWarnStyleClass);
        } else {
            String parentStyleClass = (String) parent.getAttributes().get("styleClass");
            parent.getAttributes().put("styleClass", jsfWarnStyleClass + " " + parentStyleClass);
        }
    }

    private static StyleMethods findStyleMethods(UIInput parent) {
        Method[] declaredMethods = parent.getClass().getDeclaredMethods();
        StyleMethods styleMethods = new StyleMethods();
        for (Method method : declaredMethods) {
            if ("setStyleClass".equals(method.getName())) {
                styleMethods.setStyleMethod = method;
            } else if ("getStyleClass".equals(method.getName())) {
                styleMethods.getStyleMethod = method;
            }
            if (styleMethods.found()) {
                break;
            }
        }
        return styleMethods;
    }

    private static String removeJsfWarnStyleClasses(WarningComponent component, String styleClass) {
        styleClass = styleClass.replaceAll(component.getJsfWarnStyleClass(), "");
        styleClass = styleClass.replaceAll(component.getJsfWarnInfoClass(), "");
        styleClass = styleClass.replaceAll(component.getJsfWarnWarnClass(), "");
        styleClass = styleClass.replaceAll(component.getJsfWarnErrorClass(), "");
        styleClass = styleClass.replaceAll(component.getJsfWarnFatalClass(), "");
        return styleClass.trim();
    }

    private static class StyleMethods {

        Method setStyleMethod = null;
        Method getStyleMethod = null;

        void applyStyle(UIInput parent, String jsfWarnStyleClass) {
            try {
                String parentStyleClass = (String) getStyleMethod.invoke(parent);
                setStyleMethod.invoke(parent, jsfWarnStyleClass + " " + parentStyleClass);
            } catch (Exception ex) {
                Logger.getLogger(StyleProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        boolean found() {
            return setStyleMethod != null && getStyleMethod != null;
        }
    }
}
