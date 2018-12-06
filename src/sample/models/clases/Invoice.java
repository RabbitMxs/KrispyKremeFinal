package sample.models.clases;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import sample.models.dao.MySQL;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.List;

public class Invoice {
    Connection conn;
    int id_invoice;
    double subtotal, total;
    int id_customer, id_payment,id_employee;
    Date payment_day;

    public Invoice(Connection conn) {
        this.conn = conn;
    }

    public Invoice(int id_invoice, double subtotal, double total, Date payment_date, int id_customer, int id_payment, int id_employee) {
        this.id_invoice = id_invoice;
        this.subtotal = subtotal;
        this.total = total;
        this.payment_day =payment_date;
        this.id_customer = id_customer;
        this.id_payment = id_payment;
        this.id_employee = id_employee;
    }

    public Invoice(int id_invoice, double subtotal, double total, int id_customer, int id_payment, int id_employee) {
        this.id_invoice = id_invoice;
        this.subtotal = subtotal;
        this.total = total;
        this.id_customer = id_customer;
        this.id_payment = id_payment;
        this.id_employee = id_employee;
    }

    public Date getPayment_day() {
        return payment_day;
    }

    public void setPayment_day(Date payment_day) {
        this.payment_day = payment_day;
    }

    public int getId_invoice() { return id_invoice; }

    public void setId_invoice(int id_invoice) {this.id_invoice = id_invoice; }

    public double getSubtotal() {return subtotal; }

    public void setSubtotal(double subtotal) {this.subtotal = subtotal; }

    public double getTotal() {return total; }

    public void setTotal(double total) {this.total = total; }

    public int getId_customer() {return id_customer; }

    public void setId_customer(int id_customer) {this.id_customer = id_customer; }

    public int getId_payment() {return id_payment; }

    public void setId_payment(int id_payment) {this.id_payment = id_payment; }

    public int getId_employee() {return id_employee; }

    public void setId_employee(int id_employee) {this.id_employee = id_employee; }

    @Override
    public String toString() {
        return "Invoice{" +
                "id_invoice=" + id_invoice +
                ", subtotal=" + subtotal +
                ", total=" + total +
                ", id_customer=" + id_customer +
                ", id_payment=" + id_payment +
                ", id_employee=" + id_employee +
                '}';
    }

