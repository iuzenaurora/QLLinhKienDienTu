package com.doan.qllinhkiendientu.models;

import java.io.Serializable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietHoaDonId implements Serializable {
    private String hoaDon; // Phải trùng tên với trường trong Entity
    private String sanPham;
}
