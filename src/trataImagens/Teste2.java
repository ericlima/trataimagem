package trataImagens;

import java.io.File;
import java.io.IOException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

public class Teste2 {
	public static void main(String[] args){
        String data = "1485-383-ABC";
        String format = "jpg";
        File file = new File("/home/eric/Downloads/testing.jpg");
        int width = 10;
        int height = 100;
        try {
            MultiFormatWriter writer = new MultiFormatWriter();
            BitMatrix matrix = writer.encode(data, BarcodeFormat.CODE_128, width, height);
            MatrixToImageWriter.writeToFile(matrix, format, file);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
