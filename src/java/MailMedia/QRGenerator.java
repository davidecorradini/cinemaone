/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MailMedia;

import Beans.Film;
import Beans.Posto;
import Beans.Prenotazione;
import Beans.Prezzo;
import Beans.Spettacolo;
import Beans.Utente;
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
    public static void generate(Prenotazione pren, Utente utente, Posto posto, Prezzo prezzo, Film film, Spettacolo spettacolo, String destinationPath) throws FileNotFoundException, IOException{
        String info = utente.getIdUtente() + utente.getEmail() + prezzo.getPrezzo() + prezzo.getTipo() + posto.getIdPosto() + posto.getRiga() + posto.getColonna() + film.getTitolo() + spettacolo.getIdSpettacolo() + spettacolo.getDataOra() + "cinemaonesrl";
        MessageDigest md = null;
        System.out.println(info);
        try {
            md = MessageDigest.getInstance("SHA-256");
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
