package ro.infoiasi.pcd.websockets.daemon;

import java.io.IOException;

import org.hyperic.sigar.SigarException;

import ro.infoiasi.pcd.websockets.info.ChartBuilder;
import ro.infoiasi.pcd.websockets.info.Info;
import ro.infoiasi.pcd.websockets.info.InfoProvider;
import ro.infoiasi.pcd.websockets.server.BinarySocketServlet;
import ro.infoiasi.pcd.websockets.server.StringSocketServlet;

public class UpdatesDeamon extends Thread {

	private static UpdatesDeamon instance;
	private static long interval = 10000;

	private StringSocketServlet stringServlet;
	private BinarySocketServlet binaryServlet;

	static {
		instance = new UpdatesDeamon();
	}

	public void setStringServlet(StringSocketServlet stringServlet) {
		this.stringServlet = stringServlet;
	}

	public void setBinaryServlet(BinarySocketServlet binaryServlet) {
		this.binaryServlet = binaryServlet;
	}

	public static UpdatesDeamon getInstance() {
		return instance;
	}

	@Override
	public synchronized void start() {
		super.start();
	}

	public void run() {
		try {

			while (true) {
				Thread.sleep(interval);

				// update string info
				Info info = InfoProvider.getInfo();
				this.stringServlet.updateClients(info);

				// update image info
				Info[] infos = StatisticsDaemon.getInstance().getStatistics();
				byte[] chart = ChartBuilder.buildChart(infos);
				binaryServlet.updateClients(chart);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
