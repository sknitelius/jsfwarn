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
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class WarningComponentStyleTest extends AbstractWarningComponetTest {
        
    @Test
    public void test_default_style_applied_on_failure() {
        Mockito.when(parent.getValue()).thenReturn("foobar");
        warningComponent.getAttributes().put("validator", new TestValidator(true, true));
        
        warningComponent.executeWarning(context);
        
        assertEquals("border-color: #FF6E01;", parent.getAttributes().get("style"));
    }

    @Test
    public void test_default_style_removed_when_value_blank() {
        parent.getAttributes().put("style", "border-color: #FF6E01;");
        Mockito.when(parent.getValue()).thenReturn("");
        warningComponent.getAttributes().put("validator", new TestValidator(true, true));
        
        warningComponent.executeWarning(context);
        
        assertEquals("", parent.getAttributes().get("style"));
    }
    
    @Test
    public void test_default_style_removed_when_validation_passes() {
        parent.getAttributes().put("style", "border-color: #FF6E01;");
        Mockito.when(parent.getValue()).thenReturn("foobar");
        warningComponent.getAttributes().put("validator", new TestValidator(false, true));
        
        warningComponent.executeWarning(context);
        
        assertEquals("", parent.getAttributes().get("style"));
    }
    
    @Test
    public void test_no_style_applied_when_apply_style_is_false() {
        Mockito.when(parent.getValue()).thenReturn("foobar");
        warningComponent.getAttributes().put("validator", new TestValidator(true, false));
        
        warningComponent.executeWarning(context);
        
        assertEquals(null, parent.getAttributes().get("style"));
    }

    @Test
    public void test_style_removed_when_apply_style_is_false() {
        parent.getAttributes().put("style", "border-color: #FF6E01;");
        Mockito.when(parent.getValue()).thenReturn("foobar");
        warningComponent.getAttributes().put("validator", new TestValidator(true, false));
        
        warningComponent.executeWarning(context);
        
        assertEquals("", parent.getAttributes().get("style"));
    }
    
    @Test
    public void test_only_warning_style_removed() {
        parent.getAttributes().put("style", "border-color: #FF6E01; foo: bar;");
        Mockito.when(parent.getValue()).thenReturn("foobar");
        warningComponent.getAttributes().put("validator", new TestValidator(false, true));
        
        warningComponent.executeWarning(context);
        
        assertEquals("foo: bar;", parent.getAttributes().get("style"));
    }
    
    @Test
    public void test_warning_style_appended_to_other_styles() {
        parent.getAttributes().put("style", "foo: bar;");
        Mockito.when(parent.getValue()).thenReturn("foobar");
        warningComponent.getAttributes().put("validator", new TestValidator(true, true));
        
        warningComponent.executeWarning(context);
        
        assertEquals("border-color: #FF6E01;foo: bar;", parent.getAttributes().get("style"));
    }
    
    class TestValidator implements WarningValidator {
        private final boolean fail;
        private final boolean applyStyle;
        
        public TestValidator(boolean fail, boolean applyStyle) {
            this.fail = fail;
            this.applyStyle = applyStyle;
        }
        
        @Override
        public void process(FacesContext context, UIInput component, ValidationResult validationResult) {
            if(fail) {
                validationResult.setFacesMessage(new FacesMessage(FacesMessage.SEVERITY_WARN, "warning", "warning"));
            } 
            validationResult.setApplyStyle(applyStyle);
        }
        
    }
}
