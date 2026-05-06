package com.doan.qllinhkiendientu.controller;

import com.doan.qllinhkiendientu.Services.GioHangService;
import com.doan.qllinhkiendientu.models.KhachHang;
import com.doan.qllinhkiendientu.repositories.KhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @ModelAttribute
    public void addAttributes(Model model) {
        // Lấy khách hàng demo
        List<KhachHang> customers = khachHangRepository.findAll();
        if (!customers.isEmpty()) {
            KhachHang kh = customers.get(0);
            int cartCount = gioHangService.getGioHangByKhachHang(kh).size();
            model.addAttribute("cartCount", cartCount);
        } else {
            model.addAttribute("cartCount", 0);
        }
    }
}
