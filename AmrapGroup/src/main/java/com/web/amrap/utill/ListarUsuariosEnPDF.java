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
import com.web.amrap.entidades.Usuario;
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
@Component("/usuarios_busqueda.html")
public class ListarUsuariosEnPDF extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Usuario> listaUsuarios = (List<Usuario>) model.get("listaUsuarios");

        document.setMargins(-20, -20, 40, 20);
        document.setPageSize(PageSize.LEGAL.rotate());
        document.open();

        // tabla y estilo
        PdfPTable tituloTabla = new PdfPTable(1);
        Font fuenteTitulo = FontFactory.getFont("Helvetica", 16, Color.BLACK);
        // celda de la tabla y estilo
        PdfPCell celda = new PdfPCell(new Phrase("Lista de Usuarios", fuenteTitulo));
        celda.setBorder(0);
        celda.setBackgroundColor(new Color(217, 220, 223));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(20);

        tituloTabla.addCell(celda); // guardo la celda
        tituloTabla.setSpacingAfter(30); // le doy separacion del cuerpo de la tabla 

        // tabla y estilo
        PdfPTable titulosCampos = new PdfPTable(6);
        titulosCampos.setTotalWidth(new float[]{1f, 1f, 3f, 2f, 1f, 1f});  // ancho de las columbas
        Font fuenteCampos = FontFactory.getFont("Helvetica", 16, Color.DARK_GRAY);
        // celda de la tabla y estilo
        PdfPCell nombre = new PdfPCell(new Phrase("Nombre", fuenteCampos));
        nombre.setBorder(0);
        nombre.setBackgroundColor(new Color(217, 220, 223));
        nombre.setHorizontalAlignment(Element.ALIGN_CENTER);
        nombre.setVerticalAlignment(Element.ALIGN_CENTER);
        nombre.setPadding(10);

        PdfPCell apellido = new PdfPCell(new Phrase("Apellido", fuenteCampos));
        apellido.setBorder(0);
        apellido.setBackgroundColor(new Color(217, 220, 223));
        apellido.setHorizontalAlignment(Element.ALIGN_CENTER);
        apellido.setVerticalAlignment(Element.ALIGN_CENTER);
        apellido.setPadding(10);

        PdfPCell email = new PdfPCell(new Phrase("E-mail", fuenteCampos));
        email.setBorder(0);
        email.setBackgroundColor(new Color(217, 220, 223));
        email.setHorizontalAlignment(Element.ALIGN_CENTER);
        email.setVerticalAlignment(Element.ALIGN_CENTER);
        email.setPadding(10);

        PdfPCell rol = new PdfPCell(new Phrase("Rol", fuenteCampos));
        rol.setBorder(0);
        rol.setBackgroundColor(new Color(217, 220, 223));
        rol.setHorizontalAlignment(Element.ALIGN_CENTER);
        rol.setVerticalAlignment(Element.ALIGN_CENTER);
        rol.setPadding(10);

        PdfPCell alta = new PdfPCell(new Phrase("Alta", fuenteCampos));
        alta.setBorder(0);
        alta.setBackgroundColor(new Color(217, 220, 223));
        alta.setHorizontalAlignment(Element.ALIGN_CENTER);
        alta.setVerticalAlignment(Element.ALIGN_CENTER);
        alta.setPadding(10);

        PdfPCell baja = new PdfPCell(new Phrase("Baja", fuenteCampos));
        baja.setBorder(0);
        baja.setBackgroundColor(new Color(217, 220, 223));
        baja.setHorizontalAlignment(Element.ALIGN_CENTER);
        baja.setVerticalAlignment(Element.ALIGN_CENTER);
        baja.setPadding(10);

        titulosCampos.addCell(nombre); // guado la celda
        titulosCampos.addCell(apellido);
        titulosCampos.addCell(email);
        titulosCampos.addCell(rol);
        titulosCampos.addCell(alta);
        titulosCampos.addCell(baja);

        titulosCampos.setSpacingAfter(0); // le doy separacion del cuerpo de la tabla 

        // tabla con datos de los usuarios
        PdfPTable tablaUsuarios = new PdfPTable(6);
        tablaUsuarios.setTotalWidth(new float[]{1f, 1f, 3f, 2f, 1f, 1f}); // ancho de las columnas

        Font fuenteDatos = FontFactory.getFont("Arial", 12, Color.darkGray);

        int contador = 0;

        for (Usuario usuario : listaUsuarios) {

            contador++;

            nombre = new PdfPCell(new Phrase(usuario.getNombre(), fuenteDatos));
            nombre.setBorder(0);
            nombre.setHorizontalAlignment(Element.ALIGN_CENTER);
            nombre.setVerticalAlignment(Element.ALIGN_CENTER);
            nombre.setPadding(10);
            if (contador % 2 == 0) {
                nombre.setBackgroundColor(new Color(240, 255, 255));
            } else{
                nombre.setBackgroundColor(new Color(245, 245, 220));
            }
            tablaUsuarios.addCell(nombre);

            apellido = new PdfPCell(new Phrase(usuario.getApellido(), fuenteDatos));
            apellido.setBorder(0);
            apellido.setHorizontalAlignment(Element.ALIGN_CENTER);
            apellido.setVerticalAlignment(Element.ALIGN_CENTER);
            apellido.setPadding(10);
            if (contador % 2 == 0) {
                apellido.setBackgroundColor(new Color(240, 255, 255));
            } else{
                apellido.setBackgroundColor(new Color(245, 245, 220));
            }
            tablaUsuarios.addCell(apellido);

            email = new PdfPCell(new Phrase(usuario.getEmail(), fuenteDatos));
            email.setBorder(0);
            email.setHorizontalAlignment(Element.ALIGN_CENTER);
            email.setVerticalAlignment(Element.ALIGN_CENTER);
            email.setPadding(10);
            if (contador % 2 == 0) {
                email.setBackgroundColor(new Color(240, 255, 255));
            }else{
                email.setBackgroundColor(new Color(245, 245, 220));
            }
            tablaUsuarios.addCell(email);

            rol = new PdfPCell(new Phrase(usuario.getRol().name(), fuenteDatos));
            rol.setBorder(0);
            rol.setHorizontalAlignment(Element.ALIGN_CENTER);
            rol.setVerticalAlignment(Element.ALIGN_CENTER);
            rol.setPadding(10);
            if (contador % 2 == 0) {
                rol.setBackgroundColor(new Color(240, 255, 255));
            }else
                rol.setBackgroundColor(new Color(245, 245, 220));
            tablaUsuarios.addCell(rol);

            alta = new PdfPCell(new Phrase(usuario.getAlta().toString(), fuenteDatos));
            alta.setBorder(0);
            alta.setHorizontalAlignment(Element.ALIGN_CENTER);
            alta.setVerticalAlignment(Element.ALIGN_CENTER);
            alta.setPadding(10);
            if (contador % 2 == 0) {
                alta.setBackgroundColor(new Color(240, 255, 255));
            } else{
                alta.setBackgroundColor(new Color(245, 245, 220));
            }
            tablaUsuarios.addCell(alta);

            System.out.println("baja: " + usuario.getBaja());

            if (usuario.getBaja() == null) {

                baja = new PdfPCell(new Phrase(" ", fuenteDatos));
                baja.setBorder(0);
                baja.setHorizontalAlignment(Element.ALIGN_CENTER);
                baja.setVerticalAlignment(Element.ALIGN_CENTER);
                baja.setPadding(10);
                if (contador % 2 == 0) {
                    baja.setBackgroundColor(new Color(240, 255, 255));
                } else{
                    baja.setBackgroundColor(new Color(245, 245, 220));
                }
                tablaUsuarios.addCell(baja);

            } else {

                baja = new PdfPCell(new Phrase(usuario.getBaja().toString(), fuenteDatos));
                baja.setBorder(0);
                baja.setHorizontalAlignment(Element.ALIGN_CENTER);
                baja.setVerticalAlignment(Element.ALIGN_CENTER);
                baja.setPadding(10);
                if (contador % 2 == 0) {
                    baja.setBackgroundColor(new Color(240, 255, 255));
                } else{
                    baja.setBackgroundColor(new Color(245, 245, 220));
                }
                tablaUsuarios.addCell(baja);
            }
        }

//        listaUsuarios.forEach(usuario -> {
//            tablaUsuarios.addCell(usuario.getNombre());
//            tablaUsuarios.addCell(usuario.getApellido());
//            tablaUsuarios.addCell(usuario.getEmail());
//            tablaUsuarios.addCell(usuario.getRol().name());
//            tablaUsuarios.addCell(usuario.getAlta().toString());
//
//            if (usuario.getBaja() != null) {
//                tablaUsuarios.addCell(usuario.getBaja().toString());
//            } else {
//                tablaUsuarios.addCell(" ");
//            }
//        });
        tablaUsuarios.setSpacingAfter(30);

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
        document.add(tablaUsuarios);
        document.add(tablaFechaActualizado);
    }

}
