package ro.infoiasi.pcd.websockets.info;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class InfoProvider {

	public static void main(String[] args) throws SigarException {
		getInfo();
	}

	public static Info getInfo() throws SigarException {
		Info info = new Info();
		Sigar sigar = new Sigar();

		/* cpu stuff */
		CpuPerc perc = sigar.getCpuPerc();

		
		info.setUsedCpu(Math.round(perc.getCombined() * 100.0));
		info.setIdleCpu(Math.round(perc.getIdle() * 100));

		/* memory stuff */
		Mem mem = sigar.getMem();
		info.setFreeMem(mem.getActualFree() / 1048576);
		info.setUsedMem(mem.getActualUsed() / 1048576);
		info.setTotalMem(mem.getTotal() / 1048576);

		return info;
	}
}
