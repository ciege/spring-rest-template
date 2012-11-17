package org.dedeler.template.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class IntegrationLogger {

	private final String serviceName;
	private final StringBuilder log;
	private final Calendar startTime = Calendar.getInstance();

	private final Map<String, ExternalService> externalServiceMap;

	public IntegrationLogger(String serviceName) {
		this.log = new StringBuilder();
		this.externalServiceMap = new HashMap<String, ExternalService>();
		this.serviceName = serviceName;
	}

	public void startOfExteralService(String externalServiceName, Object... params) {
		ExternalService externalService = new ExternalService(externalServiceName);
		externalServiceMap.put(externalServiceName, externalService);
	}

	public void endOfExternalService(String externalServiceName, Object returnValue) {
		if (externalServiceMap.containsKey(externalServiceName)) {
			externalServiceMap.get(externalServiceName).ends();
		}
		else {
			// TODO log this inconsistency
		}
	}

	public String finalizeLog(Object returnValue) {
		Long duration = Calendar.getInstance().getTimeInMillis() - startTime.getTimeInMillis();
		StringBuilder finalLog = new StringBuilder();

		finalLog.append("\n" + serviceName + "\nParams: ");
		finalLog.append("\nReturn Value: ");
		finalLog.append("\nExecution duration: " + duration + "ms");

		for (ExternalService externalService : externalServiceMap.values()) {
			finalLog.append(externalService.getLog());
		}

		finalLog.append("\nEnd of " + this.serviceName + " logging.\n");

		return log.toString();
	}

	private class ExternalService {
		private final String externalServiceName;
		private final Calendar startTime;
		private String duration;

		public ExternalService(String externalServiceName) {
			this.startTime = Calendar.getInstance();
			this.externalServiceName = externalServiceName;
		}

		private void ends() {
			duration = new Long(Calendar.getInstance().getTimeInMillis() - this.startTime.getTimeInMillis()).toString();
		}

		private String getLog() {
			StringBuilder innerLog = new StringBuilder();
			innerLog.append("\n\t" + this.externalServiceName + ":");
			innerLog.append("\n\tParameters: ");
			innerLog.append("\n\tReturn value: ");
			innerLog.append("\n\tDuration: " + duration + "ms");
			innerLog.append("\n");
			return innerLog.toString();
		}

	}
}
