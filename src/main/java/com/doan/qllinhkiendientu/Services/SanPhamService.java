package com.doan.qllinhkiendientu.Services;

import com.doan.qllinhkiendientu.models.SanPham;
import com.doan.qllinhkiendientu.repositories.SanPhamRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanPhamService {

    private final SanPhamRepository sanPhamRepository;

    public SanPhamService(SanPhamRepository sanPhamRepository){
        this.sanPhamRepository = sanPhamRepository;
    }

    public List<SanPham> getAll(){
        return sanPhamRepository.findAll();
    }
}
