package com.doan.qllinhkiendientu.repositories;

import com.doan.qllinhkiendientu.models.ChiTietHoaDon;
import com.doan.qllinhkiendientu.models.ChiTietHoaDonId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietHoaDonRepository extends JpaRepository<ChiTietHoaDon, ChiTietHoaDonId> {
}
