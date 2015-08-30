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
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TicketCreator {
    private final String destinationPath;
    private static final String titleFont = FontFactory.HELVETICA_BOLD;
    private static final int titleSize = 20;
    private static final String tableFont = FontFactory.HELVETICA;
       private static final int tableSize = 16;
    private static final String footerFont = FontFactory.HELVETICA;
    private static final int footerSize = 16;
    
    TicketCreator(String destinationPath){
    this.destinationPath = destinationPath;
    }
    
    public void generaTicket(Utente utente, Prenotazione prenotazione, Spettacolo spettacolo, Film film, Sala sala) throws DocumentException, FileNotFoundException, IOException{
                
        Paragraph titlePar = new Paragraph(new Phrase("cinemaOne\n" + film.getTitolo() + "\n\n", FontFactory.getFont(titleFont, titleSize)));
        titlePar.setAlignment(Element.ALIGN_CENTER);
        PdfPTable table = new PdfPTable(2);
        PdfPCell cells[][] = new PdfPCell[2][4];
        cells[0][0] = new PdfPCell(new Paragraph(new Phrase("titolo film: ", FontFactory.getFont(tableFont, tableSize))));
        table.addCell(cells[0][0]);
        
        cells[1][0] = new PdfPCell(new Paragraph(new Phrase(film.getTitolo(), FontFactory.getFont(tableFont, tableSize))));
        table.addCell(cells[1][0]);
        
        
        cells[0][1] = new PdfPCell(new Paragraph(new Phrase("regista: ", FontFactory.getFont(tableFont, tableSize))));
        table.addCell(cells[0][1]);
        
        cells[1][1] = new PdfPCell(new Paragraph(new Phrase(film.getRegista(), FontFactory.getFont(tableFont, tableSize))));
        table.addCell(cells[1][1]);
        
        
        cells[0][2] = new PdfPCell(new Paragraph(new Phrase("data-ora: ", FontFactory.getFont(tableFont, tableSize))));
        table.addCell(cells[0][2]);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date(spettacolo.getDataOra().getTime());
        cells[1][2] = new PdfPCell(new Paragraph(new Phrase(dateFormat.format(date), FontFactory.getFont(tableFont, tableSize))));
        table.addCell(cells[1][2]);
        
        
        cells[0][3] = new PdfPCell(new Paragraph(new Phrase("nella sala: ", FontFactory.getFont(tableFont, tableSize))));
        table.addCell(cells[0][3]);
        
        cells[1][3] = new PdfPCell(new Paragraph(new Phrase(sala.getNome(), FontFactory.getFont(tableFont, tableSize))));
        table.addCell(cells[1][3]);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        
        String qrCodePath = destinationPath + prenotazione.getIdPrenotazione() + ".qrCode";
        QRGenerator.generate(prenotazione, qrCodePath);
        Image qrCode = Image.getInstance(qrCodePath);
        qrCode.setAlignment(Element.ALIGN_CENTER);
        
        Paragraph footer = new Paragraph(new Phrase("grazie per averci scelto.\nBuona visione", FontFactory.getFont(footerFont, footerSize)));
        
        String fileName = destinationPath + prenotazione.getIdPrenotazione()+ ".pdf";
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(new File(fileName)));
        
        document.open();
        document.addAuthor("cinemaOne");
        document.addSubject("prenotazione spettacolo");
        document.addTitle("biglietto di: " + utente.getEmail());
        
        document.add(titlePar);
        document.add(table);
        document.add(qrCode);
        document.add(footer);
        document.close();
        
    }
   
    /*
    public static void main(String[] args) throws DocumentException, IOException{
        TicketCreator ticketCreator = new TicketCreator("/home/enrico/Downloads/");
        Utente user = new Utente();
        user.setEmail("miaemail@mail.m");
        Film film = new Film();
        film.setTitolo("titolo film");
        film.setRegista("regista film");
        Spettacolo spett = new Spettacolo();
        spett.setDataOra(Timestamp.from(Instant.now()));
        Sala sala = new Sala();
        sala.setNome("sala bellissima");
        Prenotazione pren = new Prenotazione();
        pren.setIdPrenotazione(12345);
        ticketCreator.generaTicket(user, pren, spett, film, sala);
    }
    */
}