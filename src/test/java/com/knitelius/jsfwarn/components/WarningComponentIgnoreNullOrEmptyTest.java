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
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WarningComponentIgnoreNullOrEmptyTest {

    @Mock
    private FacesContext context;

    @Mock
    private UIInput parent;

    @Mock
    private WarningValidator validator;

    @InjectMocks
    private WarningComponent warningComponent;

    @Before
    public void init() {
        warningComponent.getAttributes().put("validator", validator);
    }

    @Test
    public void test_validator_not_executed_because_value_is_empty() {
        Mockito.when(parent.getValue()).thenReturn("");
        warningComponent.executeWarning(context);
        Mockito.verifyZeroInteractions(validator);
    }

    @Test
    public void test_validator_not_executed_because_value_is_null() {
        Mockito.when(parent.getValue()).thenReturn(null);
        warningComponent.executeWarning(context);
        Mockito.verifyZeroInteractions(validator);
    }

    @Test
    public void test_validator_excuted_because_value_is_non_empty() {
        Mockito.when(parent.getValue()).thenReturn("foobar");
        warningComponent.executeWarning(context);
        Mockito.verify(validator).process(Mockito.eq(context), Mockito.eq(parent), Mockito.<ValidationResult>any());
    }

    @Test
    public void test_validator_excuted_because_ignoreNullOrBlank_is_false() {
        Mockito.when(parent.getValue()).thenReturn("");
        warningComponent.getAttributes().put("ignoreNullOrBlank", false);
        warningComponent.executeWarning(context);
        Mockito.verify(validator).process(Mockito.eq(context), Mockito.eq(parent), Mockito.<ValidationResult>any());
    }
}
