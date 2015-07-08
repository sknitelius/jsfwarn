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
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.StateHelper;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;

@FacesComponent("warning")
public class WarningComponent extends UIComponentBase {

    @Override
    public String getFamily() {
        return "warningComponent";
    }

    public String getJsfWarnStyleClass(FacesMessage.Severity severity) {
        String styleClass = null;
        if (severity != null) {
            styleClass = getStyleClassBySeverity(severity);
        }
        if (styleClass == null || styleClass.equals("")) {
            styleClass = getGlobalStyleClass();
        }
        return styleClass;
    }

    private String getGlobalStyleClass() {
        return (String) getAttributes().get("styleClass");
    }

    private String getStyleClassBySeverity(FacesMessage.Severity severity) {
        return (String) getAttributes().get(severity.toString().toLowerCase() + "Class");
    }

    public void applyJsfWarnStyling(UIInput parent, ValidationResult validationResult) {
        removeJsfWarningStyling(parent);
        if (validationResult.isApplyStyle()) {
            applyJsfWarnStyleClass(validationResult, parent);
            applyJsfWarnInlineStyle(parent);
        }
    }

    private void applyJsfWarnInlineStyle(UIInput parent) {
        Object style = parent.getAttributes().get("style");
        if (style == null || style instanceof String) {
            style = style == null ? getWarningStyle() : getWarningStyle() + ((String) style);
            parent.getAttributes().put("style", style);
        }
    }

    private void applyJsfWarnStyleClass(ValidationResult validationResult, UIInput parent) {
        String jsfWarnStyleClass = getJsfWarnStyleClass(validationResult.getSeverity());
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

    public void removeJsfWarningStyling(UIInput parent) {
        String style = (String) parent.getAttributes().get("style");
        String styleClass = (String) parent.getAttributes().get("styleClass");
        style = style == null ? "" : style;
        styleClass = styleClass == null ? "" : style;
        parent.getAttributes().put("style", style.replaceAll(getWarningStyle(), "").trim());
        parent.getAttributes().put("styleClass", removeJsfWarnStyleClasses(styleClass));
    }

    public String getWarningStyle() {
        String style = (String) getAttributes().get("style");
        style = style == null ? "" : style;
        return style.contains(";") ? style : style + ";";
    }

    public Object getWarningValidator() {
        return getAttributes().get("validator");
    }

    private String removeJsfWarnStyleClasses(String styleClass) {
        styleClass = styleClass.replaceAll(WarningComponent.this.getJsfWarnStyleClass(), "");
        styleClass = styleClass.replaceAll(getJsfWarnInfoClass(), "");
        styleClass = styleClass.replaceAll(getJsfWarnWarnClass(), "");
        styleClass = styleClass.replaceAll(getJsfWarnErrorClass(), "");
        styleClass = styleClass.replaceAll(getJsfWarnFatalClass(), "");
        return styleClass.trim();
    }

    public String getJsfWarnStyleClass() {
        final String styleClass = (String) getAttributes().get("styleClass");
        return styleClass == null ? "" : styleClass;
    }

    public String getJsfWarnInfoClass() {
        final String styleClass = (String) getAttributes().get("styleClass");
        return styleClass == null ? "" : styleClass;
    }

    public String getJsfWarnWarnClass() {
        final String styleClass = (String) getAttributes().get("styleClass");
        return styleClass == null ? "" : styleClass;
    }

    public String getJsfWarnErrorClass() {
        final String styleClass = (String) getAttributes().get("styleClass");
        return styleClass == null ? "" : styleClass;
    }

    public String getJsfWarnFatalClass() {
        final String styleClass = (String) getAttributes().get("styleClass");
        return styleClass == null ? "" : styleClass;
    }

}
