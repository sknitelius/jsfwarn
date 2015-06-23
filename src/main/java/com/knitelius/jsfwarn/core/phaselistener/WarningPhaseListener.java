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
package com.knitelius.jsfwarn.core.phaselistener;

import com.knitelius.jsfwarn.core.components.WarningComponent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class WarningPhaseListener implements PhaseListener {

    private static final long serialVersionUID = 1590057819828203795L;

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterPhase(PhaseEvent event) {
        // nothing to do after render response.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void beforePhase(PhaseEvent event) {
        FacesContext context = event.getFacesContext();
        executeWarnings(context, context.getViewRoot());
    }

    public static void executeWarnings(FacesContext context, UIComponent parent) {
        for (UIComponent child : parent.getChildren()) {
            if (WarningComponent.class.isAssignableFrom(child.getClass())) {
                (WarningComponent.class.cast(child)).executeWarning(context);
            }
            executeWarnings(context, child);
        }
    }

    /**
     * {@inheritDoc}
     */
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
}
