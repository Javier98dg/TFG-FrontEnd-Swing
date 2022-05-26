package com.frontswing.client.entity;

import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;

@Entity
@Table(name = "SICATPERSO")
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SicatpersoEntity {
    @Id
    @Column(name = "CDNIPE", nullable = false, length = 9)
    private String id;

    @Column(name = "DAP1PE", nullable = false, length = 25)
    private String primerApe;

    @Column(name = "DAP2PE", length = 25)
    private String segundoApe;

    @Column(name = "DNOMPE", length = 20)
    private String nombre;

//    @Column(name = "DDIRPE", length = 60)
//    private String direccion;

    @Column(name = "CPROVI")
    private Integer codProvincia;

    @Column(name = "CMUNIC")
    private Integer codMunicipio;

//    @Column(name = "CLOCAL")
//    private Integer codLocalidad;

    @Column(name = "CODPOS")
    private Integer codPostal;

    @Column(name = "NUTFNO")
    private Integer telefono;

//    @Column(name = "NUMFAX")
//    private Integer fax;

//    @Column(name = "FNACIM")
//    private LocalDate fnacimiento;

//    @Column(name = "CSTCIV")
//    private String estadoCivil;

//    @Column(name = "CDNIP1", length = 9)
//    private String dnicifRepre;

    @Column(name = "FECACT", nullable = false)
    private LocalDateTime fechaActualizacion;

    @Column(name = "FECBAJ")
    private LocalDateTime fechaBaja;

//    @Column(name = "NMOVIL")
//    private Integer telefonoMov;

    @Column(name = "DEMAIL", length = 100)
    private String correoElec;

//    @Column(name = "CDSEXO")
//    private String codSexo;

//    @Column(name = "CUSERD", length = 10)
//    private String usuario;

//    @Column(name = "CPROAU")
//    private Integer codAutoria;

    public SicatpersoEntity(String id,String primerApe,String segundoApe,String nombre,Integer codProvincia,Integer codMunicipio,Integer codPostal,Integer telefono,String correoElec){
        this.id = id;
        this.primerApe = primerApe;
        this.segundoApe = segundoApe;
        this.nombre = nombre;
        this.codProvincia = codProvincia;
        this.codMunicipio = codMunicipio;
        this.codPostal = codPostal;
        this.telefono = telefono;
        this.correoElec = correoElec;
    }   
}