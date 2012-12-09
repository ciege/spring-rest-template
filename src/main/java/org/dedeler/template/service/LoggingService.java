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
		FATAL("default");

		private final String name;

		private LogLevel(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public enum LogType {
		DEFAULT("default"),
		CONTROLLER("controller"),
		SERVICE("service"),
		DAO("dao");

		private final String name;

		private LogType(String name) {
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

	public static Logger getLogger(LogType type) {
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
