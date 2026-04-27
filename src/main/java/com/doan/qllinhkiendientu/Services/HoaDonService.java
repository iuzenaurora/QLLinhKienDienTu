package com.doan.qllinhkiendientu.Services;

import com.doan.qllinhkiendientu.models.*;
import com.doan.qllinhkiendientu.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;
    @Autowired
    private ChiTietHoaDonRepository chiTietHoaDonRepository;
    @Autowired
    private SanPhamRepository sanPhamRepository;
    @Autowired
    private ChiTietGioHangRepository chiTietGioHangRepository;

    @Transactional
    public HoaDon taoDonHangTuGioHang(KhachHang khachHang, String diaChi, String sdt, String phuongThuc) {
        List<ChiTietGioHang> gioHang = chiTietGioHangRepository.findByKhachHang(khachHang);
        if (gioHang.isEmpty()) {
            throw new RuntimeException("Giỏ hàng trống!");
        }

        // 1. Tạo đối tượng Hóa đơn
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMaHd("HD" + (System.currentTimeMillis() % 100000)); // Mã ngắn gọn hơn
        hoaDon.setKhachHang(khachHang);
        hoaDon.setNgayLap(LocalDate.now());
        hoaDon.setDiaChiGiaoHang(diaChi);
        hoaDon.setSoDienThoaiGiaoHang(sdt);
        hoaDon.setPhuongThucThanhToan(phuongThuc);

        if (phuongThuc.equalsIgnoreCase("Tiền mặt")) {
            hoaDon.setTrangThai("Chờ giao hàng (Thanh toán khi nhận hàng)");
        } else {
            hoaDon.setTrangThai("Chờ thanh toán (Chuyển khoản ngân hàng)");
        }

        BigDecimal tongTien = BigDecimal.ZERO;
        HoaDon savedHoaDon = hoaDonRepository.save(hoaDon);

        // 2. Chuyển từ Giỏ hàng sang Chi tiết hóa đơn
        for (ChiTietGioHang item : gioHang) {
            SanPham sp = item.getSanPham();
            
            if (sp.getSoLuong() < item.getSoLuong()) {
                throw new RuntimeException("Sản phẩm " + sp.getTenSp() + " không đủ tồn kho!");
            }

            // Trừ tồn kho
            sp.setSoLuong(sp.getSoLuong() - item.getSoLuong());
            sanPhamRepository.save(sp);

            // Tạo chi tiết hóa đơn
            ChiTietHoaDon cthd = new ChiTietHoaDon();
            cthd.setHoaDon(savedHoaDon);
            cthd.setSanPham(sp);
            cthd.setSoLuong(item.getSoLuong());
            cthd.setDonGia(sp.getGia());
            chiTietHoaDonRepository.save(cthd);

            // Tính tổng tiền: gia * soLuong
            BigDecimal subTotal = sp.getGia().multiply(new BigDecimal(item.getSoLuong()));
            tongTien = tongTien.add(subTotal);
        }

        // 3. Cập nhật tổng tiền và lưu lại
        savedHoaDon.setTongTien(tongTien);
        
        // 4. Xóa giỏ hàng sau khi đặt hàng thành công
        chiTietGioHangRepository.deleteByKhachHang(khachHang);

        return hoaDonRepository.save(savedHoaDon);
    }

    // 5. Xác nhận thanh toán thành công (Dùng cho VNPay/Ngân hàng)
    @Transactional
    public void xacNhanThanhToan(String maHd, String maGiaoDichVNPAY) {
        HoaDon hd = hoaDonRepository.findById(maHd)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn!"));

        hd.setTrangThai("Đã thanh toán - Chờ giao hàng");
        hd.setNgayThanhToan(LocalDateTime.now());

        hoaDonRepository.save(hd);
    }

    // 6. Hủy đơn hàng và HOÀN LẠI số lượng tồn kho
    @Transactional
    public void huyDonHang(String maHd) {
        HoaDon hd = hoaDonRepository.findById(maHd)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn!"));

        // Không cho phép hủy nếu đã giao
        if ("Đã giao".equals(hd.getTrangThai())) {
            throw new RuntimeException("Không thể hủy đơn hàng đã giao thành công!");
        }

        // Hoàn lại số lượng cho kho sản phẩm
        for (ChiTietHoaDon ct : hd.getDsChiTiet()) {
            SanPham sp = ct.getSanPham();
            sp.setSoLuong(sp.getSoLuong() + ct.getSoLuong());
            sanPhamRepository.save(sp);
        }

        hd.setTrangThai("Đã hủy");
        hoaDonRepository.save(hd);
    }

    // 7. Admin cập nhật trạng thái đơn hàng (Đang giao, Đã giao...)
    @Transactional
    public void capNhatTrangThai(String maHd, String trangThaiMoi) {
        HoaDon hd = hoaDonRepository.findById(maHd)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn!"));

        hd.setTrangThai(trangThaiMoi);
        hoaDonRepository.save(hd);
    }
}