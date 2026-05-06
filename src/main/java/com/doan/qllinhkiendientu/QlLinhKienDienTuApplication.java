package com.doan.qllinhkiendientu;

import com.doan.qllinhkiendientu.models.BinhLuan;
import com.doan.qllinhkiendientu.models.SanPham;
import com.doan.qllinhkiendientu.repositories.BinhLuanRepository;
import com.doan.qllinhkiendientu.repositories.SanPhamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class QlLinhKienDienTuApplication {

    public static void main(String[] args) {
        SpringApplication.run(QlLinhKienDienTuApplication.class, args);
    }

    @Bean
    public CommandLineRunner testDatabase(BinhLuanRepository binhLuanRepository, SanPhamRepository sanPhamRepository) {
        return args -> {
            System.out.println("=====================================");
            System.out.println("BẮT ĐẦU TEST KẾT NỐI DATABASE");

            try {
                List<SanPham> dsSanPham = sanPhamRepository.findAll();
                System.out.println("Số lượng sản phẩm tìm thấy: " + dsSanPham.size());
                for (SanPham sp : dsSanPham) {
                    System.out.println("- Mã SP: " + sp.getMaSp() + " | Tên: " + sp.getTenSp() + " | Giá: " + sp.getGia());
                }

                List<BinhLuan> danhSachBinhLuan = binhLuanRepository.findAll();
                System.out.println("Số lượng bình luận tìm thấy: " + danhSachBinhLuan.size());
            } catch (Exception e) {
                System.err.println("Lỗi kết nối hoặc truy vấn: " + e.getMessage());
            }

            System.out.println("=====================================");
        };
    }
}
