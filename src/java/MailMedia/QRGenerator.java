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
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 *
 * @author enrico
 */
public class QRGenerator {
    public static void generate(Prenotazione pren, String destinationPath) throws FileNotFoundException, IOException{
        ByteArrayOutputStream stream = QRCode.from(Integer.toString(pren.getIdPrenotazione()))
                .to(ImageType.JPG).stream();
        FileOutputStream output = new FileOutputStream(new File(destinationPath));
        output.write(stream.toByteArray());
        output.flush();
        output.close();
    }
    
}
