/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MailMedia;

import Beans.Film;
import Beans.Prenotazione;
import Beans.Sala;
import Beans.Spettacolo;
import Beans.Utente;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;


public class TicketCreator {
    private final String destinationPath;
    private static final String tablerightFont = FontFactory.HELVETICA;
    private static final int tableSize = 18;
    private static final String tableleftFont = FontFactory.HELVETICA_BOLD;
    private static final String footerFont = FontFactory.HELVETICA;
<<<<<<< HEAD
=======
    private static final int titleSize = 45;
    private static final String titleFont = FontFactory.HELVETICA_BOLD;
>>>>>>> newbranch
    private static final int footerSize = 14;
    
    public TicketCreator(String destinationPath){
    this.destinationPath = destinationPath;
    }
    
<<<<<<< HEAD
    public String generaTicket(Utente utente, Prenotazione prenotazione, Spettacolo spettacolo, Film film, Sala sala) throws DocumentException, FileNotFoundException, IOException{      
        
        Image image = Image.getInstance("/Users/norman/Desktop/esempio.png");
        image.setAlignment(Image.ALIGN_CENTER);
        
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        
        PdfPCell cells[][] = new PdfPCell[2][4];
        
        cells[0][0] = new PdfPCell(new Paragraph(new Phrase("Titolo film: ", FontFactory.getFont(tableleftFont, tableSize))));
        table.addCell(cells[0][0]);
        
        cells[1][0] = new PdfPCell(new Paragraph(new Phrase(film.getTitolo(), FontFactory.getFont(tablerightFont, tableSize))));
        table.addCell(cells[1][0]);
                                                            
        cells[0][1] = new PdfPCell(new Paragraph(new Phrase("Data-ora: ", FontFactory.getFont(tableleftFont, tableSize))));
=======
    public String generaTicket(Utente utente, Prenotazione prenotazione, Spettacolo spettacolo, Film film, Sala sala, Posto posto) throws DocumentException, FileNotFoundException, IOException{
        
        //Image image = Image.getInstance(destinationPath + "logo.png");
        //image.setAlignment(Image.ALIGN_CENTER);   
        
        Paragraph titlePar = new Paragraph(new Phrase("CinemaOne s.r.l.\n\n", FontFactory.getFont(titleFont, titleSize)));
        titlePar.setAlignment(Element.ALIGN_CENTER);
        
        PdfPTable table = new PdfPTable(2);
        table.setWidths(new float[]{1, 3});
        
        PdfPCell cells[][] = new PdfPCell[2][5];
        cells[0][0] = new PdfPCell(new Paragraph(new Phrase("Titolo film: ", FontFactory.getFont(tableleftFont, tableSize))));
        cells[0][0].setBorder(Rectangle.NO_BORDER);
        table.addCell(cells[0][0]);
        
        
        cells[1][0] = new PdfPCell(new Paragraph(new Phrase(film.getTitolo(), FontFactory.getFont(tablerightFont, tableSize))));
        cells[1][0].setBorder(Rectangle.NO_BORDER);
        table.addCell(cells[1][0]);
                                                            
        cells[0][1] = new PdfPCell(new Paragraph(new Phrase("Data-ora: ", FontFactory.getFont(tableleftFont, tableSize))));
        cells[0][1].setBorder(Rectangle.NO_BORDER);
>>>>>>> newbranch
        table.addCell(cells[0][1]);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date(spettacolo.getTimeStamp().getTime());
    
        cells[1][1] = new PdfPCell(new Paragraph(new Phrase(dateFormat.format(date), FontFactory.getFont(tablerightFont, tableSize))));
<<<<<<< HEAD
        table.addCell(cells[1][1]); 
        
        cells[0][2] = new PdfPCell(new Paragraph(new Phrase("Sala: ", FontFactory.getFont(tableleftFont, tableSize))));
        table.addCell(cells[0][2]);
        
        cells[1][2] = new PdfPCell(new Paragraph(new Phrase(sala.getNome(), FontFactory.getFont(tablerightFont, tableSize))));
        table.addCell(cells[1][2]);
                                                            
        cells[0][3] = new PdfPCell(new Paragraph(new Phrase("Fila/Posto: ", FontFactory.getFont(tableleftFont, tableSize))));
        table.addCell(cells[0][3]);
        
        
        //sostituire getRegista con metodo opportuno per fila e posto
        cells[1][3] = new PdfPCell(new Paragraph(new Phrase(film.getRegista(), FontFactory.getFont(tablerightFont, tableSize))));
        table.addCell(cells[1][3]);
        
=======
        cells[1][1].setBorder(Rectangle.NO_BORDER);
        table.addCell(cells[1][1]); 
        
        cells[0][2] = new PdfPCell(new Paragraph(new Phrase("Sala: ", FontFactory.getFont(tableleftFont, tableSize))));
        cells[0][2].setBorder(Rectangle.NO_BORDER);
        table.addCell(cells[0][2]);
        
        cells[1][2] = new PdfPCell(new Paragraph(new Phrase(sala.getNome(), FontFactory.getFont(tablerightFont, tableSize))));
        cells[1][2].setBorder(Rectangle.NO_BORDER);
        table.addCell(cells[1][2]);
                                                            
        cells[0][3] = new PdfPCell(new Paragraph(new Phrase("Fila/Posto: ", FontFactory.getFont(tableleftFont, tableSize))));
        cells[0][3].setBorder(Rectangle.NO_BORDER);
        table.addCell(cells[0][3]);
        
        //sostituire getRegista con metodo opportuno per fila e posto
	String postoStr = String.valueOf(posto.getRiga()).toUpperCase();
        if(posto.getColonna()<10)
            postoStr = postoStr + "0";
        postoStr = postoStr + posto.getColonna();
        cells[1][3] = new PdfPCell(new Paragraph(new Phrase(postoStr, FontFactory.getFont(tablerightFont, tableSize))));
        cells[1][3].setBorder(Rectangle.NO_BORDER);
        table.addCell(cells[1][3]);
        
        cells[0][4] = new PdfPCell(new Phrase("", FontFactory.getFont(tableleftFont, tableSize)));
        cells[0][4].setBorder(Rectangle.NO_BORDER);
        table.addCell(cells[0][4]);
        
        cells[1][4] = new PdfPCell(new Phrase("", FontFactory.getFont(tableleftFont, tableSize)));
        cells[1][4].setBorder(Rectangle.NO_BORDER);
        table.addCell(cells[1][4]);
>>>>>>> newbranch
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        String qrCodePath = destinationPath + "qrcode" + prenotazione.getIdSpettacolo() + prenotazione.getIdPosto() + ".qrCode";
        QRGenerator.generate(prenotazione, qrCodePath);
        Image qrCode = Image.getInstance(qrCodePath);
        qrCode.setAlignment(Element.ALIGN_CENTER);
        
<<<<<<< HEAD
        Paragraph footer = new Paragraph(new Phrase("Cinema One s.r.l.\nViale A. Degasperi 95, 38023 Cles TN\ninfo@cinemaone.it \n+39 347 244 3532", FontFactory.getFont(footerFont, footerSize)));
=======
        Paragraph footer = new Paragraph(new Phrase("\n\nCinema One s.r.l.\nViale A. Degasperi 95, 38023 Cles TN\ninfo@cinemaone.it \n+39 347 244 3532", FontFactory.getFont(footerFont, footerSize)));
        footer.setAlignment(Element.ALIGN_LEFT);
>>>>>>> newbranch
        
        String fileName = destinationPath + "ticket" + prenotazione.getIdSpettacolo() + prenotazione.getIdPosto() + ".pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(new File(fileName)));
        
        document.open();
<<<<<<< HEAD
        document.addAuthor("Cinema One");
        document.addSubject("Prenotazione spettacolo");
        document.addTitle("Biglietto di: " + utente.getEmail());

        document.add(image);
=======
        document.addAuthor("CinemaOne");
        document.addSubject("Prenotazione spettacolo");
        document.addTitle("Biglietto di: " + utente.getEmail());
        
        document.add(titlePar);
>>>>>>> newbranch
        document.add(table);
        document.add(qrCode);
        document.add(footer);
        document.close();
        return fileName;
    }
    
    public static void main(String[] args) throws DocumentException, IOException{
        TicketCreator ticketCreator = new TicketCreator("/Users/norman/Downloads");
        Utente user = new Utente();
        user.setEmail("miaemail@mail.m");
        Film film = new Film();
        film.setTitolo("<titolo film>");
        film.setRegista("<ABC0123456789>");
        Spettacolo spett = new Spettacolo();
        spett.setDataOra(Timestamp.from(Instant.now()));
        Sala sala = new Sala();
        sala.setNome("<n sala>");
        Prenotazione pren = new Prenotazione();
        pren.setIdPrenotazione(12345);
        ticketCreator.generaTicket(user, pren, spett, film, sala);
    }
}
