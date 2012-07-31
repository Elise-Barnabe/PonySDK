/*
 * Copyright (c) 2011 PonySDK
 *  Owners:
 *  Luciano Broussal  <luciano.broussal AT gmail.com>
 *  Mathieu Barbier   <mathieu.barbier AT gmail.com>
 *  Nicolas Ciaravola <nicolas.ciaravola.pro AT gmail.com>
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

package com.ponysdk.core.service;

import java.util.Calendar;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ponysdk.core.Application;
import com.ponysdk.core.SystemProperty;
import com.ponysdk.core.session.SessionManager;
import com.ponysdk.core.tools.BannerPrinter;

public class ApplicationLoader implements ServletContextListener, HttpSessionListener {

    private static final Logger log = LoggerFactory.getLogger(ApplicationLoader.class);

    private final SessionManager sessionManager = new SessionManager();

    private String applicationName;
    private String applicationDescription;

    @Override
    public void contextInitialized(final ServletContextEvent event) {
        applicationName = System.getProperty(SystemProperty.APPLICATION_NAME);
        applicationDescription = System.getProperty(SystemProperty.APPLICATION_DESCRIPTION);

        event.getServletContext().setAttribute("SESSION_MANAGER", sessionManager);

        printLicence();
    }

    @Override
    public void contextDestroyed(final ServletContextEvent event) {
        printDestroyedBanner();
    }

    @Override
    public void sessionCreated(final HttpSessionEvent arg0) {
        System.err.println("Session created (global Loader) " + arg0.getSession().getId());
        if (log.isDebugEnabled()) {
            log.debug("Session created #" + arg0.getSession().getId());
        }
    }

    @Override
    public void sessionDestroyed(final HttpSessionEvent arg0) {
        System.err.println("Session destroyed (global Loader) " + arg0.getSession().getId());

        final Application applicationSession = (Application) arg0.getSession().getAttribute(Application.class.getCanonicalName());
        applicationSession.fireSessionDestroyed();
    }

    private void printDestroyedBanner() {
        final int columnCount = applicationName.length() + 30;

        final BannerPrinter bannerPrinter = new BannerPrinter(columnCount);
        bannerPrinter.appendNewEmptyLine(2);
        bannerPrinter.appendLineSeparator();
        bannerPrinter.appendNewLine(2);
        bannerPrinter.appendCenteredLine(applicationName + " - Context Destroyed");
        bannerPrinter.appendNewLine(2);
        bannerPrinter.appendLineSeparator();

        log.info(bannerPrinter.toString());
    }

    private void printLicence() {
        String title = "";
        if (applicationName == null && applicationDescription == null) {
            title = "Powered by PonySDK http://www.ponysdk.com";
        } else if (applicationName == null && applicationDescription != null) {
            title = applicationDescription;
        } else if (applicationName == null && applicationDescription != null) {
            title = applicationName;
        } else {
            title = applicationName + " - " + applicationDescription;
        }

        final int columnCount = title.length() + 30;

        final BannerPrinter bannerPrinter = new BannerPrinter(columnCount);
        bannerPrinter.appendNewEmptyLine();
        bannerPrinter.appendLineSeparator();
        bannerPrinter.appendNewLine();
        bannerPrinter.appendCenteredLine(title);
        bannerPrinter.appendNewLine();
        bannerPrinter.appendCenteredLine("WEB  APPLICATION");
        bannerPrinter.appendNewLine();
        bannerPrinter.appendCenteredLine("(c) " + Calendar.getInstance().get(Calendar.YEAR) + " PonySDK");
        bannerPrinter.appendNewLine();
        bannerPrinter.appendLineSeparator();

        log.info(bannerPrinter.toString());
    }
}
