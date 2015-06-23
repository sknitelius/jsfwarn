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
package com.knitelius.jsfwarn.core.validator;

import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

/**
 * <p>
 * A <strong>WarningValidator</strong> implementation is a class that can
 * perform validations that will only display warning messages. A
 * <code>WarningValidator</code> can be associated UIInput in the view, they are
 * called before <em>Render Response</em>.
 * </p>
 * <p>
 * Unlike {@link javax.faces.validator.Validator}s, {@link WarningValidator}s
 * can use the model and applications state, since both Model Update and Invoke
 * Application are performed before the Warnings are evaluated.
 * <p/>
 * <p/>
 * <p>
 * {@link WarningValidator} implementations must have a zero-arguments public
 * constructor.
 * </p>
 */
public interface WarningValidator {

  /**
   * Executes the targeted validation.
   * 
   * @param context - FacesContext.
   * @param component - the component to be validated.
   * @return warningMsg - optional warning message, if the validation fails.
   */
  public ValidationResult process(FacesContext context, UIInput component);

}
