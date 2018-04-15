package trataImagens;

import java.io.IOException;

public class Teste {
	
	public static void main(String... args) {
	
		TrataImagem imagem = new TrataImagem();
		
		try {
			imagem.processa();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
}
