package org.dedeler.template.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {

	public enum LogLevel {
		TRACE("default"),
		DEBUG("default"),
		INFO("default"),
		WARNING("default"),
		ERROR("default"),
		FATAL("default"),
		CONTROLLER("controller"),
		SERVICE("service"),
		DAO("dao");

		private final String name;

		private LogLevel(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	private static final Logger defaultLogger = LoggerFactory.getLogger("default");
	private static final Logger controllerLogger = LoggerFactory.getLogger("controller");
	private static final Logger daoLogger = LoggerFactory.getLogger("dao");
	private static final Logger serviceLogger = LoggerFactory.getLogger("service");

	private Logger getLogger(LogLevel level) {
		switch (level) {
			case TRACE:
			case DEBUG:
			case INFO:
			case ERROR:
			case FATAL:

				return defaultLogger;
			case CONTROLLER:
				return controllerLogger;
			case SERVICE:
				return serviceLogger;
			case DAO:
				return daoLogger;
		}
		return null;
	}

	public void log(LogLevel level, String message) {
		// FIXME for default case, integrate logged-in user information

		switch (level) {
			case TRACE:
			case DEBUG:
				getLogger(level).debug(message);
				break;

			case INFO:
				getLogger(level).info(message);
				break;
			case WARNING:
				getLogger(level).warn(message);
				break;
			case ERROR:
			case FATAL:
				getLogger(level).error(message);
				break;
			default:
				getLogger(level).info(message);
				break;
		}
	}
}
