package com.doan.qllinhkiendientu.repositories;

import com.doan.qllinhkiendientu.models.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, String> {
}
