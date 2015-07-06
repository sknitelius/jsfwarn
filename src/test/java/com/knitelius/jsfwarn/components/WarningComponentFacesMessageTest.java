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

import javax.faces.application.FacesMessage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WarningComponentFacesMessageTest extends AbstractWarningComponetTest {
    
    @Captor
    private ArgumentCaptor<FacesMessage> messageCaptor;
    
    @Test
    public void test_message_applied_to_facescontext_when_validator_fails() {
        final TestValidator testValidator = new TestValidator(true, true);
        testValidator.setWarningMessage("I am the one who knocks.");
        Mockito.when(parent.getValue()).thenReturn("foobar");
        warningComponent.getAttributes().put("validator", testValidator);

        warningComponent.executeWarning(context);
        
        Mockito.verify(context).addMessage(Mockito.any(String.class), messageCaptor.capture());
        assertEquals("I am the one who knocks.", messageCaptor.getValue().getSummary());
    }
    
    @Test
    public void test_no_message_added_when_validator_passes() {
        final TestValidator testValidator = new TestValidator(false, true);
        Mockito.when(parent.getValue()).thenReturn("foobar");
        warningComponent.getAttributes().put("validator", testValidator);

        warningComponent.executeWarning(context);
        
        Mockito.verify(context, Mockito.never()).addMessage(Mockito.any(String.class), Mockito.any(FacesMessage.class));
    }
}
