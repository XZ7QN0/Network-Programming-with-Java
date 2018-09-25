import java.io.*;
public class BufferInputStreamFromTxtFile{
 public static void main(String args[]){

   // Attempt to read file as a FileInputStream, then chain to BufferedInputStream
   try{

     /* Possible Method to Chain Filters
     ** Problem: Intermixing calls to different streams connected to the same
     ** source may violate several implicit contracts of the filter streams.
     ** It's recommended not to do this.
     ***** CODE BELOW *****
     FileInputStream fin=new FileInputStream("testoust.txt");
     BufferedInputStream bin=new BufferedInputStream(fin);
     ***** CODE COMPLETE *****
     */

     /*
     ** No way to access underlying file input stream.
     ** Now we can't accidentally read from the FileInputStream and corrupt the buffer.
     ** BufferedInputStream is used polymorphically as an instance of InputStream
     ***** CODE BELOW *****
     InputStream in = new FileInputStream("testout.txt");
     in = new BufferedInputStream(in);
     ***** CODE COMPLETE *****
     */

     /*
     ** Construct of one stream directly inside another.
     ** Filters cannot be disconnected from a stream.
     ***** CODE BELOW *****
     DataInputStream in = new DataInputStream(
                          new BufferedInputStream(
                          new FileInputStream("testout.txt")));
     ***** CODE COMPLETE *****
     */

     // Makes code more readable and understandable
     DataInputStream in = new DataInputStream(
                          new BufferedInputStream(
                          new FileInputStream("testout.txt")));
     int i;
     while((i=in.read())!=-1){
       System.out.print((char)i);
    }
    in.close();
     //bin.close();
     //fin.close();
  } // In case file doesn't exist
  catch(Exception e){System.out.println(e);}
 }
}
