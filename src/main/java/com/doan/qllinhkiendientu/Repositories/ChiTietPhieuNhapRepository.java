package com.doan.qllinhkiendientu.repositories;

import com.doan.qllinhkiendientu.models.ChiTietPhieuNhap;
import com.doan.qllinhkiendientu.models.ChiTietPhieuNhapId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietPhieuNhapRepository extends JpaRepository<ChiTietPhieuNhap, ChiTietPhieuNhapId> {
}
