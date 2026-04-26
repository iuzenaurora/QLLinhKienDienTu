package Repositories;

import Models.BinhLuan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BinhLuanRepository extends JpaRepository<BinhLuan, String> {
    // Để trống, Spring Boot sẽ tự "lo" phần code SQL bên dưới
}