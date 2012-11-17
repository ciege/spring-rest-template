package org.dedeler.template.service.impl;

import org.dedeler.template.service.LoggingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggingServiceImpl implements LoggingService {

	private static final Logger defaultLogger = LoggerFactory.getLogger("default");
	private static final Logger controllerLogger = LoggerFactory.getLogger("controller");
	private static final Logger daoLogger = LoggerFactory.getLogger("dao");
	private static final Logger serviceLogger = LoggerFactory.getLogger("service");

	private Logger getLogger(LogType type) {
		switch (type) {
			case CONTROLLER:
				return controllerLogger;
			case SERVICE:
				return serviceLogger;
			case DAO:
				return daoLogger;
			default:
				return defaultLogger;
		}
	}

	@Override
	public void log(LogType type, LogLevel level, String message) {
		// FIXME for default case, integrate logged-in user information

		switch (level) {
			case TRACE:
			case DEBUG:
				getLogger(type).debug(message);
				break;
			case INFO:
				getLogger(type).info(message);
				break;
			case WARNING:
				getLogger(type).warn(message);
				break;
			case ERROR:
			case FATAL:
				getLogger(type).error(message);
				break;
			default:
				getLogger(type).info(message);
				break;
		}
	}
}
