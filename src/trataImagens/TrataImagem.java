package trataImagens;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TrataImagem {

	int bloco = 45;
	int bloco2 = 33;

	int z = 0;

	int max_x = 0;
	int max_y = 0;
	
	String log = "";

	BufferedImage image = null;
	
	BufferedImage timage = null;
	
	BufferedImage p1 = null;
	BufferedImage p2 = null;
	BufferedImage p3 = null;
	BufferedImage p4 = null;

	FileOutputStream saida = null;
	
	public void apura(String trecho) {
		
		File file = new File("/home/eric/cadernos/" + trecho);
		
		try {
			
			timage = ImageIO.read(file);
			
			max_x = timage.getWidth();
			max_y = timage.getHeight();
			
			Ponto ponto1 = busca_n2(0,0,max_x, max_y);
			
			System.out.println(ponto1);			

			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	public void processa(String arquivo) throws IOException {

		File file = new File(arquivo);
		
		File outputfile1 = new File("/home/eric/cadernos/p1.jpg");
		File outputfile2 = new File("/home/eric/cadernos/p2.jpg");
		File outputfile3 = new File("/home/eric/cadernos/p3.jpg");
		File outputfile4 = new File("/home/eric/cadernos/p4.jpg");
		
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

			System.out.println("tempo leitura imagem:" + seconds);

			max_x = image.getWidth();
			max_y = image.getHeight();

			log = String.format("max = %1$d, %2$d", max_x, max_y);
			System.out.println(log);

			// procura bloco1
			
			Ponto ponto1 = busca_n0(0, 0, max_x/2, max_y/2);
			
			System.out.println(ponto1);			

			// procura bloco 2

			Ponto ponto2 = busca_n0(max_x/2, 0, max_x - (max_x/2) , max_y/2);

			System.out.println(ponto2);
			
			// procura bloco 3
			
			Ponto ponto3 = busca_n0(0,max_y/2,max_x - (max_x/2), 1700);

			System.out.println(ponto3);
			
			int largura = (ponto2.getX()-ponto1.getX())/4;
			
			p1 = image.getSubimage(ponto1.getX()+bloco, ponto1.getY(), largura, 1700);

			p2 = image.getSubimage(ponto1.getX()+largura+1, ponto1.getY(), largura, 1700);

			p3 = image.getSubimage(ponto1.getX()+(largura*2)+1, ponto1.getY(), largura, 1700);

			p4 = image.getSubimage(ponto1.getX()+(largura*3)+1, ponto1.getY(), largura, 1700);
			
			ImageIO.write(p1, "jpg", outputfile1);
			ImageIO.write(p2, "jpg", outputfile2);
			ImageIO.write(p3, "jpg", outputfile3);
			ImageIO.write(p4, "jpg", outputfile4);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			saida.close();
			System.out.println("fim");

		}
	}

	private Ponto busca_n0(int xx, int yy, int ww, int hh) {
		//	System.out.println(String.format("%1$d %2$d %3$d %4$d", xx,yy,ww,hh));
		Long initTime = System.nanoTime();
		Ponto retorno = new Ponto();
		z=0;
		int n0x = 0;
		int n0y = 0;
		int p = 0;
		try {
			for (n0x = xx; n0x <= (xx+ww); n0x++) {
				for (n0y = yy; n0y <= (yy+hh); n0y++) {
					p = image.getRGB(n0x, n0y);
					if (p != -1) {
						if (busca_n1(n0x, n0y)) {
							retorno.setX(n0x);
							retorno.setY(n0y);
							z = 1;
							//log = String.format("ponto  = %1$d, %2$d ", n0x, n0y);
							for (int w = 0; w <= log.length() - 1; w++) {
								try {
									saida.write(log.charAt(w));
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}						
							//System.out.println(log);
							break;
						}
					}
				}
				if (z == 1)
					break;
			}
		} catch (Exception e) {
			log = String.format("erro = %1$d, %2$d", n0x, n0y);
			System.out.println(log + ' ' + p);
			throw e;
		}
		long endTime1 = System.nanoTime();

		double seconds = ((double) (endTime1 - initTime)) / 1000000000.0;

		System.out.println("tempo:" + seconds);
		
		return retorno;
	}

	private Ponto busca_n2(int xx, int yy, int ww, int hh) {
		//	System.out.println(String.format("%1$d %2$d %3$d %4$d", xx,yy,ww,hh));
		Long initTime = System.nanoTime();
		Ponto retorno = new Ponto();
		z=0;
		int n0x = 0;
		int n0y = 0;
		int p = 0;
		try {
			for (n0x = xx; n0x <= (xx+ww); n0x++) {
				for (n0y = yy; n0y <= (yy+hh); n0y++) {
					p = timage.getRGB(n0x, n0y);
					if (p != -1) {
						if (busca_n3(n0x, n0y)) {
							retorno.setX(n0x);
							retorno.setY(n0y);
							z = 1;
							//log = String.format("ponto  = %1$d, %2$d ", n0x, n0y);
							for (int w = 0; w <= log.length() - 1; w++) {
								try {
									saida.write(log.charAt(w));
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}						
							//System.out.println(log);
							break;
						}
					}
				}
				if (z == 1)
					break;
			}
		} catch (Exception e) {
			log = String.format("erro = %1$d, %2$d", n0x, n0y);
			System.out.println(log + ' ' + p);
			throw e;
		}
		long endTime1 = System.nanoTime();

		double seconds = ((double) (endTime1 - initTime)) / 1000000000.0;

		System.out.println("tempo:" + seconds);
		
		return retorno;
	}
	
	
	private boolean busca_n1(int x, int y) {
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
	
	private boolean busca_n3(int x, int y) {
		int marca = 1;
		for (int _x = 0; _x <= bloco2; _x++) {
			if (timage.getRGB(_x + x, y) == -1) {
				marca = 0;
			}
		}
		return marca == 1;
	}
	
	
	

}
