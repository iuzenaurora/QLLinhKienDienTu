package com.doan.qllinhkiendientu.Services;

import com.doan.qllinhkiendientu.Repositories.SanPhamRepository;
import com.doan.qllinhkiendientu.Repositories.ThongBaoRepository;
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

    @Scheduled(fixedRate = 10000)
    public void quatTonKhoTuDong() {
        List<SanPham> listSapHet = sanPhamRepository.findSanPhamDuoiNguong();

        for (SanPham sp : listSapHet) {
            // KIỂM TRA: Nếu đã có thông báo chưa đọc cho sản phẩm này rồi thì bỏ qua
            boolean daCoThongBao = thongBaoRepository.existsByTieuDeContainingAndTrangThaiFalse(sp.getMaSp());

            if (!daCoThongBao) {
                ThongBao tb = new ThongBao();
                tb.setTieuDe("Cảnh báo tồn kho thấp: " + sp.getMaSp());
                tb.setNoiDung("Sản phẩm " + sp.getTenSp() + " hiện chỉ còn " + sp.getSoLuong());
                tb.setNgayTao(LocalDateTime.now());
                tb.setTrangThai(false);

                thongBaoRepository.save(tb);
                System.out.println("Đã tạo thông báo mới cho: " + sp.getMaSp());
            } else {
                System.out.println("Thông báo cho " + sp.getMaSp() + " đã tồn tại, không tạo thêm.");
            }
        }
    }
}