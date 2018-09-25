import java.io.*;
public class InputStreamExample {

public static void main(String[] args)throws Exception{

          // Variable declarations
          String srg = "1##2#34###12";
          byte ary[] = srg.getBytes();
          int i, j;

          // Input Stream declarations
          ByteArrayInputStream array = new ByteArrayInputStream(ary);
          PushbackInputStream push = new PushbackInputStream(array);

          /* ALGORITHM
          ** Changes something
          */
              while( (i = push.read())!= -1) {
                  if(i == '#') {
                      if( (j = push.read()) == '#'){
                           System.out.print("**");
                      }else {
                          push.unread(j);
                          System.out.print((char)i);
                      }
                  }else {
                              System.out.print((char)i);
                  }
             }
  }
}
