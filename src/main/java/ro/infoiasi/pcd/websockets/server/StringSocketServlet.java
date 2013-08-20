package ro.infoiasi.pcd.websockets.server;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import ro.infoiasi.pcd.websockets.daemon.StatisticsDaemon;
import ro.infoiasi.pcd.websockets.daemon.UpdatesDeamon;
import ro.infoiasi.pcd.websockets.info.Info;
import ro.infoiasi.pcd.websockets.socket.StringSocket;


// -Djava.library.path="D:\WORKSPACE\workspace\pcd\pcd_2\lib"
public class StringSocketServlet extends WebSocketServlet {

	private static final long serialVersionUID = 1L;

	private static final Log LOGGER = LogFactory
			.getLog(StringSocketServlet.class);
	private Set<StringSocket> sockets;

	@Override
	public void init() throws ServletException {
		super.init();
		this.sockets = new HashSet<StringSocket>();

		StatisticsDaemon statisticsDaemon = StatisticsDaemon.getInstance();
		statisticsDaemon.start();

		UpdatesDeamon updatesDeamon = UpdatesDeamon.getInstance();
		updatesDeamon.setStringServlet(this);
		updatesDeamon.start();
	}

	@Override
	public WebSocket doWebSocketConnect(HttpServletRequest request,
			String protocol) {
		LOGGER.info("new string connection arrived...");

		StringSocket socket = new StringSocket(this);
		this.sockets.add(socket);

		return socket;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		LOGGER.info("HTTP protocol not accepted.");
	}

	public void closeClient(StringSocket socket) {
		this.sockets.remove(socket);
	}

	public void updateClients(Info info) throws IOException {
		for (StringSocket socket : this.sockets) {
			socket.getConnection().sendMessage(info.toString());
		}
		LOGGER.info("STRING INFO UPDATE");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void destroy() {
		StatisticsDaemon.getInstance().stop();
		UpdatesDeamon.getInstance().stop();
		super.destroy();
	}
}
