package nlp.sina.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectFileUtil {
	
	public static  void writeObject(String filePath,Object object) {  
        try {  
            FileOutputStream outStream = new FileOutputStream(filePath);  
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outStream);  
              
            objectOutputStream.writeObject(object);  
            outStream.close();  
            System.out.println("successful");  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
	public static Object readObject(String filePath){  
        Object obj=null;
        try {  
        	FileInputStream freader = new FileInputStream(filePath);  
            ObjectInputStream objectInputStream = new ObjectInputStream(freader);  
            obj =  objectInputStream.readObject();  
            objectInputStream.close();
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
        }
        return obj;
          
    }  
	
	public static void main(String[] args){
		File f = new File("data/content/");
		System.out.println(f.getAbsolutePath());
	}
}
