import java.io.*;
public class DecryptorDRMain {
    public static void main(String[] args) throws IOException
    {
        BufferedReader t = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Inserisci la il percorso del file da decriptare");
        String tc = t.readLine();

        System.out.println("Inserisci la password");
        String pw = t.readLine();

        File f = new File(tc);
        if(f.exists())
        {
            EncryptorDR e = new EncryptorDR(f, pw);
            e.decryptFile();
        }
    }
}