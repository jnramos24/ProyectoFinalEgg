package com.web.amrap.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    
    @OneToOne
    private EjercicioNombre ejercicioNombre;
    
    @ManyToOne
    private Rutina rutina;
    @Temporal(TemporalType.TIMESTAMP)
    private Date alta;
    private String identificador;    
    private Integer series;
    private Integer repeticiones;
    private Integer pausa;
    private Integer dificultad;
    private String kilogramos;
    private String notas;
    private String atencion;
    private Boolean completado;

}
