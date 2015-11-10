/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MailMedia;

import Beans.Prenotazione;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 *
 * @author enrico
 */
public class QRGenerator {
    public static void generate(Prenotazione pren, String destinationPath) throws FileNotFoundException, IOException{
        String info = pren.getIdSpettacolo() + "" + pren.getIdPosto() + "cinemaonesrl";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            //
        }
        md.update(info.getBytes());
        byte[] md5 = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : md5) {
            sb.append(String.format("%02x", b & 0xff));
        }
        String infoCr = sb.toString();
        ByteArrayOutputStream stream = QRCode.from(infoCr)
                .to(ImageType.JPG).stream();
        FileOutputStream output = new FileOutputStream(new File(destinationPath));
        output.write(stream.toByteArray());
        output.flush();
        output.close();
    }    
}
