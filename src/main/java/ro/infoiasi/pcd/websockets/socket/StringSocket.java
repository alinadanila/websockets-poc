package ro.infoiasi.pcd.websockets.socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.websocket.WebSocket;

import ro.infoiasi.pcd.websockets.server.StringSocketServlet;

public class StringSocket implements WebSocket.OnTextMessage {

	private static final Log LOGGER = LogFactory.getLog(StringSocket.class);

	private Connection connection;
	private StringSocketServlet servlet;

	public StringSocket(StringSocketServlet servlet) {
		this.servlet = servlet;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void onClose(int code, String message) {
		try {
			this.servlet.closeClient(this);
			LOGGER.info("closing string connection");
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}

	@Override
	public void onOpen(Connection connection) {
		this.connection = connection;
		LOGGER.info("new string connection opened");
	}

	@Override
	public void onMessage(String message) {
		LOGGER.info("MESSAGE " + message);
	}
}
