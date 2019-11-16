import java.io.*;
import java.security.MessageDigest;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;

public class EncryptorDR
{
    private File origin, finished;
    private String password;
    public EncryptorDR()
    {

    }
    public EncryptorDR(File f, String pw)
    {
        if(f.exists())
        {
            this.origin = f;
            this.password = pw;
        }else
        {
            System.out.println("File non esistente");
        }

    }
    public void encryptFile()
    {
        try
        {
            String encFilePath = this.origin.getAbsolutePath() + ".drenc";
            this.finished = new File(encFilePath);
            this.finished.createNewFile();
            FileInputStream originStream = new FileInputStream(this.origin);
            byte[] buffer = new byte[64];
            byte[] encKey = calculateSHA512(this.password);
            long nFullRounds = this.origin.length() / 64;

            FileOutputStream destintinationStream = new FileOutputStream(this.finished);
            for(long i = 0; i < nFullRounds; i++)
            {
                originStream.read(buffer);
                byte[] encryptedBuffer = cryptBuffer(buffer, encKey);
                destintinationStream.write(encryptedBuffer);
            }
            int remain = (int) this.origin.length() % 64;
            buffer = new byte[remain];
            originStream.read(buffer);
            destintinationStream.write(cryptBuffer(buffer, encKey));
            destintinationStream.close();
            originStream.close();

        }catch(IOException ioe)
        {
            System.out.println("Impossibile creare il file criptato");
            ioe.printStackTrace();
        }
    }
    public void decryptFile()
    {
        try
        {
            String decFilePath = this.origin.getAbsolutePath().replace(".drenc", "");
            this.finished = new File(decFilePath);
            this.finished.createNewFile();
            FileInputStream originStream = new FileInputStream(this.origin);
            byte[] buffer = new byte[64];
            byte[] encKey = calculateSHA512(this.password);
            long nFullRounds = this.origin.length() / 64;

            FileOutputStream destintinationStream = new FileOutputStream(this.finished);
            for(long i = 0; i < nFullRounds; i++)
            {
                originStream.read(buffer);
                byte[] decryptedBuffer = decryptBuffer(buffer, encKey);
                destintinationStream.write(decryptedBuffer);
            }
            int remain = (int) this.origin.length() % 64;
            buffer = new byte[remain];
            originStream.read(buffer);
            destintinationStream.write(decryptBuffer(buffer, encKey));
            destintinationStream.close();
            originStream.close();

        }catch(IOException ioe)
        {
            System.out.println("Impossibile creare il file criptato");
            ioe.printStackTrace();
        }
    }
    public byte[] cryptBuffer(byte[] buffer, byte[] encKey)
    {
        byte[] encriptedBuffer = new byte[buffer.length];
        for(int i = 0; i < buffer.length; i++)
        {
            encriptedBuffer[i] = (byte) ((int) buffer[i] + (int) encKey[i]);
        }
        return encriptedBuffer;
    }
    public byte[] decryptBuffer(byte[] buffer, byte[] encKey)
    {
        byte[] decriptedBuffer = new byte[buffer.length];
        for(int i = 0; i < buffer.length; i++)
        {
           decriptedBuffer[i] = (byte) ((int) buffer[i] - (int) encKey[i]);

        }
        return decriptedBuffer;
    }
    public byte[] calculateSHA512(String input)
    {
        MessageDigest sha512Conv = null;
        try
        {
            sha512Conv = MessageDigest.getInstance("SHA-512");
        }catch(NoSuchAlgorithmException nsae)
        {
            nsae.printStackTrace();
        }
        byte[] enckKey = sha512Conv.digest(input.getBytes());
        return enckKey;
    }
    public String encryptString(String tc, String pw)
    {
        byte[] byteString = tc.getBytes();
        byte[] encKey = calculateSHA512(pw);
        byte[] encString = new byte[byteString.length];
        int lByteString = byteString.length;
        int lEncKey = encKey.length;
        int nFullRounds = lByteString / lEncKey;
        int index = 0;
        for(int i = 0; i < nFullRounds; i++)
        {
            index = i * 64;
            for(int k = 0; k < lEncKey; k++)
            {
                encString[index] = (byte) ((int) byteString[index] + (int) encKey[k]);
                index++;
            }
        }

        for(int i = 0; index < lByteString; i++)
        {
            encString[index] = (byte) ((int) byteString[index] + (int) encKey[i]);
            index++;
        }

        return new String(encString);
    }

    public String decryptString(String td, String pw)
    {
        byte[] byteString = td.getBytes();
        byte[] encKey = calculateSHA512(pw);
        byte[] encString = new byte[byteString.length];
        int lByteString = byteString.length;
        int lEncKey = encKey.length;
        int nFullRounds = lByteString / lEncKey;
        int index = 0;
        for(int i = 0; i < nFullRounds; i++)
        {
            index = i * 64;
            for(int k = 0; k < lEncKey; k++)
            {
                encString[index] = (byte) ((int) byteString[index] - (int) encKey[k]);
                index++;
            }
        }

        for(int i = 0; index < lByteString; i++)
        {
            encString[index] = (byte) ((int) byteString[index] - (int) encKey[i]);
            index++;
        }

        return new String(encString);
    }
}
