package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "KhuyenMai")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KhuyenMai {
    @Id
    @Column(name = "MaKM", columnDefinition = "CHAR(10)")
    private String maKm;

    @Column(name = "TenKM", columnDefinition = "NVARCHAR(100)")
    private String tenKm;

    @Column(name = "PhanTramGiam")
    private Integer phanTramGiam = 0;

    @Column(name = "TienGiamToiDa", columnDefinition = "DECIMAL(18,2)")
    private BigDecimal tienGiamToiDa = BigDecimal.ZERO;

    @Column(name = "DonToiThieu", columnDefinition = "DECIMAL(18,2)")
    private BigDecimal donToiThieu = BigDecimal.ZERO;

    @Column(name = "SoLuong")
    private Integer soLuong = 100;

    @Column(name = "NgayBatDau")
    private LocalDateTime ngayBatDau;

    @Column(name = "NgayKetThuc")
    private LocalDateTime ngayKetThuc;

    @Column(name = "TrangThai")
    private boolean trangThai = true;
}
