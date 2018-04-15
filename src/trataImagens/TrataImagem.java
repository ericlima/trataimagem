package trataImagens;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TrataImagem {

	int x1 = 0;
	int y1 = 0;

	int x2 = 0;
	int y2 = 0;

	int x3 = 0;
	int y3 = 0;

	int x4 = 0;
	int y4 = 0;

	int bloco = 45;

	int x = 0;
	int y = 0;
	int p = 0;
	int z = 0;

	String log = "";

	BufferedImage image = null;

	public void processa() throws IOException {

		File file = new File("/home/eric/cadernos/page1.tif");

		FileOutputStream saida = null;

		try {
			saida = new FileOutputStream("/home/eric/cadernos/saida.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {

			// leitura da pagina
			Long initTime = System.nanoTime();

			image = ImageIO.read(file);

			Long endTime1 = System.nanoTime();

			double seconds = ((double) (endTime1 - initTime)) / 1000000000.0;

			System.out.println("tempo1:" + seconds);

			initTime = System.nanoTime();

			int max_x = image.getWidth();
			int max_y = image.getHeight();

			log = String.format("max = %1$d, %2$d", max_x, max_y);
			System.out.println(log);

			// procura bloco1

			for (x = 0; x <= (max_x - bloco - 1); x++) {
				for (y = 0; y <= (max_y - bloco - 1); y++) {
					p = image.getRGB(x, y);
					// log = String.format("ponto = %1$d, %2$d", x, y);
					// System.out.println(log + ' ' + p);
					if (p != -1) {
						if (procura(x, y, bloco, image)) {
							x1 = x;
							y1 = y;
							z = 1;
							log = String.format("ponto 1 = %1$d, %2$d ", x, y);
							for (int w = 0; w <= log.length() - 1; w++)
								saida.write(log.charAt(w));
							saida.write(13);
							System.out.println(log);
							break;
						}
					}
				}
				if (z == 1)
					break;
			}

			endTime1 = System.nanoTime();

			seconds = ((double) (endTime1 - initTime)) / 1000000000.0;

			System.out.println("tempo2:" + seconds);

			// procura bloco 2

			z = 0;

			for (x = max_x / 2; x <= (max_x - bloco - 1); x++) {
				for (y = y1 + bloco + 1; y <= (max_y - bloco - 1); y++) {
					p = image.getRGB(x, y);
					// log = String.format("ponto = %1$d, %2$d", x, y);
					// System.out.println(log + ' ' + p);
					if (p != -1) {
						if (procura(x, y, bloco, image)) {
							x2 = x;
							y2 = y;
							z = 1;
							log = String.format("ponto 2 = %1$d, %2$d", x, y);
							for (int w = 0; w <= log.length() - 1; w++)
								saida.write(log.charAt(w));
							System.out.println(log);
							break;
						}
					}
				}
				if (z == 1)
					break;
			}

			endTime1 = System.nanoTime();

			seconds = ((double) (endTime1 - initTime)) / 1000000000.0;

			System.out.println("tempo3:" + seconds);

			// procura bloco 3

			z = 0;

			for (x = x1 + bloco + 1; x <= (max_x / 2); x++) {
				for (y = y1 + bloco + 1; y <= (max_y - bloco - 1); y++) {
					p = image.getRGB(x, y);
					// log = String.format("ponto = %1$d, %2$d", x, y);
					// System.out.println(log + ' ' + p);
					if (p != -1) {
						if (procura(x, y, bloco, image)) {
							x3 = x;
							y3 = y;
							z = 1;
							log = String.format("ponto 3 = %1$d, %2$d", x, y);
							for (int w = 0; w <= log.length() - 1; w++)
								saida.write(log.charAt(w));
							System.out.println(log);
							break;
						}
					}
				}
				if (z == 1)
					break;
			}

			endTime1 = System.nanoTime();

			seconds = ((double) (endTime1 - initTime)) / 1000000000.0;

			System.out.println("tempo4:" + seconds);

			// procura bloco 4

			z = 0;

			for (x = x3 + bloco + 1; x <= (max_x-bloco-1); x++) {
				for (y = y3 ; y <= (max_y - bloco - 1); y++) {
					p = image.getRGB(x, y);
					// log = String.format("ponto = %1$d, %2$d", x, y);
					// System.out.println(log + ' ' + p);
					if (p != -1) {
						if (procura(x, y, bloco, image)) {
							x4 = x;
							y4 = y;
							z = 1;
							log = String.format("ponto 4 = %1$d, %2$d", x, y);
							for (int w = 0; w <= log.length() - 1; w++)
								saida.write(log.charAt(w));
							System.out.println(log);
							break;
						}
					}
				}
				if (z == 1)
					break;
			}

			endTime1 = System.nanoTime();

			seconds = ((double) (endTime1 - initTime)) / 1000000000.0;

			System.out.println("tempo5:" + seconds);
			
		} catch (Exception e) {
			log = String.format("ponto = %1$d, %2$d", x, y);
			System.out.println(log + ' ' + p);
			e.printStackTrace();
		} finally {
			saida.close();
			System.out.println("fim");

		}
	}

	private boolean procura(int x, int y, int bloco, BufferedImage image) {
		int marca = 1;
		for (int _x = 0; _x <= bloco; _x++) {
			for (int _y = 0; _y <= bloco; _y++) {
				if (image.getRGB(_x + x, _y + y) == -1) {
					marca = 0;
				}
			}
		}
		return marca == 1;
	}

}
