package com.doan.qllinhkiendientu.repositories;

import com.doan.qllinhkiendientu.models.ChiTietGioHang;
import com.doan.qllinhkiendientu.models.ChiTietGioHangId;
import com.doan.qllinhkiendientu.models.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChiTietGioHangRepository extends JpaRepository<ChiTietGioHang, ChiTietGioHangId> {
    List<ChiTietGioHang> findByKhachHang(KhachHang khachHang);
    void deleteByKhachHang(KhachHang khachHang);
}
