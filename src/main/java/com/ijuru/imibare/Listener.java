package com.ijuru.imibare;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Listens for webapp stop/start
 */
public class Listener implements ServletContextListener {

	protected static final Logger log = LogManager.getLogger(Listener.class);

	/**
	 * Web application is being deployed or started
	 */
	public void contextInitialized(ServletContextEvent event) {
		log.info("Initializing Wordify");

		try {
			Context.startApp();

		} catch (Exception ex) {
			log.error("Unable to start application", ex);
		}
	}

	/**
	 * Web application is being undeployed or stopped
	 */
	public void contextDestroyed(ServletContextEvent event) {
		log.info("Destroying Wordify");

		Context.destroyApp();
	}
}