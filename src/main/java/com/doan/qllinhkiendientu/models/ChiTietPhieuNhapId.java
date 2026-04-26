package com.doan.qllinhkiendientu.models;

import java.io.Serializable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietPhieuNhapId implements Serializable {
    private String phieuNhap;
    private String sanPham;
}
