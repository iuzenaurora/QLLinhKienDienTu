package com.doan.qllinhkiendientu.repositories;

import com.doan.qllinhkiendientu.models.TokenQuenMatKhau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TokenQuenMatKhauRepository extends JpaRepository<TokenQuenMatKhau, Integer> {
    Optional<TokenQuenMatKhau> findByChuoiToken(String chuoiToken);
}
