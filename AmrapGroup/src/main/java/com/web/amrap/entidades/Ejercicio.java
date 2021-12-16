package com.web.amrap.entidades;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table
public class Ejercicio implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String nombre;
    private String categoria;
    private String video;
    private Integer series;
    private Integer repeticiones;
    private Integer pausa;
    private Integer dificultad;
    private String kilogramos;
    private String notas;
    private String atencion;

}
