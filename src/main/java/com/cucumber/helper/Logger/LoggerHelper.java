/**
 * @author rahul.rathore
 *	
 *	07-Aug-2016
 */
package com.cucumber.helper.Logger;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


/**
 * @author rakes
 *
 */
public class LoggerHelper {
			
		@SuppressWarnings("rawtypes")
		public static Logger getLogger(Class clazz) {
			return LogManager.getLogger(clazz);
		}	
}
