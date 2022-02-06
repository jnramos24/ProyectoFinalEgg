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
import com.web.amrap.entidades.Ejercicio;
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

@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
@Component("/ejercicio/ejercicios_busqueda.html")
public class ListarEjerciciosEnPDF extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Ejercicio> listaEjercicios = (List<Ejercicio>) model.get("listaEjercicios");

        document.setMargins(-20, -20, 40, 20);
        document.setPageSize(PageSize.LEGAL.rotate());
        document.open();

        // tabla y estilo
        PdfPTable tituloTabla = new PdfPTable(1);
        Font fuenteTitulo = FontFactory.getFont("Helvetica", 16, Color.BLACK);
        // celda de la tabla y estilo
        PdfPCell celda = new PdfPCell(new Phrase("Lista de Ejercicios", fuenteTitulo));
        celda.setBorder(0);
        celda.setBackgroundColor(new Color(217, 220, 223));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(20);

        tituloTabla.addCell(celda); // guardo la celda
        tituloTabla.setSpacingAfter(20); // le doy separacion del cuerpo de la tabla 

        // tabla y estilo
        PdfPTable titulosCampos = new PdfPTable(7);
        titulosCampos.setTotalWidth(new float[]{3f, 1f, 1f, 1f, 1f, 1f,1f});  // ancho de las columbas
        Font fuenteCampos = FontFactory.getFont("Helvetica", 16, Color.DARK_GRAY);
        
        // celdas de la tabla y estilo
        PdfPCell nombre = new PdfPCell(new Phrase("Ejercicio", fuenteCampos));
        nombre.setBorder(0);
        nombre.setBackgroundColor(new Color(217, 220, 223));
        nombre.setHorizontalAlignment(Element.ALIGN_CENTER);
        nombre.setVerticalAlignment(Element.ALIGN_CENTER);
        nombre.setPadding(10);

        PdfPCell series = new PdfPCell(new Phrase("Series", fuenteCampos));
        series.setBorder(0);
        series.setBackgroundColor(new Color(217, 220, 223));
        series.setHorizontalAlignment(Element.ALIGN_CENTER);
        series.setVerticalAlignment(Element.ALIGN_CENTER);
        series.setPadding(10);

        PdfPCell repeticiones = new PdfPCell(new Phrase("Repeticiones", fuenteCampos));
        repeticiones.setBorder(0);
        repeticiones.setBackgroundColor(new Color(217, 220, 223));
        repeticiones.setHorizontalAlignment(Element.ALIGN_CENTER);
        repeticiones.setVerticalAlignment(Element.ALIGN_CENTER);
        repeticiones.setPadding(10);

        PdfPCell pausa = new PdfPCell(new Phrase("Pausa", fuenteCampos));
        pausa.setBorder(0);
        pausa.setBackgroundColor(new Color(217, 220, 223));
        pausa.setHorizontalAlignment(Element.ALIGN_CENTER);
        pausa.setVerticalAlignment(Element.ALIGN_CENTER);
        pausa.setPadding(10);

        PdfPCell dificultad = new PdfPCell(new Phrase("Dificultad", fuenteCampos));
        dificultad.setBorder(0);
        dificultad.setBackgroundColor(new Color(217, 220, 223));
        dificultad.setHorizontalAlignment(Element.ALIGN_CENTER);
        dificultad.setVerticalAlignment(Element.ALIGN_CENTER);
        dificultad.setPadding(10);

        PdfPCell kilogramos = new PdfPCell(new Phrase("Kilogramos", fuenteCampos));
        kilogramos.setBorder(0);
        kilogramos.setBackgroundColor(new Color(217, 220, 223));
        kilogramos.setHorizontalAlignment(Element.ALIGN_CENTER);
        kilogramos.setVerticalAlignment(Element.ALIGN_CENTER);
        kilogramos.setPadding(10);

        PdfPCell notas = new PdfPCell(new Phrase("Notas", fuenteCampos));
        notas.setBorder(0);
        notas.setBackgroundColor(new Color(217, 220, 223));
        notas.setHorizontalAlignment(Element.ALIGN_CENTER);
        notas.setVerticalAlignment(Element.ALIGN_CENTER);
        notas.setPadding(10);

        titulosCampos.addCell(nombre); // guado la celda
        titulosCampos.addCell(series);
        titulosCampos.addCell(repeticiones);
        titulosCampos.addCell(pausa);
        titulosCampos.addCell(dificultad);
        titulosCampos.addCell(kilogramos);
        titulosCampos.addCell(notas);

        titulosCampos.setSpacingAfter(0); // le doy separacion del cuerpo de la tabla 

        // tabla con datos de los usuarios
        PdfPTable tablaEjercicios = new PdfPTable(7);
        tablaEjercicios.setTotalWidth(new float[]{3f, 1f, 1f, 1f, 1f, 1f,1f});  // ancho de las columbas

        Font fuenteDatos = FontFactory.getFont("Arial", 12, Color.darkGray);
        
        int contador = 0;

        for (Ejercicio ejercicio : listaEjercicios) {
            
//            contador++;
//            
//            if (contador % 2 == 0) {
//                nombre.setBackgroundColor(new Color(240, 255, 255));
//                series.setBackgroundColor(new Color(240, 255, 255));
//                repeticiones.setBackgroundColor(new Color(240, 255, 255));
//                pausa.setBackgroundColor(new Color(240, 255, 255));
//                dificultad.setBackgroundColor(new Color(240, 255, 255));
//                kilogramos.setBackgroundColor(new Color(240, 255, 255));         
//                
//            }
            
            nombre = new PdfPCell(new Phrase(ejercicio.getEjercicioNombre().getNombre(), fuenteDatos));
            nombre.setBorder(0);
            nombre.setHorizontalAlignment(Element.ALIGN_CENTER);
            nombre.setVerticalAlignment(Element.ALIGN_CENTER);
            tablaEjercicios.addCell(nombre);

            series = new PdfPCell(new Phrase(ejercicio.getSeries().toString(), fuenteDatos));
            series.setBorder(0);
            series.setHorizontalAlignment(Element.ALIGN_CENTER);
            series.setVerticalAlignment(Element.ALIGN_CENTER);
            tablaEjercicios.addCell(series);

            repeticiones = new PdfPCell(new Phrase(ejercicio.getRepeticiones().toString(), fuenteDatos));
            repeticiones.setBorder(0);
            repeticiones.setHorizontalAlignment(Element.ALIGN_CENTER);
            repeticiones.setVerticalAlignment(Element.ALIGN_CENTER);
            tablaEjercicios.addCell(repeticiones);

            pausa = new PdfPCell(new Phrase(ejercicio.getPausa().toString(), fuenteDatos));
            pausa.setBorder(0);
            pausa.setHorizontalAlignment(Element.ALIGN_CENTER);
            pausa.setVerticalAlignment(Element.ALIGN_CENTER);
            tablaEjercicios.addCell(pausa);

            if (ejercicio.getDificultad() == null) {

                dificultad = new PdfPCell(new Phrase(" ", fuenteDatos));
                dificultad.setBorder(0);
                dificultad.setHorizontalAlignment(Element.ALIGN_CENTER);
                dificultad.setVerticalAlignment(Element.ALIGN_CENTER);
                tablaEjercicios.addCell(dificultad);         

            } else {

                dificultad = new PdfPCell(new Phrase(ejercicio.getDificultad().toString(), fuenteDatos));
                dificultad.setBorder(0);
                dificultad.setHorizontalAlignment(Element.ALIGN_CENTER);
                dificultad.setVerticalAlignment(Element.ALIGN_CENTER);
                tablaEjercicios.addCell(dificultad);
            }
 

            if (ejercicio.getKilogramos() == null) {

                kilogramos = new PdfPCell(new Phrase(" ", fuenteDatos));
                kilogramos.setBorder(0);
                kilogramos.setHorizontalAlignment(Element.ALIGN_CENTER);
                kilogramos.setVerticalAlignment(Element.ALIGN_CENTER);

                tablaEjercicios.addCell(kilogramos);

            } else {

                kilogramos = new PdfPCell(new Phrase(ejercicio.getKilogramos().toString(), fuenteDatos));
                kilogramos.setBorder(0);
                kilogramos.setHorizontalAlignment(Element.ALIGN_CENTER);
                kilogramos.setVerticalAlignment(Element.ALIGN_CENTER);
                tablaEjercicios.addCell(kilogramos);
            }
            
              if (ejercicio.getNotas()== null) {

                notas = new PdfPCell(new Phrase(" ", fuenteDatos));
                notas.setBorder(0);
                notas.setHorizontalAlignment(Element.ALIGN_CENTER);
                notas.setVerticalAlignment(Element.ALIGN_CENTER);

                tablaEjercicios.addCell(notas);

            } else {

                notas = new PdfPCell(new Phrase(ejercicio.getKilogramos().toString(), fuenteDatos));
                notas.setBorder(0);
                notas.setHorizontalAlignment(Element.ALIGN_CENTER);
                notas.setVerticalAlignment(Element.ALIGN_CENTER);
                tablaEjercicios.addCell(notas);
            }
        }

        tablaEjercicios.setSpacingAfter(30);

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
        document.add(tablaEjercicios);
        document.add(tablaFechaActualizado);
    }

}
