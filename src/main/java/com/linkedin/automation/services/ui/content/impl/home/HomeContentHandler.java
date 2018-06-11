package com.linkedin.automation.services.ui.content.impl.home;

import com.linkedin.automation.core.logger.Logger;
import com.linkedin.automation.pages.content.elements.impl.home.HomeContentBlock;
import com.linkedin.automation.services.ui.handlers.AbstractBlockHandler;

public class HomeContentHandler extends AbstractBlockHandler<HomeContentBlock> {

    public boolean isHomePageLoaded() {
        Logger.info("Check if Home page is loaded correctly");
        return getCheckedBlock().isCorrect();
    }
}
