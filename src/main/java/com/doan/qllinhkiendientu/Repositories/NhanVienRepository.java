package com.doan.qllinhkiendientu.repositories;

import com.doan.qllinhkiendientu.models.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, String> {
}
