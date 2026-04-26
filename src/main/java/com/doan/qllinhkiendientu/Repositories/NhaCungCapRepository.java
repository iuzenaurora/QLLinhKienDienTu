package com.doan.qllinhkiendientu.repositories;

import com.doan.qllinhkiendientu.models.NhaCungCap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhaCungCapRepository extends JpaRepository<NhaCungCap, String> {
}
