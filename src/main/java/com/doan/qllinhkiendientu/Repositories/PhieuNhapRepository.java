package com.doan.qllinhkiendientu.repositories;

import com.doan.qllinhkiendientu.models.PhieuNhap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhieuNhapRepository extends JpaRepository<PhieuNhap, String> {
}
