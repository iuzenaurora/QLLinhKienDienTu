package com.doan.qllinhkiendientu.controller;

import com.doan.qllinhkiendientu.Services.SanPhamService;
import com.doan.qllinhkiendientu.models.SanPham;
import com.doan.qllinhkiendientu.repositories.DanhMucRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    private final SanPhamService sanPhamService;
    private final DanhMucRepository danhMucRepository;

    public HomeController(SanPhamService sanPhamService, DanhMucRepository danhMucRepository){
        this.sanPhamService = sanPhamService;
        this.danhMucRepository = danhMucRepository;
    }

    private void addCommonAttributes(Model model) {
        model.addAttribute("danhmucs", danhMucRepository.findAll());
    }

    @GetMapping("/")
    public String home(@RequestParam(required = false) String keyword,
                       @RequestParam(required = false) String category,
                       Model model){
        List<SanPham> sanphams;
        
        if (keyword != null && !keyword.isEmpty()) {
            sanphams = sanPhamService.searchByName(keyword);
            model.addAttribute("pageTitle", "Kết quả tìm kiếm: " + keyword);
        } else if (category != null && !category.isEmpty()) {
            sanphams = sanPhamService.filterByCategory(category);
            model.addAttribute("pageTitle", "Danh mục: " + category);
        } else {
            sanphams = sanPhamService.getAll();
            model.addAttribute("pageTitle", "Trang chủ");
        }

        model.addAttribute("sanphams", sanphams);
        addCommonAttributes(model);
        return "customers/index";
    }

    @GetMapping("/product/{id}")
    public String detail(@PathVariable String id, Model model){
        SanPham sp = sanPhamService.getById(id);
        model.addAttribute("sp", sp);
        model.addAttribute("pageTitle", sp.getTenSp());
        addCommonAttributes(model);
        return "customers/product-detail";
    }
}
