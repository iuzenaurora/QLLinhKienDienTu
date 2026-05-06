package com.doan.qllinhkiendientu.Services;

import com.doan.qllinhkiendientu.models.*;
import com.doan.qllinhkiendientu.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GioHangService {

    @Autowired
    private ChiTietGioHangRepository chiTietGioHangRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    public List<ChiTietGioHang> getGioHangByKhachHang(KhachHang khachHang) {
        return chiTietGioHangRepository.findByKhachHang(khachHang);
    }

    @Transactional
    public void themVaoGio(KhachHang khachHang, String maSp, int soLuong) {
        SanPham sanPham = sanPhamRepository.findById(maSp)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        // Kiểm tra xem sản phẩm đã có trong giỏ chưa
        ChiTietGioHangId id = new ChiTietGioHangId(khachHang.getMaKh(), maSp);
        Optional<ChiTietGioHang> existingItem = chiTietGioHangRepository.findById(id);

        if (existingItem.isPresent()) {
            ChiTietGioHang item = existingItem.get();
            item.setSoLuong(item.getSoLuong() + soLuong);
            chiTietGioHangRepository.save(item);
        } else {
            ChiTietGioHang newItem = new ChiTietGioHang();
            newItem.setKhachHang(khachHang);
            newItem.setSanPham(sanPham);
            newItem.setSoLuong(soLuong);
            chiTietGioHangRepository.save(newItem);
        }
    }

    @Transactional
    public void xoaKhoiGio(KhachHang khachHang, String maSp) {
        ChiTietGioHangId id = new ChiTietGioHangId(khachHang.getMaKh(), maSp);
        chiTietGioHangRepository.deleteById(id);
    }

    @Transactional
    public void capNhatSoLuong(KhachHang khachHang, String maSp, int soLuong) {
        ChiTietGioHangId id = new ChiTietGioHangId(khachHang.getMaKh(), maSp);
        ChiTietGioHang item = chiTietGioHangRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không có trong giỏ hàng"));
        
        if (soLuong <= 0) {
            chiTietGioHangRepository.delete(item);
        } else {
            item.setSoLuong(soLuong);
            chiTietGioHangRepository.save(item);
        }
    }
}
