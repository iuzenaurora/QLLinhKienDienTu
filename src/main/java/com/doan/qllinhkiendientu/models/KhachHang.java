package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "KhachHang")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KhachHang {
    @Id
    private String maKh;
    private String tenKh;
    private String diaChi;
    private String sdt;
    private String email;

    @OneToMany(mappedBy = "khachHang")
    private List<HoaDon> dsHoaDon;

    @OneToMany(mappedBy = "khachHang")
    private List<BinhLuan> dsBinhLuan;
}
