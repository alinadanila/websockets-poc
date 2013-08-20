package ro.infoiasi.pcd.websockets.daemon;

import java.util.ArrayList;
import java.util.List;

import org.hyperic.sigar.SigarException;

import ro.infoiasi.pcd.websockets.info.Info;
import ro.infoiasi.pcd.websockets.info.InfoProvider;

public class StatisticsDaemon extends Thread {

	private static StatisticsDaemon instance;
	private static long interval = 1000;

	private List<Info> stats;

	static {
		instance = new StatisticsDaemon();
	}

	public StatisticsDaemon() {
		this.stats = new ArrayList<Info>();
	}

	public static StatisticsDaemon getInstance() {
		return instance;
	}

	public void run() {
		try {
			while (true) {
				this.populateStatistics();
				Thread.sleep(interval);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void populateStatistics() throws SigarException {
		// at most 100 records
		if (this.stats.size() > 100) {
			this.stats.remove(0);
		}

		Info info = InfoProvider.getInfo();
		this.stats.add(info);
	}

	public Info[] getStatistics() {
		Info[] infos = new Info[stats.size()];
		stats.toArray(infos);

		return infos;
	}
}
