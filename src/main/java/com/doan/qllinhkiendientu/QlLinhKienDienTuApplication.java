package com.doan.qllinhkiendientu;

import com.doan.qllinhkiendientu.models.BinhLuan;
import com.doan.qllinhkiendientu.repositories.BinhLuanRepository;
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
    public CommandLineRunner testDatabase(BinhLuanRepository binhLuanRepository) {
        return args -> {
            System.out.println("=====================================");
            System.out.println("BẮT ĐẦU TEST KẾT NỐI DATABASE");

            try {
                List<BinhLuan> danhSachBinhLuan = binhLuanRepository.findAll();
                System.out.println("Số lượng bình luận tìm thấy: " + danhSachBinhLuan.size());
                for (BinhLuan bl : danhSachBinhLuan) {
                    System.out.println("- Mã BL: " + bl.getMaBl() + " | Nội dung: " + bl.getNoiDung());
                }
            } catch (Exception e) {
                System.err.println("Lỗi kết nối: " + e.getMessage());
            }

            System.out.println("=====================================");
        };
    }
}
