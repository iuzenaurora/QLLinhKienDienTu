package com.doan.qllinhkiendientu.repositories;

import com.doan.qllinhkiendientu.models.BinhLuan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BinhLuanRepository extends JpaRepository<BinhLuan, String> {
}
