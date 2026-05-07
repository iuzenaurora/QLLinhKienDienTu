package com.doan.qllinhkiendientu.repositories;

import com.doan.qllinhkiendientu.models.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, String> {
}
