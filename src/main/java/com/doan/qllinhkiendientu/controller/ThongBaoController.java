package com.doan.qllinhkiendientu.controller;

import com.doan.qllinhkiendientu.Repositories.ThongBaoRepository;
import com.doan.qllinhkiendientu.models.ThongBao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thong-bao")
@CrossOrigin("*")
public class ThongBaoController {
    @Autowired
    private ThongBaoRepository thongBaoRepository;

    @GetMapping("/chua-doc")
    public List<ThongBao> getThongBaoChuaDoc() {
        return thongBaoRepository.findByTrangThaiFalseOrderByNgayTaoDesc();
    }

    @PutMapping("/da-xem/{id}")
    public void danhDauDaXem(@PathVariable Integer id) {
        ThongBao tb = thongBaoRepository.findById(id).orElse(null);
        if (tb != null) {
            tb.setTrangThai(true); // Chuyển trạng thái thành Đã xem
            thongBaoRepository.save(tb);
        }
    }
}