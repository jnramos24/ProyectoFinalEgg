package com.web.amrap.utill;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.web.amrap.entidades.Rutina;
import java.awt.Color;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

@PreAuthorize("hasAnyRole('ROLE_ADMIN','USUARIO_REGISTRADO')")
@Component("/rutina/rutinas_busqueda.html")
public class ListarRutinasEnPDF extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Rutina> listaRutinas = (List<Rutina>) model.get("rutinas");

        document.setMargins(-20, -20, 40, 20);
        document.setPageSize(PageSize.LEGAL.rotate());
        document.open();

        String nombreUsuario = "";
        String nombreRutina = "";
        String apellidoUsuario = "";

        for (Rutina rutina : listaRutinas) {

            nombreRutina = rutina.getNombre();
            nombreUsuario = rutina.getUsuario().getNombre();
            apellidoUsuario = rutina.getUsuario().getApellido();
            //como los datos se repiten, la variable toma el ultimo valor, pero da igual.
        }

        // tabla y estilo
        PdfPTable tituloTabla = new PdfPTable(1);
        Font fuenteTitulo = FontFactory.getFont("Helvetica", 16, Color.BLACK);
        // celda de la tabla y estilo
        PdfPCell celda = new PdfPCell(new Phrase("Alumno:  " + nombreUsuario + " " + apellidoUsuario + "  -  Rutinas"));
        celda.setBorder(0);
        celda.setBackgroundColor(new Color(217, 220, 223));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(20);

        tituloTabla.addCell(celda); // guardo la celda
        tituloTabla.setSpacingAfter(20); // le doy separacion del cuerpo de la tabla 

        // tabla y estilo
        PdfPTable titulosCampos = new PdfPTable(3);
        titulosCampos.setTotalWidth(new float[]{3f, 4f, 3f});  // ancho de las columbas
        Font fuenteCampos = FontFactory.getFont("Helvetica", 16, Color.DARK_GRAY);

        // celdas de los titulos de campo y estilos
        PdfPCell nombre = new PdfPCell(new Phrase("Nombre", fuenteCampos));
        nombre.setBorder(0);
        nombre.setBackgroundColor(new Color(217, 220, 223));
        nombre.setHorizontalAlignment(Element.ALIGN_CENTER);
        nombre.setVerticalAlignment(Element.ALIGN_CENTER);
        nombre.setPadding(10);

        PdfPCell objetivo = new PdfPCell(new Phrase("Objetivo", fuenteCampos));
        objetivo.setBorder(0);
        objetivo.setBackgroundColor(new Color(217, 220, 223));
        objetivo.setHorizontalAlignment(Element.ALIGN_CENTER);
        objetivo.setVerticalAlignment(Element.ALIGN_CENTER);
        objetivo.setPadding(10);

        PdfPCell estado = new PdfPCell(new Phrase("Estado", fuenteCampos));
        estado.setBorder(0);
        estado.setBackgroundColor(new Color(217, 220, 223));
        estado.setHorizontalAlignment(Element.ALIGN_CENTER);
        estado.setVerticalAlignment(Element.ALIGN_CENTER);
        estado.setPadding(10);

        titulosCampos.addCell(nombre); // guado la celda
        titulosCampos.addCell(objetivo);
        titulosCampos.addCell(estado);

        titulosCampos.setSpacingAfter(0); // le doy separacion del cuerpo de la tabla 

        // tabla con datos de los usuarios
        PdfPTable tablaRutinas = new PdfPTable(3);
        tablaRutinas.setTotalWidth(new float[]{3f, 4f, 3f});  // ancho de las columbas

        Font fuenteDatos = FontFactory.getFont("Arial", 12, Color.darkGray);

        int contador = 0;

        for (Rutina rutina : listaRutinas) {

            contador++;

            nombre = new PdfPCell(new Phrase(rutina.getNombre(), fuenteDatos));
            nombre.setBorder(0);
            nombre.setHorizontalAlignment(Element.ALIGN_CENTER);
            nombre.setVerticalAlignment(Element.ALIGN_CENTER);
            nombre.setPadding(10);
            if (contador % 2 == 0) {
                nombre.setBackgroundColor(new Color(240, 255, 255));
            } else {
                nombre.setBackgroundColor(new Color(245, 245, 220));
            }
            tablaRutinas.addCell(nombre);

            objetivo = new PdfPCell(new Phrase(rutina.getObjetivo(), fuenteDatos));
            objetivo.setBorder(0);
            objetivo.setHorizontalAlignment(Element.ALIGN_CENTER);
            objetivo.setVerticalAlignment(Element.ALIGN_CENTER);
            objetivo.setPadding(10);
            if (contador % 2 == 0) {
                objetivo.setBackgroundColor(new Color(240, 255, 255));
            } else {
                objetivo.setBackgroundColor(new Color(245, 245, 220));
            }
            tablaRutinas.addCell(objetivo);

            if (rutina.getCompletado() == null) {

                estado = new PdfPCell(new Phrase("No", fuenteDatos));
                estado.setBorder(0);
                estado.setHorizontalAlignment(Element.ALIGN_CENTER);
                estado.setVerticalAlignment(Element.ALIGN_CENTER);
                estado.setPadding(10);
                if (contador % 2 == 0) {
                    estado.setBackgroundColor(new Color(240, 255, 255));
                } else {
                    estado.setBackgroundColor(new Color(245, 245, 220));
                }
                tablaRutinas.addCell(estado);

            } else {

//                estado = new PdfPCell(new Phrase(rutina.getCompletado().toString(), fuenteDatos));
                estado = new PdfPCell(new Phrase((rutina.getCompletado() == true)?"Si":"No", fuenteDatos));
                estado.setBorder(0);
                estado.setHorizontalAlignment(Element.ALIGN_CENTER);
                estado.setVerticalAlignment(Element.ALIGN_CENTER);
                estado.setPadding(10);
                if (contador % 2 == 0) {
                    estado.setBackgroundColor(new Color(240, 255, 255));
                } else {
                    estado.setBackgroundColor(new Color(245, 245, 220));
                }
                tablaRutinas.addCell(estado);
            }

        }

        tablaRutinas.setSpacingAfter(30);

        //tabla de fecha de actualizacion
        PdfPTable tablaFechaActualizado = new PdfPTable(1);

        Date fechaActual = new Date();
        DateFormat formateadorFechaCorta = DateFormat.getDateInstance(DateFormat.SHORT);

        PdfPCell celdaFechaActualizado = new PdfPCell(new Phrase("Actualizado el: " + formateadorFechaCorta.format(fechaActual).toString(), fuenteCampos));
        celdaFechaActualizado.setBorder(0);
        celdaFechaActualizado.setHorizontalAlignment(Element.ALIGN_LEFT);
        celdaFechaActualizado.setVerticalAlignment(Element.ALIGN_CENTER);

        tablaFechaActualizado.addCell(celdaFechaActualizado);

        document.add(tituloTabla);
        document.add(titulosCampos);
        document.add(tablaRutinas);
        document.add(tablaFechaActualizado);
    }
}
