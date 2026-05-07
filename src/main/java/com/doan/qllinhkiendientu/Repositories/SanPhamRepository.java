package com.doan.qllinhkiendientu.repositories;

import com.doan.qllinhkiendientu.models.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, String> {
    List<SanPham> findByTenSpContainingIgnoreCase(String tenSp);
    List<SanPham> findByDanhMucMaDm(String maDm);

    @Query("SELECT s FROM SanPham s WHERE s.soLuong <= s.nguongToiThieu")
    List<SanPham> findSanPhamDuoiNguong();
}