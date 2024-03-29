package com.api.ponline.model.Entity.jadwal;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.api.ponline.model.Entity.Anggota.Anggota;
import com.api.ponline.model.Entity.kolam.Kolam;

@Entity
@Table(name = "TAB_JADWAL")
public class Jadwal implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "khelyqlath")
    private Long id;

    @Column(name = "jyiycynlnd")
    private Date dateToDo;

    @ManyToOne
    private Kolam kolam;

    @ManyToOne
    private Anggota anggota;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Kolam getKolam() {
        return kolam;
    }

    public void setKolam(Kolam kolam) {
        this.kolam = kolam;
    }

    public Anggota getAnggota() {
        return anggota;
    }

    public void setAnggota(Anggota anggota) {
        this.anggota = anggota;
    }

    public Date getDateToDo() {
        return dateToDo;
    }

    public void setDateToDo(Date dateToDo) {
        this.dateToDo = dateToDo;
    }

    
}
