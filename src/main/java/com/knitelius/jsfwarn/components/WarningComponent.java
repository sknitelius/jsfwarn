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

import javax.faces.application.FacesMessage;

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

    public String getWarningStyle() {
        String style = (String) getAttributes().get("style");
        style = style == null ? "" : style;
        return style.contains(";") ? style : style + ";";
    }

    public Object getWarningValidator() {
        return getAttributes().get("validator");
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
