import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.EOFException;

/**
 * 
 * @author gellai.com
 */
public class rtlsdr2cfile {

    private static PrintStream output;
    private static BufferedReader inputReader;
    private static FileInputStream fileReader;
    private static DataInputStream dataStream;
    private static FileOutputStream fos;
    
    public rtlsdr2cfile() {
        
        output = new PrintStream(System.out);
    }
    
    /**
     * Convert float to 4 bytes
     * @param data
     * @return 
     */
    private byte[] floatTo4Bytes(float data) {
        
        int bits = Float.floatToIntBits(data);
        
        byte[] bytes = new byte[4];
        bytes[0] = (byte)(bits & 0xff);
        bytes[1] = (byte)((bits >> 8) & 0xff);
        bytes[2] = (byte)((bits >> 16) & 0xff);
        bytes[3] = (byte)((bits >> 24) & 0xff);
        
        return bytes;
    }
    
    public static void main(String[] args) {
        
        rtlsdr2cfile rs = new rtlsdr2cfile();
        
        if(args.length != 2) {
            output.println("\nusage: rtlsdr2cfile <input file> <output file>");
            System.exit(1);
        }
        
        try {
            fos = new FileOutputStream(args[1]);
            
            try {
                fileReader = new FileInputStream(args[0]);
                dataStream = new DataInputStream(fileReader);
            
                int iI, iQ;
                float fI, fQ;
            
                boolean done = false;
                       
                while( !done ) {
                
                    iI = dataStream.readUnsignedByte();
                    iQ = dataStream.readUnsignedByte();
                
                    if(iI == -1 || iQ == -1) {
                        done = true;
                        continue;
                    }
            
                    fI = ((float) iI - 127.5F) * 0.0078125F;
                    fQ = ((float) iQ - 127.5F) * 0.0078125F;
                    
                    fos.write(rs.floatTo4Bytes(fI));
                    fos.write(rs.floatTo4Bytes(fQ));
                }
                dataStream.close();
                System.exit(0);
            }
            catch(FileNotFoundException fe) {
                output.println("\nFile not found: " + fe);
                System.exit(2);
            }
            catch(EOFException exception) {
            
            }
            catch(IOException ioe) {
                output.println("\nI/O Read error: " + ioe);
                System.exit(3);
            }
        }
        catch(IOException ioe) {
            output.println("\nI/O Write error: " + ioe);
            System.exit(4);
        }        
    }
}
