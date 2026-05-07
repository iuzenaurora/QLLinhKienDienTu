package com.doan.qllinhkiendientu.Services;

import com.doan.qllinhkiendientu.repositories.SanPhamRepository;
import com.doan.qllinhkiendientu.repositories.ThongBaoRepository;
import com.doan.qllinhkiendientu.models.SanPham;
import com.doan.qllinhkiendientu.models.ThongBao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TonKhoService {
    @Autowired
    private SanPhamRepository sanPhamRepository;
    @Autowired
    private ThongBaoRepository thongBaoRepository;

    @Scheduled(cron = "0 0 8 * * ?") // Quét tự động mỗi ngày
    public void quatTonKhoTuDong() {
        List<SanPham> listSapHet = sanPhamRepository.findSanPhamDuoiNguong();

        for (SanPham sp : listSapHet) {
            ThongBao tb = new ThongBao();
            tb.setTieuDe("Cảnh báo tồn kho thấp: " + sp.getMaSp());
            tb.setNoiDung("Sản phẩm " + sp.getTenSp() + " hiện chỉ còn " + sp.getSoLuong());
            tb.setNgayTao(LocalDateTime.now());
            tb.setTrangThai(false);

            thongBaoRepository.save(tb);
        }
    }
}