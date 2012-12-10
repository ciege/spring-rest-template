package org.dedeler.template.soap;

import org.dedeler.template.service.LoggingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Lazily initializes soap clients. This also enables us to use clients
 * with different service hosts at runtime <br>
 * <br>
 * <strong>Code sample:</strong>
 * 
 * <pre>
 * private MobileCommonServiceEndpoint commonWS;
 * private final String endpoint = &quot;http://sample.endpoints.service.external.com/&quot;
 * 
 * public MobileCommonServiceEndpoint getCommonWsPort() {
 * 	if (commonWS == null) {
 * 		try {
 * 			loggingService.debug(&quot;Acquiring MobileCommonService...&quot;);
 * 			MobileCommonService mobileCommonService = new MobileCommonService(new URL(commonServiceWsdl), new QName(endpoint, &quot;MobileCommonService&quot;));
 * 			mobileCommonService.setHandlerResolver(new JaxWsHandlerResolver(&quot;MobileCommonServiceEndpoint&quot;));
 * 			commonWS = mobileCommonService.getMobileCommonServiceEndpointPort();
 * 			((BindingProvider) commonWS).getRequestContext().put(&quot;com.sun.xml.ws.request.timeout&quot;, webserviceTimeout * 1000);
 * 			loggingService.debug(&quot;MobileCommonService is successfully acquired&quot;);
 * 		}
 * 		catch (Exception e) {
 * 			commonWS = null;
 * 			ApiException apiException = new ApiException(&quot;External web services are not available&quot;, e);
 * 			loggingService.error(&quot;External web services are not available&quot;, apiException);
 * 			throw apiException;
 * 		}
 * 	}
 * 	return commonWS;
 * }
 * </pre>
 * 
 * @author destan
 * 
 */
@Component
public class SoapClientProvider {

	@Autowired
	private LoggingService loggingService;

	@Value("${WebserviceTimeout}")
	private Integer webserviceTimeout;

	@Value("${webService.commonServiceWsdl}")
	private String commonServiceWsdl;

}
