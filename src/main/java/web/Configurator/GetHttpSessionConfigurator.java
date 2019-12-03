package web.Configurator;


import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;




public class GetHttpSessionConfigurator extends ServerEndpointConfig.Configurator {
	
	@Override
    public void modifyHandshake(ServerEndpointConfig sec,
                                HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession=(HttpSession) request.getHttpSession();
        if(httpSession == null){
        	System.out.println("httpSession null");

        }
        sec.getUserProperties().put(HttpSession.class.getName(),httpSession);
    }

}
