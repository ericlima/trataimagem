package trataImagens;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TrataImagem {

	int blocao = 35;
	int bloquinho = 33;

	int z = 0;

	int max_x = 0;
	int max_y = 0;
	
	String log = "";

	BufferedImage image = null;
	
	BufferedImage coluna = null;
	
	BufferedImage p1 = null;
	BufferedImage p2 = null;
	BufferedImage p3 = null;
	BufferedImage p4 = null;

	FileOutputStream saida = null;
	
	public void apura(String trecho) {
		
		File file = new File("/home/eric/cadernos/" + trecho);
		
		try {
			
			coluna = ImageIO.read(file);
			
			max_x = coluna.getWidth();
			max_y = coluna.getHeight();
			
			Ponto ponto1 = busca_n2(0,0,max_x-1, max_y-1);
			
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
		
		outputfile1.delete();
		outputfile2.delete();
		outputfile3.delete();
		outputfile4.delete();
		
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
			
			Ponto ponto1 = busca_blocao(0, 0, max_x/2, max_y/2);
			
			if ((ponto1.getX()==0) && (ponto1.getY()==0)) {
				throw new Exception("ponto1 não encontrado");
			}
			
			System.out.println(ponto1);			

			// procura bloco 2

			Ponto ponto2 = busca_blocao(max_x/2, 0, max_x - (max_x/2) , max_y/2);

			System.out.println(ponto2);
			
			// procura bloco 3
			
			Ponto ponto3 = busca_blocao(0,max_y/2,max_x - (max_x/2), 1700);

			System.out.println(ponto3);
			
			//----------------------------------------------------------
			
			int largura = (ponto2.getX()-ponto1.getX())/4;
			// int altura = (ponto3.getY()-ponto1.getY());
			
			int altura = 1700;
			
			// verifica se necessita rotacao
			if (false && (ponto1.getY() != ponto2.getY())) {

				double cat1 = (double) (ponto2.getX() - ponto1.getX());
				
				double cat2 = 0d;
				
				boolean clockwise = true;
				
				if (ponto2.getY() > ponto1.getY()) { 
					cat2 = (double) (ponto2.getY() - ponto1.getY());
					clockwise = false;
				} else {
					cat2 = (double) (ponto1.getY() - ponto2.getY());
					clockwise = true;
				}
				
				double hypt = Math.tan((cat1 / Math.sqrt((cat1*cat1)+(cat2*cat2))));
				
				if (clockwise) {
					rotaciona(image,hypt);
				} else {
					rotaciona(image,hypt*-1);
				}
				
			}			
			
			coluna = image.getSubimage(ponto1.getX(), ponto1.getY(), largura, altura);
			
			tracos(coluna,96,114);		        
			
//			Ponto c1 = busca_n2(0,0,coluna.getWidth()-1,coluna.getHeight()-1);
//			
//			int sublargura = coluna.getWidth() - c1.getX();
//			int subaltura = coluna.getHeight() - c1.getY();
//			
//			p1 = coluna.getSubimage(c1.getX()-2, c1.getY()-2,sublargura,subaltura);

			ImageIO.write(coluna, "jpg", outputfile1);

			//----------------------------------------------------------
			
			coluna = image.getSubimage(ponto1.getX()+largura+1, ponto1.getY(), largura, altura);
			
			tracos(coluna,92,114);	
			
//			Ponto c2 = busca_n2(0,0,coluna.getWidth()-1,coluna.getHeight()-1);
//			
//			sublargura = coluna.getWidth() - c2.getX();
//			subaltura = coluna.getHeight() - c2.getY();
//			
//			p2 = coluna.getSubimage(c2.getX()-2, c2.getY()-2,sublargura,subaltura);
			
			ImageIO.write(coluna, "jpg", outputfile2);
			
			//----------------------------------------------------------
			
			coluna = image.getSubimage(ponto1.getX()+(largura*2)+1, ponto1.getY(), largura, altura);
			
			tracos(coluna,85,114);	
			
//			Ponto c3 = busca_n2(0,0,coluna.getWidth()-1,coluna.getHeight()-1);
//			
//			sublargura = coluna.getWidth() - c3.getX();
//			subaltura = coluna.getHeight() - c3.getY();
//			
//			p3 = coluna.getSubimage(c3.getX()-2, c3.getY()-2,sublargura,subaltura);
			
			ImageIO.write(coluna, "jpg", outputfile3);
			
			//----------------------------------------------------------
			
			coluna = image.getSubimage(ponto1.getX()+(largura*3)+1, ponto1.getY(), largura, altura);
			
			tracos(coluna,85,114);	
			
//			Ponto c4 = busca_n2(0,0,coluna.getWidth()-1,coluna.getHeight()-1);
//			
//			sublargura = coluna.getWidth() - c4.getX();
//			subaltura = coluna.getHeight() - c4.getY();
//			
//			p4 = coluna.getSubimage(c4.getX()-2, c4.getY()-2,sublargura,subaltura);
			
			ImageIO.write(coluna, "jpg", outputfile4);
			
			//----------------------------------------------------------

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			saida.close();
			System.out.println("fim");

		}
	}
	
	private void rotaciona(BufferedImage four2, double graus) {
		AffineTransform at = new AffineTransform();
		at.translate(four2.getWidth() / 2, four2.getHeight() / 2);
		at.rotate((Math.PI / 360) * graus);
		// at.scale(0.5, 0.5);
		at.translate(-four2.getWidth()/2, -four2.getHeight()/2);
		Graphics2D g2d = (Graphics2D) four2.getGraphics();
		g2d.drawImage(four2, at, null);
		g2d.dispose();
	}
	
	private void tracos(BufferedImage img, int xini, int yini) {
		
		try {
		Dimension imgDim = new Dimension(img.getWidth(),img.getHeight());
		Graphics2D g2d = img.createGraphics();
		
        g2d.setBackground(Color.WHITE);
        g2d.setColor(Color.BLACK);
        BasicStroke bs = new BasicStroke(1);
        g2d.setStroke(bs);
        
        for(int i=xini; i<(imgDim.width);i=i+38) {
        	g2d.drawLine(i,0, i, imgDim.height);
        }
        
        for(int i=yini; i<(imgDim.height);i=i+62) {
        	g2d.drawLine(0, i, imgDim.width, i);
        }
		} catch(Exception e) {
		}
        
	}

	private Ponto busca_blocao(int xx, int yy, int ww, int hh) {
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
							//System.out.println(log);
							for (int w = 0; w <= log.length() - 1; w++) {
								try {
									saida.write(log.charAt(w));
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}						
							
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
		for (n0y = yy; n0y <= (yy+hh); n0y++) {
			for (n0x = xx; n0x <= (xx+ww); n0x++) {
					p = coluna.getRGB(n0x, n0y);
					if (p != -1) {
						if (busca_n3(n0x, n0y)) {
							retorno.setX(n0x);
							retorno.setY(n0y);
							z = 1;
							for (int w = 0; w <= log.length() - 1; w++) {
								try {
									saida.write(log.charAt(w));
								} catch (IOException e) {
									e.printStackTrace();
								}
							}						
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
		for (int _x = 0; _x <= blocao; _x++) {
			for (int _y = 0; _y <= blocao; _y++) {
				if (image.getRGB(_x + x, _y + y) == -1) {
					marca = 0;
					break;
				}
			}
			if (marca==0)
				break;
		}
		return marca == 1;
	}
	
	private boolean busca_n3(int x, int y) {
		int marca = 1;
		for (int _x = 0; _x <= bloquinho; _x++) {
			if (coluna.getRGB(_x + x, y) == -1) {
				marca = 0;
			}
		}
		return marca == 1;
	}
	
	
	

}
