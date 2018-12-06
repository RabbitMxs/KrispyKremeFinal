package sample.Employee_View.Shopping_Cart;


import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.DashedLine;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.test.annotations.WrapToTest;

import java.io.IOException;

@WrapToTest
public class Ticket {

    int id_employee;
    double subtotal,total;

    public static final String krispy = "src/Images/krispy.png";

    public Ticket(int id_employee, double subtotal, double total) {
        this.id_employee = id_employee;
        this.subtotal = subtotal;
        this.total = total;
    }

    public void createPdf(String dest) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        //Document document = new Document(pdf, pageSize);
        Document document = new Document(pdf);
        document.setMargins(50, 180, 50, 180);


        PdfFont font1 = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
        PdfFont font2 = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);

        Text title =new Text("KRISPY KREME S.A.B DE C.V.").setFont(font1).setFontSize(15);
        Text subtitle = new Text("PARQUE CELAYA NO.198 \n COL. LATINOAMERICANA C.P. 8400\n MEXICO, \n R.F.C. TME-840315-KT6 \nCENTRO DE ATENCION").setFont(font2).setFontSize(10);

        Paragraph p1 = new Paragraph().add(title).add("\n").add(subtitle);
        p1.setTextAlignment(TextAlignment.CENTER);

        DottedLine dottedLine = new DottedLine(1);
        DashedLine dashedLine = new DashedLine(1);

        Text text1 = new Text("COMPROBANTE DE PAGO").setFont(font2).setFontSize(10).setBold();
        Paragraph p2 = new Paragraph().add(text1).add("\n\n");
        p2.setTextAlignment(TextAlignment.CENTER);

        Text text3 = new Text("EMPLEADO: "+id_employee).setFont(font2).setFontSize(10);
        Paragraph p3 = new Paragraph().add(text3);
        p3.setTextAlignment(TextAlignment.LEFT);

        Text text7 = new Text("SUBTOTAL  : "+subtotal).setFont(font2).setFontSize(10);
        Text text8 = new Text("TOTAL : "+total).setFont(font2).setFontSize(10);
        /*Text text9 = new Text("TOTAL A PAGAR  : "+(invo.getId_plan().getTotal()+invo.getId_plan().getTotal()*.16)).setFont(font2).setFontSize(10);
        Text text10 = new Text("DEPOSITO  : "+deposito).setFont(font2).setFontSize(10);
        Text text11 = new Text("CAMBIO   : "+(deposito-(invo.getId_plan().getTotal()+invo.getId_plan().getTotal()*.16))).setFont(font2).setFontSize(10);*/
        Paragraph p4 = new Paragraph().add(text7).add("\n").add(text8);
        p3.setTextAlignment(TextAlignment.LEFT);


        Image krispy  = new Image(ImageDataFactory.create("src/Images/Login.png"));


        //Add paragraph to the document
        document.add(krispy).add(new Paragraph("\n")).add(p1).add(new LineSeparator(dashedLine)).add(p2).add(p3);
        document.add(new LineSeparator(dashedLine)).add(p4);

        //Close document
        document.close();
    }
}

