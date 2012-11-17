package org.dedeler.template.service;

public interface LoggingService {

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

	void log(LogType type, LogLevel level, String message);
}
