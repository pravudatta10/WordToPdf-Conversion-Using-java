package com.pravudatta.wordtopdf;
import com.documents4j.api.DocumentType; 
import com.documents4j.api.IConverter; 
import com.documents4j.job.LocalConverter;
import org.apache.commons.io.output.ByteArrayOutputStream;
import java.io.*;  
import java.util.concurrent.Future; 
import java.util.concurrent.TimeUnit;

public class WordToPdfConversion 
{
	public  static  void main(String [] args) throws Exception
	{
		 
		 ByteArrayOutputStream byteoutput = new ByteArrayOutputStream();
		 	//Only take the file which has .docx extension
	        InputStream in = new BufferedInputStream(new FileInputStream("C://Users//Desktop//input//MyReport.docx"));
	        IConverter converter = LocalConverter.builder()
	                .baseFolder(new File("C://Users//Desktop//Junk"))
	                .workerPool(20, 25, 2, TimeUnit.SECONDS)
	                .processTimeout(5, TimeUnit.SECONDS)
	                .build();

	        Future<Boolean> conversion = converter
	                .convert(in).as(DocumentType.DOCX)
	                .to(byteoutput).as(DocumentType.PDF)
	                .prioritizeWith(1000) // optional
	                .schedule();
	        conversion.get();
	        
	        
	        try (OutputStream outputStream = new FileOutputStream("C://Users//Desktop//OUTPUT//Report.pdf")) {
	            byteoutput.writeTo(outputStream);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        in.close();
	        byteoutput.close();
	        System.out.println("Pdf Created Succesfully");
	}
}
