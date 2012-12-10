package org.dedeler.template.soap;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

/**
 * 
 * @author destan
 * 
 */
public class JaxWsHandlerResolver implements HandlerResolver {

	private String serviceName;

	public JaxWsHandlerResolver(String serviceName) {
		this.serviceName = serviceName;
	}

	public JaxWsHandlerResolver() {}

	@Override
	@SuppressWarnings("rawtypes")
	public List<Handler> getHandlerChain(PortInfo info) {
		List<Handler> hchain = new ArrayList<Handler>();
		hchain.add(new JaxWsLoggingHandler(serviceName));
		return hchain;
	}

}
