package com.entrega1.persitence;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.entrega1.dto.NotaDTO;

import java.util.List;

public interface NotaDao {

    @Insert
    Long insert (NotaDTO notaDTO);

    @Delete
    void delete (NotaDTO notaDTO);

    @Update
    void update (NotaDTO notaDTO);

    @Query("SELECT * FROM tb_nota WHERE id = :id")
    NotaDTO findById(Long id);

    @Query("SELECT * FROM tb_nota ORDER BY anoLetivo ASC")
    List<NotaDTO> listAll();

}
