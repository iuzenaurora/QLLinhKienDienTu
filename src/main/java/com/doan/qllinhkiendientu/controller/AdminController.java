package com.doan.qllinhkiendientu.controller;

import com.doan.qllinhkiendientu.models.*;
import com.doan.qllinhkiendientu.Repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private com.doan.qllinhkiendientu.repositories.DanhMucRepository danhMucRepository;

    @Autowired
    private com.doan.qllinhkiendientu.repositories.NhaCungCapRepository nhaCungCapRepository;

    @Autowired
    private com.doan.qllinhkiendientu.repositories.HoaDonRepository hoaDonRepository;

    @Autowired
    private com.doan.qllinhkiendientu.repositories.ChiTietHoaDonRepository chiTietHoaDonRepository;

    // ==========================
    // DANH SÁCH SẢN PHẨM
    // ==========================
    @GetMapping("")
    public String index(Model model) {

        List<SanPham> dsSanPham = sanPhamRepository.findAll();

        model.addAttribute("dsSanPham", dsSanPham);

        return "admin/index";
    }

    // ==========================
    // FORM THÊM SẢN PHẨM
    // ==========================
    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute("sanPham", new SanPham());

        model.addAttribute("dsDanhMuc",
                danhMucRepository.findAll());

        model.addAttribute("dsNhaCungCap",
                nhaCungCapRepository.findAll());

        return "admin/add";
    }

    // ==========================
    // THÊM SẢN PHẨM
    // ==========================
    @PostMapping("/create")
    public String create(
            @ModelAttribute SanPham sanPham,
            @RequestParam("HinhAnhUpload") MultipartFile file
    ) throws IOException {

        // upload hình
        if (!file.isEmpty()) {

            String uploadDir =
                    "src/main/resources/static/images/";

            String fileName =
                    file.getOriginalFilename();

            file.transferTo(
                    Paths.get(uploadDir + fileName)
            );

            sanPham.setHinhAnh(fileName);
        }

        sanPhamRepository.save(sanPham);

        return "redirect:/admin";
    }

    // ==========================
    // FORM SỬA
    // ==========================
    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable String id,
            Model model
    ) {

        SanPham sanPham =
                sanPhamRepository.findById(id)
                        .orElse(null);

        if (sanPham == null) {
            return "redirect:/admin";
        }

        model.addAttribute("sanPham", sanPham);

        model.addAttribute("dsDanhMuc",
                danhMucRepository.findAll());

        model.addAttribute("dsNhaCungCap",
                nhaCungCapRepository.findAll());

        return "admin/edit";
    }

    // ==========================
    // SỬA SẢN PHẨM
    // ==========================
    @PostMapping("/edit")
    public String edit(
            @ModelAttribute SanPham sanPham,
            @RequestParam("HinhAnhUpload") MultipartFile file
    ) throws IOException {

        // upload hình mới
        if (!file.isEmpty()) {

            String uploadDir =
                    "src/main/resources/static/images/";

            String fileName =
                    file.getOriginalFilename();

            file.transferTo(
                    Paths.get(uploadDir + fileName)
            );

            sanPham.setHinhAnh(fileName);
        }

        sanPhamRepository.save(sanPham);

        return "redirect:/admin";
    }

    // ==========================
    // XÓA SẢN PHẨM
    // ==========================
    @GetMapping("/delete/{id}")
    public String delete(
            @PathVariable String id
    ) {

        sanPhamRepository.deleteById(id);

        return "redirect:/admin";
    }

    // ==========================
    // DANH SÁCH ĐƠN HÀNG
    // ==========================
    @GetMapping("/donhang")
    public String danhSachDonHang(Model model) {

        List<HoaDon> dsHoaDon =
                hoaDonRepository.findAll();

        model.addAttribute("dsHoaDon", dsHoaDon);

        return "admin/donhang";
    }

    // ==========================
    // FORM CẬP NHẬT ĐƠN HÀNG
    // ==========================
    @GetMapping("/donhang/{maHD}")
    public String capNhatDonHang(
            @PathVariable String maHD,
            Model model
    ) {

        HoaDon hoaDon =
                hoaDonRepository.findById(maHD)
                        .orElse(null);

        if (hoaDon == null) {
            return "redirect:/admin/donhang";
        }

        List<ChiTietHoaDon> chiTiet =
                chiTietHoaDonRepository.findAll();

        model.addAttribute("hoaDon", hoaDon);

        model.addAttribute("chiTiet", chiTiet);

        return "admin/capnhatdonhang";
    }

    // ==========================
    // UPDATE TRẠNG THÁI
    // ==========================
    @PostMapping("/donhang/update")
    public String updateTrangThai(
            @RequestParam String maHD,
            @RequestParam String trangThai
    ) {

        HoaDon hoaDon =
                hoaDonRepository.findById(maHD)
                        .orElse(null);

        if (hoaDon != null) {

            hoaDon.setTrangThai(trangThai);

            hoaDonRepository.save(hoaDon);
        }

        return "redirect:/admin/donhang";
    }

    // ==========================
    // THỐNG KÊ DOANH THU
    // ==========================
    @GetMapping("/thongke")
    public String thongKeDoanhThu(Model model) {

        List<HoaDon> dsHoaDon =
                hoaDonRepository.findAll();

        java.math.BigDecimal tongDoanhThu = java.math.BigDecimal.ZERO;

        for (HoaDon hd : dsHoaDon) {

            if ("Đã giao".equals(hd.getTrangThai())) {

                tongDoanhThu = tongDoanhThu.add(hd.getTongTien());
            }
        }

        model.addAttribute(
                "tongDoanhThu",
                tongDoanhThu
        );

        model.addAttribute(
                "dsHoaDon",
                dsHoaDon
        );

        return "admin/thongke";
    }

    // ==========================
    // QUẢN LÝ HÓA ĐƠN
    // ==========================
    @GetMapping("/quanlyhoadon")
    public String quanLyHoaDon(Model model) {

        List<HoaDon> dsHoaDon =
                hoaDonRepository.findAll();

        int tongSoDon = dsHoaDon.size();

        int soDonChuaGiao = 0;

        for (HoaDon hd : dsHoaDon) {

            if ("Chưa giao".equals(hd.getTrangThai())) {

                soDonChuaGiao++;
            }
        }

        model.addAttribute(
                "tongSoDon",
                tongSoDon
        );

        model.addAttribute(
                "soDonChuaGiao",
                soDonChuaGiao
        );

        return "admin/quanlyhoadon";
    }
}