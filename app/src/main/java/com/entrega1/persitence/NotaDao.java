package com.entrega1.persitence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.entrega1.dto.NotaDTO;

import java.util.List;

@Dao
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
