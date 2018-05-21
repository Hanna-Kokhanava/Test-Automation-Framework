package com.linkedin.automation.services.ui.handlers.scroll.constraints;

import com.linkedin.automation.services.ui.handlers.scroll.ScrollVector;

public abstract class ElementSearcher extends Constraint {

    @Override
    public Boolean apply(ScrollVector scrollVector) {
        return !check();
    }

    public abstract boolean check();
}
