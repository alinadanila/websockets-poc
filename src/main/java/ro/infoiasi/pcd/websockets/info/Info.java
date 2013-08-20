package ro.infoiasi.pcd.websockets.info;

import java.io.IOException;
import java.io.StringWriter;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class Info {
	private static ObjectMapper objectMapper;

	private long usedCpu;
	private long idleCpu;

	private long freeMem;
	private long usedMem;
	private long totalMem;

	static {
		objectMapper = new ObjectMapper();
	}

	public double getUsedCpu() {
		return usedCpu;
	}

	public void setUsedCpu(long usedCpu) {
		this.usedCpu = usedCpu;
	}

	public long getIdleCpu() {
		return idleCpu;
	}

	public void setIdleCpu(long idleCpu) {
		this.idleCpu = idleCpu;
	}

	public double getFreeMem() {
		return freeMem;
	}

	public void setFreeMem(long freeMem) {
		this.freeMem = freeMem;
	}

	public long getUsedMem() {
		return usedMem;
	}

	public void setUsedMem(long usedMem) {
		this.usedMem = usedMem;
	}

	public long getTotalMem() {
		return totalMem;
	}

	public void setTotalMem(long totalMem) {
		this.totalMem = totalMem;
	}

	@Override
	public String toString() {
		StringWriter stringWriter = new StringWriter();
		try {

			objectMapper.writeValue(stringWriter, this);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return stringWriter.toString();
	}
}
