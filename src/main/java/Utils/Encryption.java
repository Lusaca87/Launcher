package Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;


public class Encryption {

    private static Cipher cipher;

    /**
     * Generate the keypair for encryption and save it to files for reuse.
     */
    public static void generateKeyPair(){
        try {
            cipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        FileOutputStream out = null;

        KeyPairGenerator genInstance = null;
        try {
            genInstance = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        genInstance.initialize(4096);
        KeyPair keyPair = genInstance.generateKeyPair();

        Key privKey = keyPair.getPrivate();
        Key pubKey = keyPair.getPublic();

        String fileName = String.format("%s.key", "privKey");
        String directory = "accounts/data";
        String fullFileName = String.format("%s/%s", directory, fileName);

        if (!Tools.checkFileExist(fileName, directory)) {
            File tempFile = new File(Tools.getDirectorys(fullFileName));
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }

            try {
                out = new FileOutputStream(fullFileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                out.write(privKey.getEncoded());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        fileName = String.format("%s.pub", "pubKey");
        fullFileName = String.format("%s/%s", directory, fileName);

        if (!Tools.checkFileExist(fileName, directory)) {
            File tempFile = new File(Tools.getDirectorys(fullFileName));
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }

            try {
                out = new FileOutputStream(fullFileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                out.write(pubKey.getEncoded());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Encypt the string and save it to file
     * @param stringToEnc the string to encrypt
     * @param fileToSave the filename to save
     */
    public static void encryptString(String stringToEnc, String fileToSave) {
        try {
            cipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        Path path = Paths.get("accounts/data/pubKey.pub");
        if(!path.toFile().exists()){
            try {
                throw new NoSuchFileException("File \"" + path + "\" did not exist!");
            } catch (NoSuchFileException e) {
                e.printStackTrace();
            }
        }

        byte[] bytes = new byte[0];
        PublicKey pub = null;


        try {
            bytes = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        X509EncodedKeySpec ks = new X509EncodedKeySpec(bytes);
        KeyFactory kf = null;
        try {
            kf = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            pub = kf.generatePublic(ks);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        try {
            cipher.init(Cipher.ENCRYPT_MODE, pub);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] cipherText = new byte[0];
        try {
            cipherText = cipher.doFinal(stringToEnc.getBytes("UTF-8"));
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            Tools.writeDataToFile(Hex.encodeHexString(cipherText), fileToSave, "file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Encrypt the data from the given file. Requires saved private keyfile
     * @param fileName the filename to load
     */
    public static byte[] decryptFile(String fileName) {

        try {
            cipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        Path path = Paths.get("accounts/data/privKey.key");
        if(!path.toFile().exists()){
            try {
                throw new NoSuchFileException("File \"" + path + "\" did not exist!");
            } catch (NoSuchFileException e) {
                e.printStackTrace();
            }

        }
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
        KeyFactory kf = null;
        try {
            kf = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        PrivateKey pvt = null;
        try {
            pvt = kf.generatePrivate(ks);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        byte[] cipherText = new byte[0];
        try {
            cipherText = Hex.decodeHex(Tools.readFileIntoString(fileName).toCharArray());
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        try {
            cipher.init(Cipher.DECRYPT_MODE, pvt);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] plaintext2 = new byte[0];
        try {
            plaintext2 = cipher.doFinal(cipherText);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return plaintext2;
    }

    /**
     * Encrypt and get the saved password
     */
    public static String getPassword(){
        return new String(decryptFile("accounts/data/credits.dat"), StandardCharsets.UTF_8);
    }

}



