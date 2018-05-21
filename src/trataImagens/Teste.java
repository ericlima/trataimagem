package trataImagens;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import com.lowagie.text.pdf.PRIndirectReference;
import com.lowagie.text.pdf.PRStream;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfObject;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStream;

public class Teste {

	public static void main(String... args) {

		org.apache.log4j.BasicConfigurator.configure();

		TrataImagem imagem = new TrataImagem();
		
		try {
			//imagem.processa("/home/eric/cadernos/pdfs/672676(out).png");
			
			String someImage = "/home/eric/cadernos/pdfs/cbarra.jpg";
			String someImageIn = "/home/eric/cadernos/pdfs/674550.pdf";
			String someImageOut = "/home/eric/cadernos/pdfs/674550(out).jpg";

			String pdfFile = "/home/eric/cadernos/pdfs/673886.pdf";

			// //config option 1:convert all document to image
			// String [] args_1 = new String[3];
			// args_1[0] = "-outputPrefix";
			// args_1[1] = "/home/eric/cadernos/pdfs/my_image_1.jpg";
			// args_1[2] = pdfPath;
			//
			// //config option 2:convert page 1 in pdf to image
			// String [] args_2 = new String[7];
			// args_2[0] = "-startPage";
			// args_2[1] = "1";
			// args_2[2] = "-endPage";
			// args_2[3] = "1";
			// args_2[4] = "-outputPrefix";
			// args_2[5] = "/home/eric/cadernos/pdfs/my_image_2.jpg";
			// args_2[6] = pdfPath;
			//
			// try {
			// // will output "my_image_1.jpg"
			// PDFToImage.main(args_1);
			// // will output "my_image_2.jpg"
			// PDFToImage.main(args_2);
			// } catch (Exception e) {
			// //logger.error(e.getMessage(),e);
			// }

			String outputPrefix = "/home/eric/cadernos/pdfs/673886(out)";
			String imageFormat = "png";
			String password = "";
			int startPage = 1;
			int endPage = 1;
			String color = "rgb";
			float cropBoxLowerLeftX = 0.0F;
		    float cropBoxLowerLeftY = 0.0F;
		    float cropBoxUpperRightX = 0.0F;
		    float cropBoxUpperRightY = 0.0F;
		    boolean showTime = false;
		    int dpi = 300;
//		    try
//		    {
//		      dpi = Toolkit.getDefaultToolkit().getScreenResolution();
//		    }
//		    catch (HeadlessException e)
//		    {
//		      dpi = 96;
//		    }

			PDDocument document = null;

			try {
				document = PDDocument.load(new File(pdfFile), password);

				ImageType imageType = null;
				if ("bilevel".equalsIgnoreCase(color)) {
					imageType = ImageType.BINARY;
				} else if ("gray".equalsIgnoreCase(color)) {
					imageType = ImageType.GRAY;
				} else if ("rgb".equalsIgnoreCase(color)) {
					imageType = ImageType.RGB;
				} else if ("rgba".equalsIgnoreCase(color)) {
					imageType = ImageType.ARGB;
				}
				if (imageType == null) {
					System.err.println("Error: Invalid color.");
					System.exit(2);
				}
				if ((cropBoxLowerLeftX != 0.0F) || (cropBoxLowerLeftY != 0.0F) || (cropBoxUpperRightX != 0.0F)
						|| (cropBoxUpperRightY != 0.0F)) {
					changeCropBox(document, cropBoxLowerLeftX, cropBoxLowerLeftY, cropBoxUpperRightX,
							cropBoxUpperRightY);
				}
				boolean success = true;
				endPage = Math.min(endPage, document.getNumberOfPages());
				PDFRenderer renderer = new PDFRenderer(document);
				for (int i = startPage - 1; i < endPage; i++) {
					BufferedImage image = renderer.renderImageWithDPI(i, dpi, imageType);
					String fileName = outputPrefix + (i + 1) + "." + imageFormat;
					success &= ImageIOUtil.writeImage(image, fileName, dpi);
				}
				if (!success) {
					System.err.println("Error: no writer found for image format '" + imageFormat + "'");
					System.exit(1);
				}
			} finally {
				if (document != null) {
					document.close();
				}
			}
			
			Long initTime = System.nanoTime();
			imagem.processa(outputPrefix+"1.png");
			Long endTime1 = System.nanoTime();

			double seconds = ((double) (endTime1 - initTime)) / 1000000000.0;

			System.out.println("tempo total =============>:" + seconds);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	private static String getImageFormats()
	  {
	    StringBuilder retval = new StringBuilder();
	    String[] formats = ImageIO.getReaderFormatNames();
	    for (int i = 0; i < formats.length; i++) {
	      if (formats[i].equalsIgnoreCase(formats[i]))
	      {
	        retval.append(formats[i]);
	        if (i + 1 < formats.length) {
	          retval.append(", ");
	        }
	      }
	    }
	    return retval.toString();
	  }
	  
	  private static void changeCropBox(PDDocument document, float a, float b, float c, float d)
	  {
	    for (PDPage page : document.getPages())
	    {
	      System.out.println("resizing page");
	      PDRectangle rectangle = new PDRectangle();
	      rectangle.setLowerLeftX(a);
	      rectangle.setLowerLeftY(b);
	      rectangle.setUpperRightX(c);
	      rectangle.setUpperRightY(d);
	      page.setCropBox(rectangle);
	    }
	  }

	/**
	 * @author Fernando H. Gomes
	 * 
	 *         Extrai imagens de um pdf criando varios Files no outputpath. Cada
	 *         File é uma imagen extraida.<br/>
	 *         A nomenclatura padrão de saida é
	 *         "Image_P[NumeroPagina]_[Cont_Imagen_P_Pagina]"
	 * 
	 * @param sourcePdf
	 * @param outputPath
	 */
	public static void ExtractImagesFromPDF(String sourcePdf, String outputPath) {
		try {
			PdfReader pdf = new PdfReader(sourcePdf);
			// percorrendo as paginas do pdf
			for (int pageNumber = 1; pageNumber <= pdf.getNumberOfPages(); pageNumber++) {
				PdfDictionary pg = pdf.getPageN(pageNumber);
				PdfDictionary res = (PdfDictionary) PdfReader.getPdfObject(pg.get(PdfName.RESOURCES));
				PdfDictionary xobj = (PdfDictionary) PdfReader.getPdfObject(res.get(PdfName.XOBJECT));
				// pegando o XOBJECT da pagina. que é o que contem as keys q serão usada pra
				// identificar cada "atributo" na pagina
				if (xobj != null) {
					int cont_img_por_pagina = 0;
					// percorrendo os "atributos" da pagina
					for (Iterator it = xobj.getKeys().iterator(); it.hasNext();) {
						PdfObject obj = xobj.get((PdfName) it.next());
						/*
						 * An indirect object is an object that has been labeled so that it can be
						 * referenced by other objects. Any type of PdfObject may be labeled as an
						 * indirect object. An indirect object consists of an object identifier, a
						 * direct object, and the endobj keyword. The object identifier consists of an
						 * integer object number, an integer generation number, and the obj keyword.
						 * This object is described in the 'Portable Document Format Reference Manual
						 * version 1.7' section 3.2.9 (page 63-65).
						 */
						if (obj.isIndirect()) {
							// pego o dicionario de chave-valor do meu "atributo"
							PdfDictionary tg = (PdfDictionary) PdfReader.getPdfObject(obj);
							PdfName type = (PdfName) PdfReader.getPdfObject(tg.get(PdfName.SUBTYPE));
							// verifico se a propriedade subtype é do tipo IMAGE
							if (PdfName.IMAGE.equals(type)) {
								// pego os bytes do meu objeto
								int XrefIndex = ((PRIndirectReference) obj).getNumber();
								PdfObject pdfObj = pdf.getPdfObject(XrefIndex);
								PdfStream pdfStrem = (PdfStream) pdfObj;
								byte[] bytes = PdfReader.getStreamBytesRaw((PRStream) pdfStrem);
								if ((bytes != null)) {
									// escrevo como um arquivo que depois eu vou usar em algum lugar
									cont_img_por_pagina++;
									FileOutputStream fw = new FileOutputStream(
											outputPath + "Image_P" + pageNumber + "_" + cont_img_por_pagina + ".jpg");
									fw.write(bytes);
									fw.flush();
									fw.close();
								}
							}
						}
					}
				}
			}
			pdf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
