package com.ssproject.bms.book.repository;

import com.ssproject.bms.book.entity.BookOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepositoryTests extends JpaRepository<BookOrderEntity, Integer> {
}
