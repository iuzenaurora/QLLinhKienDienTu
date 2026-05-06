package com.doan.qllinhkiendientu.controller;

import com.doan.qllinhkiendientu.Services.SanPhamService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HomeController {

    private final SanPhamService sanPhamService;

    public HomeController(SanPhamService sanPhamService){
        this.sanPhamService = sanPhamService;
    }

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("pageTitle", "Trang chủ");
        model.addAttribute("sanphams", sanPhamService.getAll());
        return "index";
    }
}
