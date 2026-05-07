package com.doan.qllinhkiendientu.repositories;

import com.doan.qllinhkiendientu.models.KhuyenMai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhuyenMaiRepository extends JpaRepository<KhuyenMai, String> {
}
