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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WarningProcessorIgnoreNullOrEmptyTest extends AbstractWarningProcessorTest {

    @Test
    public void test_validator_not_executed_because_value_is_empty() {
        Mockito.when(parent.getValue()).thenReturn("");
        //FIXME - after refactor.
    }

    @Test
    public void test_validator_not_executed_because_value_is_null() {
        Mockito.when(parent.getValue()).thenReturn(null);
        //FIXME - after refactor.
    }

    @Test
    public void test_validator_excuted_because_value_is_non_empty() {
        //FIXME - after refactor.
    }

    @Test
    public void test_validator_excuted_because_ignoreNullOrBlank_is_false() {
        Mockito.when(parent.getValue()).thenReturn("");
        warningComponent.getAttributes().put("ignoreNullOrBlank", false);
        //FIXME - after refactor.
    }
}
