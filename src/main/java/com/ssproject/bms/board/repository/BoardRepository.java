package com.ssproject.bms.board.repository;

import com.ssproject.bms.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    @Modifying
    @Query(value = "update BoardEntity b set b.inqlreCo=b.inqlreCo+1 where b.nttId=:nttId")
    void updateHits(@Param("nttId") int nttId);
}
