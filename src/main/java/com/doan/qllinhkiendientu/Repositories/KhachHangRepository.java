package com.doan.qllinhkiendientu.repositories;

import com.doan.qllinhkiendientu.models.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, String> {
}
