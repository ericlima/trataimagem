package trataImagens;

public class Teste {
	
	public static void main(String... args) {
	
		TrataImagem imagem = new TrataImagem();
		
		try {
			
			Long initTime = System.nanoTime();
			
			imagem.processa("/home/eric/cadernos/folhas/folha5.tif");
			
			Long endTime1 = System.nanoTime();

			double seconds = ((double) (endTime1 - initTime)) / 1000000000.0;

			System.out.println("tempo leitura imagem:" + seconds);

			//imagem.apura("p1.jpg");			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
}
