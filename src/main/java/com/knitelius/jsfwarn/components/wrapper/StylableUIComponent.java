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
package com.knitelius.jsfwarn.components.wrapper;

import com.knitelius.jsfwarn.processor.StyleProcessor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;

/**
 * Wrapper for UIComponents, many of these (like HTMLInputText) possess common methods (such as setStyleClass).
 * Sadly they do not possess a common interface or parent that could be used via "instanceof". 
 * 
 * This wrapper allows common access to the style and styleClass attribute via reflection.
 * 
 * @author Stephan Knitelius
 */
public class StylableUIComponent {

    private final UIComponent component;

    public StylableUIComponent(UIComponent component) {
        this.component = component;
    }

    public void applyStyleClass(String styleClass) {
        try {
            String parentStyleClass = (String) getStyleClassMethod().invoke(component);
            setStyleClassMethod().invoke(component, styleClass + " " + parentStyleClass);
        } catch (Exception ex) {
            String parentStyleClass = (String) component.getAttributes().get("styleClass");
            component.getAttributes().put("styleClass", styleClass + " " + parentStyleClass);
            Logger.getLogger(StyleProcessor.class.getName()).log(Level.WARNING, null, ex);
        }
    }
    
    public void setStyle(String style) {
        try {
            setStyleMethod().invoke(component, style);
        } catch (Exception e) {
            component.getAttributes().put("style", style);
        }
    }
    
    public String getStyle() {
        try {
            return (String) getStyleMethod().invoke(component);
        } catch (Exception e) {
            return (String) component.getAttributes().get("style");
        }
    }

    public void setStyleClass(String styleClass) {
        try {
            setStyleClassMethod().invoke(component, styleClass);
        } catch (Exception ex) {
            component.getAttributes().put("styleClass", styleClass);
            Logger.getLogger(StylableUIComponent.class.getName()).log(Level.WARNING, null, ex);
        }
    }

    public String getStyleClass() {
        try {
            return (String) getStyleClassMethod().invoke(component);
        } catch (Exception ex) {
            return (String) component.getAttributes().get("styleClass");
        }
    }

    public Map<String, Object> getAttributes() {
        return component.getAttributes();
    }
    
    private Method setStyleMethod() throws NoSuchMethodException {
        return component.getClass().getDeclaredMethod("setStyle", String.class);
    }

    private Method getStyleMethod() throws NoSuchMethodException {
        return component.getClass().getDeclaredMethod("getStyle", (Class<?>[]) null);
    }

    private Method setStyleClassMethod() throws NoSuchMethodException {
        return component.getClass().getDeclaredMethod("setStyleClass", String.class);
    }

    private Method getStyleClassMethod() throws NoSuchMethodException {
        return component.getClass().getDeclaredMethod("getStyleClass", (Class<?>[]) null);
    }
}
