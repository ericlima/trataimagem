package trataImagens;

public class Teste {
	
	public static void main(String... args) {
	
		TrataImagem imagem = new TrataImagem();
		
		try {
			
			imagem.processa("/home/eric/cadernos/folhas/folha3.tif");
			//imagem.processa("/home/eric/cadernos/prova5t.jpg");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
}
