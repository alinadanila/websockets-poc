package ro.infoiasi.pcd.websockets.socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.websocket.WebSocket;

import ro.infoiasi.pcd.websockets.server.BinarySocketServlet;

public class BinarySocket implements WebSocket.OnBinaryMessage {

	private static final Log LOGGER = LogFactory.getLog(BinarySocket.class);

	private Connection connection;
	private BinarySocketServlet servlet;

	public BinarySocket(BinarySocketServlet servlet) {
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
			LOGGER.info("closing binary connection");
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}

	@Override
	public void onOpen(Connection connection) {
		this.connection = connection;
		LOGGER.info("new binary connection opened");
	}

	@Override
	public void onMessage(byte[] arg0, int arg1, int arg2) {
		// do nothing
	}
}
