package com.doan.qllinhkiendientu.controller;

import com.doan.qllinhkiendientu.Services.GioHangService;
import com.doan.qllinhkiendientu.Services.HoaDonService;
import com.doan.qllinhkiendientu.models.ChiTietGioHang;
import com.doan.qllinhkiendientu.models.KhachHang;
import com.doan.qllinhkiendientu.models.SanPham;
import com.doan.qllinhkiendientu.repositories.KhachHangRepository;
import com.doan.qllinhkiendientu.repositories.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class GioHangController {

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    // Helper để lấy khách hàng mặc định (do chưa có login)
    private KhachHang getCurrentCustomer() {
        List<KhachHang> customers = khachHangRepository.findAll();
        if (customers.isEmpty()) {
            throw new RuntimeException("Vui lòng thêm khách hàng vào database để dùng tính năng giỏ hàng");
        }
        return customers.get(0); // Lấy khách hàng đầu tiên làm demo
    }

    @GetMapping
    public String viewCart(Model model) {
        KhachHang kh = getCurrentCustomer();
        List<ChiTietGioHang> items = gioHangService.getGioHangByKhachHang(kh);
        
        BigDecimal total = items.stream()
                .map(item -> item.getSanPham().getGia().multiply(new BigDecimal(item.getSoLuong())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("items", items);
        model.addAttribute("total", total);
        model.addAttribute("pageTitle", "Giỏ hàng");
        
        return "customers/cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam String maSp, @RequestParam(defaultValue = "1") int soLuong) {
        KhachHang kh = getCurrentCustomer();
        gioHangService.themVaoGio(kh, maSp, soLuong);
        return "redirect:/cart";
    }

    @GetMapping("/add/{maSp}")
    public String addToCartGet(@PathVariable String maSp) {
        KhachHang kh = getCurrentCustomer();
        gioHangService.themVaoGio(kh, maSp, 1);
        return "redirect:/cart";
    }

    @GetMapping("/buy-now/{maSp}")
    public String buyNow(@PathVariable String maSp, @RequestParam(defaultValue = "1") int soLuong) {
        return "redirect:/cart/checkout?maSp=" + maSp + "&soLuong=" + soLuong;
    }

    @GetMapping("/checkout")
    public String checkout(@RequestParam(required = false) String maSp,
                           @RequestParam(required = false, defaultValue = "1") int soLuong,
                           Model model) {
        KhachHang kh = getCurrentCustomer();
        BigDecimal total;
        
        if (maSp != null) {
            // Trường hợp mua ngay 1 sản phẩm
            SanPham sp = sanPhamRepository.findById(maSp)
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
            
            total = sp.getGia().multiply(new BigDecimal(soLuong));
            model.addAttribute("singleProduct", sp);
            model.addAttribute("soLuong", soLuong);
            model.addAttribute("isDirect", true);
        } else {
            // Trường hợp thanh toán cả giỏ hàng
            List<ChiTietGioHang> items = gioHangService.getGioHangByKhachHang(kh);
            if (items.isEmpty()) return "redirect:/cart";

            total = items.stream()
                    .map(item -> item.getSanPham().getGia().multiply(new BigDecimal(item.getSoLuong())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            
            model.addAttribute("items", items);
            model.addAttribute("isDirect", false);
        }

        model.addAttribute("total", total);
        model.addAttribute("customer", kh);
        model.addAttribute("pageTitle", "Thanh toán");
        
        return "customers/checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(@RequestParam String diaChi, 
                                  @RequestParam String sdt, 
                                  @RequestParam String phuongThuc,
                                  @RequestParam(required = false) String maSp,
                                  @RequestParam(required = false, defaultValue = "1") Integer soLuong) {
        KhachHang kh = getCurrentCustomer();
        
        if (maSp != null && !maSp.isEmpty()) {
            // Thanh toán trực tiếp 1 sản phẩm
            hoaDonService.taoDonHangTrucTiep(kh, maSp, soLuong, diaChi, sdt, phuongThuc);
        } else {
            // Thanh toán cả giỏ hàng
            hoaDonService.taoDonHangTuGioHang(kh, diaChi, sdt, phuongThuc);
        }
        
        return "redirect:/cart/success";
    }

    @GetMapping("/success")
    public String orderSuccess(Model model) {
        model.addAttribute("pageTitle", "Đặt hàng thành công");
        return "customers/order-success";
    }

    @GetMapping("/remove/{maSp}")
    public String removeFromCart(@PathVariable String maSp) {
        KhachHang kh = getCurrentCustomer();
        gioHangService.xoaKhoiGio(kh, maSp);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateQuantity(@RequestParam String maSp, @RequestParam int soLuong) {
        KhachHang kh = getCurrentCustomer();
        gioHangService.capNhatSoLuong(kh, maSp, soLuong);
        return "redirect:/cart";
    }
}