    public static List<Invoice> LlenarPdfMes(Connection connection)
    {

        List<Invoice> invoices = new ArrayList<Invoice>();
        try
        {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM invoice WHERE payment_day BETWEEN '2018/12/01'and '2018/12/31' ORDER BY payment_day";
            ResultSet rs = statement.executeQuery(query);
            Invoice i = null;
            while(rs.next()) {

                //CustomerDAO customerDAO = new CustomerDAO(MySQL.getConnection());
                //PlansDAO plansDAO = new PlansDAO(MySQL.getConnection());
                //MonthsDAO monthsDAO = new MonthsDAO(MySQL.getConnection());

                i = new Invoice(
                        rs.getInt("id_invoice"),
                        rs.getDouble("subtotal"),
                        rs.getDouble("total"),
                        rs.getDate("payment_day"),
                        rs.getInt("id_customer"),
                        rs.getInt("id_payment"),
                        rs.getInt("id_employee")
                );
                invoices.add(i);
            }
            rs.close();
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return invoices;
    }

    public static void consulta_facturaMes(String destiny) throws DocumentException, IOException
    {
        Calendar date = new GregorianCalendar();

        Document document_invoice = new Document();

        try
        {
            PdfWriter.getInstance(document_invoice, new FileOutputStream(destiny));
            document_invoice.open();
            document_invoice.setMargins(50,50,50,50);


        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        Image Donas = Image.getInstance("src/Images/Employee.jpg");
        Donas.scaleAbsoluteHeight(100);
        Donas.scaleAbsoluteWidth(200);
        Donas.setAlignment(Element.ALIGN_CENTER);
        document_invoice.add(Donas);


        Text title = new Text("Krispy Kream DE MEXICO S.A.B DE C.V.");
        title.setFont(Font.font("Times New Roman", 12));
        Text subtitle = new Text("En tu Corazon NO.198 \n COL. CUAUCHTEMOC C.P. 06500\n MEXICO, CIUDAD DE MEXICO \n R.F.C. TME-840315-KT6 \nCENTRO DE ATENCION\n"+date.getTime());
        subtitle.setFont(Font.font("Times New Roman", 12));

        Paragraph parrafo_1 = new Paragraph();
        parrafo_1.add(title.getText());
        parrafo_1.add("\n");
        parrafo_1.add(subtitle.getText());
        parrafo_1.setAlignment(Element.ALIGN_CENTER);

        document_invoice.add(parrafo_1);

        Paragraph ph = new Paragraph(new Phrase(""));
        PdfPCell cell = new PdfPCell(ph);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderColor(BaseColor.BLACK);
        cell.setBorderWidth(2f);

        PdfPTable border = new PdfPTable(1);
        border.addCell(cell);
        border.setHorizontalAlignment(Element.ALIGN_LEFT);
        border.setWidthPercentage(100f);
        document_invoice.add(border);


        Text text1 = new Text("Captura de Reporte");
        text1.setFont(Font.font("Times New Roman", 18));

        Paragraph p2 = new Paragraph();
        p2.add(text1.getText());
        p2.add("\n\n");
        p2.setAlignment(Element.ALIGN_CENTER);
        document_invoice.add(p2);

        PdfPTable table_invoicePDF = new PdfPTable(7);
        table_invoicePDF.setWidthPercentage(100);

        Text idventa = new Text("Id Venta");
        idventa.setFont(Font.font("Arial", FontWeight.BOLD,14));
        Paragraph col_0 = new Paragraph(idventa.getText());
        table_invoicePDF.addCell(col_0.getContent());

        Text Subtotal = new Text("Subtotal");
        Subtotal.setFont(Font.font("Arial", FontWeight.BOLD,14));
        Paragraph col_1 = new Paragraph(Subtotal.getText());
        table_invoicePDF.addCell(col_1.getContent());

        Text Total = new Text("Total");
        Total.setFont(Font.font("Arial", FontWeight.BOLD,14));
        Paragraph col_2 = new Paragraph(Total.getText());
        table_invoicePDF.addCell(col_2.getContent());

        Text Fecha = new Text("Fecha");
        Total.setFont(Font.font("Arial", FontWeight.BOLD,14));
        Paragraph col_3 = new Paragraph(Fecha.getText());
        table_invoicePDF.addCell(col_3.getContent());

        Text IdCliente = new Text("Id Cliente");
        IdCliente.setFont(Font.font("Arial", FontWeight.BOLD,14));
        Paragraph col_4 = new Paragraph(IdCliente.getText());
        table_invoicePDF.addCell(col_4.getContent());

        Text Idpago = new Text("Id Pago");
        Idpago.setFont(Font.font("Arial", FontWeight.BOLD,14));
        Paragraph col_5 = new Paragraph(Idpago.getText());
        table_invoicePDF.addCell(col_5.getContent());

        Text IdEmpleado = new Text("Id Empleado");
        IdEmpleado.setFont(Font.font("Arial", FontWeight.BOLD,14));
        Paragraph col_6 = new Paragraph(IdEmpleado.getText());
        table_invoicePDF.addCell(col_6.getContent());

        MySQL.Connect();
        //CustomerDAO customerDAO = new CustomerDAO(conex.getConnection());

        List<Invoice> invoices = new ArrayList<>(LlenarPdfMes(MySQL.getConnection()));
        for (int i = 0; i < invoices.size(); i++)
        {
            Invoice I = invoices.get(i);
            table_invoicePDF.addCell(String.valueOf(I.id_invoice));
            table_invoicePDF.addCell(String.valueOf(I.getSubtotal()));
            table_invoicePDF.addCell(String.valueOf(I.getTotal()));
            table_invoicePDF.addCell(String.valueOf(I.getPayment_day()));
            table_invoicePDF.addCell(String.valueOf(I.getId_customer()));
            table_invoicePDF.addCell(String.valueOf(I.getId_payment()));
            table_invoicePDF.addCell(String.valueOf(I.getId_employee()));
        }

        document_invoice.add(table_invoicePDF);

        //Close document
        document_invoice.close();
    }

    public static List<Invoice> LlenarPdfAño(Connection connection)
    {

        List<Invoice> invoices = new ArrayList<Invoice>();
        try
        {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM invoice WHERE payment_day BETWEEN '2018/01/01'and '2018/12/31' ORDER BY payment_day";
            ResultSet rs = statement.executeQuery(query);
            Invoice i = null;
            while(rs.next()) {

                //CustomerDAO customerDAO = new CustomerDAO(MySQL.getConnection());
                //PlansDAO plansDAO = new PlansDAO(MySQL.getConnection());
                //MonthsDAO monthsDAO = new MonthsDAO(MySQL.getConnection());

                i = new Invoice(
                        rs.getInt("id_invoice"),
                        rs.getDouble("subtotal"),
                        rs.getDouble("total"),
                        rs.getDate("payment_day"),
                        rs.getInt("id_customer"),
                        rs.getInt("id_payment"),
                        rs.getInt("id_employee")
                );
                invoices.add(i);
            }
            rs.close();
            statement.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Error al recuperar información...");
        }
        return invoices;
    }

    public static void consulta_facturaAño(String destiny) throws DocumentException, IOException
    {
        Calendar date = new GregorianCalendar();

        Document document_invoice = new Document();

        try
        {
            PdfWriter.getInstance(document_invoice, new FileOutputStream(destiny));
            document_invoice.open();
            document_invoice.setMargins(50,50,50,50);


        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        Image Donas = Image.getInstance("src/Images/Employee.jpg");
        Donas.scaleAbsoluteHeight(100);
        Donas.scaleAbsoluteWidth(200);
        Donas.setAlignment(Element.ALIGN_CENTER);
        document_invoice.add(Donas);


        Text title = new Text("Krispy Kream DE MEXICO S.A.B DE C.V.");
        title.setFont(Font.font("Times New Roman", 12));
        Text subtitle = new Text("En tu Corazon NO.198 \n COL. CUAUCHTEMOC C.P. 06500\n MEXICO, CIUDAD DE MEXICO \n R.F.C. TME-840315-KT6 \nCENTRO DE ATENCION\n"+date.getTime());
        subtitle.setFont(Font.font("Times New Roman", 12));

        Paragraph parrafo_1 = new Paragraph();
        parrafo_1.add(title.getText());
        parrafo_1.add("\n");
        parrafo_1.add(subtitle.getText());
        parrafo_1.setAlignment(Element.ALIGN_CENTER);

        document_invoice.add(parrafo_1);

        Paragraph ph = new Paragraph(new Phrase(""));
        PdfPCell cell = new PdfPCell(ph);
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderColor(BaseColor.BLACK);
        cell.setBorderWidth(2f);

        PdfPTable border = new PdfPTable(1);
        border.addCell(cell);
        border.setHorizontalAlignment(Element.ALIGN_LEFT);
        border.setWidthPercentage(100f);
        document_invoice.add(border);


        Text text1 = new Text("Captura de Reporte");
        text1.setFont(Font.font("Times New Roman", 18));

        Paragraph p2 = new Paragraph();
        p2.add(text1.getText());
        p2.add("\n\n");
        p2.setAlignment(Element.ALIGN_CENTER);
        document_invoice.add(p2);

        PdfPTable table_invoicePDF = new PdfPTable(7);
        table_invoicePDF.setWidthPercentage(100);

        Text idventa = new Text("Id Venta");
        idventa.setFont(Font.font("Arial", FontWeight.BOLD,14));
        Paragraph col_0 = new Paragraph(idventa.getText());
        table_invoicePDF.addCell(col_0.getContent());

        Text Subtotal = new Text("Subtotal");
        Subtotal.setFont(Font.font("Arial", FontWeight.BOLD,14));
        Paragraph col_1 = new Paragraph(Subtotal.getText());
        table_invoicePDF.addCell(col_1.getContent());

        Text Total = new Text("Total");
        Total.setFont(Font.font("Arial", FontWeight.BOLD,14));
        Paragraph col_2 = new Paragraph(Total.getText());
        table_invoicePDF.addCell(col_2.getContent());

        Text Fecha = new Text("Fecha");
        Total.setFont(Font.font("Arial", FontWeight.BOLD,14));
        Paragraph col_3 = new Paragraph(Fecha.getText());
        table_invoicePDF.addCell(col_3.getContent());

        Text IdCliente = new Text("Id Cliente");
        IdCliente.setFont(Font.font("Arial", FontWeight.BOLD,14));
        Paragraph col_4 = new Paragraph(IdCliente.getText());
        table_invoicePDF.addCell(col_4.getContent());

        Text Idpago = new Text("Id Pago");
        Idpago.setFont(Font.font("Arial", FontWeight.BOLD,14));
        Paragraph col_5 = new Paragraph(Idpago.getText());
        table_invoicePDF.addCell(col_5.getContent());

        Text IdEmpleado = new Text("Id Empleado");
        IdEmpleado.setFont(Font.font("Arial", FontWeight.BOLD,14));
        Paragraph col_6 = new Paragraph(IdEmpleado.getText());
        table_invoicePDF.addCell(col_6.getContent());

        MySQL.Connect();
        //CustomerDAO customerDAO = new CustomerDAO(conex.getConnection());

        List<Invoice> invoices = new ArrayList<>(LlenarPdfAño(MySQL.getConnection()));
        for (int i = 0; i < invoices.size(); i++)
        {
            Invoice I = invoices.get(i);
            table_invoicePDF.addCell(String.valueOf(I.id_invoice));
            table_invoicePDF.addCell(String.valueOf(I.getSubtotal()));
            table_invoicePDF.addCell(String.valueOf(I.getTotal()));
            table_invoicePDF.addCell(String.valueOf(I.getPayment_day()));
            table_invoicePDF.addCell(String.valueOf(I.getId_customer()));
            table_invoicePDF.addCell(String.valueOf(I.getId_payment()));
            table_invoicePDF.addCell(String.valueOf(I.getId_employee()));
        }

        document_invoice.add(table_invoicePDF);

        //Close document
        document_invoice.close();
    }
}
