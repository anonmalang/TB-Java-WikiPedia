import java.io.IOException;
import java.net.MalformedURLException;

import org.json.*;
public class helper {
	api Api 	=	 new api();
	
			
	public void renderIsi(String[] data , String img) throws MalformedURLException{
		
		System.out.println(data[0]);
		formIsi isi 	=	 new formIsi(data , img);
		isi.setVisible(true);
	}
	public void prosesIsi(String text) throws IOException{
		String [] response 	=	 Api.responseApi(text); 
		String img 			=	 Api.getImage(text);
		renderIsi(response , img);
	}
}
