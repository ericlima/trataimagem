package trataImagens;

import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.ghost4j.document.DocumentException;
import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.RendererException;
import org.ghost4j.renderer.SimpleRenderer;

public class TrataPdf {

	public static void main(String... args) {

		PDFDocument document = new PDFDocument();

		try {
			document.load(new File("/home/eric/Downloads/FJ-27.pdf"));

//			// create analyzer
//			InkAnalyzer analyzer = new InkAnalyzer();
//
//			// analyze
//			List<AnalysisItem> coverageData = analyzer.analyze(document);
//
//			// print result
//			for (AnalysisItem analysisItem : coverageData) {
//				System.out.println(analysisItem);
//
//			}

			// create renderer
			SimpleRenderer renderer = new SimpleRenderer();

			// set resolution (in DPI)
			renderer.setResolution(300);

			// render
			List<Image> images = renderer.render(document, 1, 1);

			for (int i = 0; i < images.size(); i++) {
				ImageIO.write((RenderedImage) images.get(i), "png", new File((i + 1) + ".png"));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RendererException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
