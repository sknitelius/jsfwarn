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

import com.knitelius.jsfwarn.validator.WarningValidator;
import java.util.HashMap;
import java.util.Map;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

public class AbstractWarningComponetTest {

    @Mock
    protected FacesContext context;

    @Mock
    protected UIInput parent;

    @Mock
    protected WarningValidator validator;

    @InjectMocks
    protected WarningComponent warningComponent;

    protected Map<String, Object> parentAttributes;
    
    @Before
    public void globalInit() {
        warningComponent.getAttributes().put("validator", validator);
        parentAttributes = new HashMap<String, Object>();
        Mockito.when(parent.getAttributes()).thenReturn(parentAttributes);
    }
}
