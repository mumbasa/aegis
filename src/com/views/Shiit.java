package com.views;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Shiit {

	public static void main(String[] args) throws IOException, DocumentException {
		// TODO Auto-generated method stub
		Document document = new Document();
		
		PdfWriter.getInstance(document, new FileOutputStream("/home/bryan/Desktop/Hello.pdf"));
		Font font1 = new Font(Font.FontFamily.HELVETICA  , 25, Font.BOLD);
		
		Image image = Image.getInstance("/home/bryan/Pictures/gens.jpg");
		image.scaleAbsolute(150f, 120f);
		document.open();
		document.add(new Paragraph("Dander skiler",font1));
		Paragraph p = new Paragraph("Dander skiler");
		p.setAlignment(Element.ALIGN_RIGHT);
		document.add(p);
		  document.add(new Chunk("This is sentence 1. "));
          document.add(new Chunk("This is sentence 2. "));
          document.add(new Chunk("This is sentence 3. "));
          document.add(new Chunk("This is sentence 4. "));
          document.add(new Chunk("This is sentence 5. "));
          document.add(new Chunk("This is sentence 6. "));
          
          PdfPTable table = new PdfPTable(3); // 3 columns.

          PdfPCell cell1 = new PdfPCell(new Paragraph("Cell 1"));
          PdfPCell cell2 = new PdfPCell(new Paragraph("Cell 2"));
          PdfPCell cell3 = new PdfPCell(new Paragraph("Cell 3"));

          table.addCell(cell1);
          table.addCell(cell2);
          table.addCell(cell3);
          document.add(table);
          document.add(image);
		document.close();
	}

}
