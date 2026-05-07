package com.doan.qllinhkiendientu.repositories;

import com.doan.qllinhkiendientu.models.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, String> {
}
