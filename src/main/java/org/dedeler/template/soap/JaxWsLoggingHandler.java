package org.dedeler.template.soap;

import java.util.Date;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Intercepts inbound and outbound soap calls
 * 
 * @author destan
 * 
 */
public class JaxWsLoggingHandler extends SpringBeanAutowiringSupport implements SOAPHandler<SOAPMessageContext> {

	private static Logger serviceLogger = LoggerFactory.getLogger("service");

	private final String serviceName;

	public JaxWsLoggingHandler(String serviceName) {
		this.serviceName = serviceName;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		try {
			Boolean isOutbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
			SOAPMessage message = context.getMessage();
			if (isOutbound) {
				Date startTime = new Date();
				context.put("beginTime", startTime);
				context.put("transactionId", getTransactionId(message));
				context.put("fullName", serviceName + "#" + getOperationName(message));
			}
			else {

				Date endTime = new Date();
				Date startTime = (Date) context.get("beginTime");
				long duration = endTime.getTime() - startTime.getTime();
				String log = "\nExecuted Ws: " + context.get("fullName") + " | TransactionId: " + context.get("transactionId") + " | " + duration
						+ "ms\n";
				serviceLogger.info(log);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return true;
	}

	@Override
	public void close(MessageContext context) {}

	@Override
	public Set<QName> getHeaders() {
		return null;
	}

	private String getOperationName(SOAPMessage message) {
		try {
			return message.getSOAPBody().getFirstChild().getLocalName();
		}
		catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	private String getTransactionId(SOAPMessage message) {
		try {
			NodeList nodeList = message.getSOAPBody().getFirstChild().getFirstChild().getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeName().equals("transactionId")) {
					return node.getTextContent();
				}
			}
			return "-1";
		}
		catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

}
