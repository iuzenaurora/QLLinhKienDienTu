package com.doan.qllinhkiendientu.models;

import java.io.Serializable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietGioHangId implements Serializable {
    private String khachHang; // Tên trường phải khớp với Entity
    private String sanPham;
}
