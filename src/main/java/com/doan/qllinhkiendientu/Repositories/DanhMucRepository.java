package com.doan.qllinhkiendientu.repositories;

import com.doan.qllinhkiendientu.models.DanhMuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc, String> {
}
