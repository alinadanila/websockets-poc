package ro.infoiasi.pcd.websockets.server;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import ro.infoiasi.pcd.websockets.daemon.UpdatesDeamon;
import ro.infoiasi.pcd.websockets.socket.BinarySocket;

public class BinarySocketServlet extends WebSocketServlet {

	private static final long serialVersionUID = 1L;

	private static final Log LOGGER = LogFactory
			.getLog(BinarySocketServlet.class);

	private Set<BinarySocket> sockets;

	@Override
	public void init() throws ServletException {
		super.init();
		this.sockets = new HashSet<BinarySocket>();

		UpdatesDeamon updatesDeamon = UpdatesDeamon.getInstance();
		updatesDeamon.setBinaryServlet(this);
	}

	@Override
	public WebSocket doWebSocketConnect(HttpServletRequest request,
			String protocol) {
		LOGGER.info("new binary connection arrived...");

		BinarySocket socket = new BinarySocket(this);
		this.sockets.add(socket);

		return socket;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		LOGGER.info("HTTP protocol not accepted.");
	}

	public void closeClient(BinarySocket socket) {
		this.sockets.remove(socket);
	}

	public void updateClients(byte[] chart) throws IOException {
		for (BinarySocket socket : this.sockets) {
			//byte[] encoded = Base64.encodeBase64(chart);
			byte[] encoded = chart;

			socket.getConnection().sendMessage(encoded, 0, encoded.length);
		}
		LOGGER.info("BINARY CHART UPDATE");
	}
}
