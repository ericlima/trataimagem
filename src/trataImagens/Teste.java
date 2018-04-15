package trataImagens;

public class Teste {
	
	public static void main(String... args) {
	
		TrataImagem imagem = new TrataImagem();
		
		try {
			
			imagem.processa("/home/eric/cadernos/folhas/folha2.tif");
			
			//imagem.apura("p1.jpg");			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
}
