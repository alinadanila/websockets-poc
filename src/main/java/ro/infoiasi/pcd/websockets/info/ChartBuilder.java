package ro.infoiasi.pcd.websockets.info;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.DefaultXYDataset;

public class ChartBuilder {
	public static byte[] buildChart(Info[] infos) throws IOException {
		DefaultXYDataset dataSet = new DefaultXYDataset();

		double[][] cpu = new double[2][infos.length];
		double[][] mem = new double[2][infos.length];

		int i = 0;
		for (Info info : infos) {
			cpu[0][i] = i;
			cpu[1][i] = i;
			// cpu[1][i] = info.getSysCpu() + info.getUserCpu();
			i++;
		}

		dataSet.addSeries("cpu", cpu);

		JFreeChart chart = ChartFactory.createXYLineChart(
				"Hardware Usage Chart", "x", "y", dataSet,
				PlotOrientation.VERTICAL, true, true, false);

		BufferedImage image = chart.createBufferedImage(400, 400);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", baos);
		baos.flush();
		byte[] imageInBytes = baos.toByteArray();
		baos.close();

		// write the file on disk
		FileOutputStream fos = new FileOutputStream("D:\\image.jpg");
		fos.write(imageInBytes, 0, imageInBytes.length);
		fos.flush();
		fos.close();

		return imageInBytes;
	}
}
