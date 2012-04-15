/*
 * Copyright (c) 2011 PonySDK
 *  Owners:
 *  Luciano Broussal  <luciano.broussal AT gmail.com>
 *	Mathieu Barbier   <mathieu.barbier AT gmail.com>
 *	Nicolas Ciaravola <nicolas.ciaravola.pro AT gmail.com>
 *  
 *  WebSite:
 *  http://code.google.com/p/pony-sdk/
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.ponysdk.impl.webapplication.menu;

import com.ponysdk.core.activity.AbstractActivity;
import com.ponysdk.impl.webapplication.application.ApplicationActivity;
import com.ponysdk.impl.webapplication.page.InitializingActivity;
import com.ponysdk.impl.webapplication.page.PageActivity;
import com.ponysdk.impl.webapplication.page.PageProvider;
import com.ponysdk.impl.webapplication.page.event.PageDisplayedEvent;
import com.ponysdk.impl.webapplication.page.event.PageDisplayedEvent.PageDisplayHandler;
import com.ponysdk.ui.server.basic.PAcceptsOneWidget;
import com.ponysdk.ui.server.basic.event.PSelectionEvent;
import com.ponysdk.ui.server.basic.event.PSelectionHandler;

public class MenuActivity extends AbstractActivity implements PageDisplayHandler, InitializingActivity, PSelectionHandler<String> {

    private MenuView menuView;
    private ApplicationActivity applicationActivity;
    private PageProvider pageProvider;

    @Override
    public void onPageDisplayed(final PageDisplayedEvent event) {
        if (event.getSource().equals(this)) return;

        final PageActivity pageActivity = event.getPageActivity();
        if (pageActivity.getPageName() != null) {
            menuView.selectItem(pageActivity.getPageCategories(), pageActivity.getPageName());
        }
    }

    @Override
    public void start(final PAcceptsOneWidget world) {
        world.setWidget(menuView);
        for (final PageActivity pageActivity : pageProvider.getPageActivities()) {
            menuView.addItem(pageActivity.getPageCategories(), pageActivity.getPageName());
        }
    }

    @Override
    public void onSelection(final PSelectionEvent<String> event) {
        selectItem(event.getSelectedItem());
    }

    public void selectItem(final String pageName) {
        final PageActivity pageActivity = pageProvider.getPageActivity(pageName);
        if (pageActivity != null) {
            applicationActivity.goTo(pageActivity.getDefautHomePagePlace());
        }
    }

    public void setMenuView(final MenuView menuView) {
        this.menuView = menuView;
    }

    public ApplicationActivity getApplicationActivity() {
        return applicationActivity;
    }

    public void setApplicationActivity(final ApplicationActivity applicationActivity) {
        this.applicationActivity = applicationActivity;
    }

    public PageProvider getPageProvider() {
        return pageProvider;
    }

    public void setPageProvider(final PageProvider pageProvider) {
        this.pageProvider = pageProvider;
    }

    public MenuView getMenuView() {
        return menuView;
    }

    @Override
    public void afterContextInitialized() {
        menuView.addSelectionHandler(this);
        addHandler(PageDisplayedEvent.TYPE, this);
    }

}
