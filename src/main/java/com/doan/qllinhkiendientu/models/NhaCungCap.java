package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "NhaCungCap")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NhaCungCap {
    @Id
    private String maNcc;
    private String tenNcc;
    private String diaChi;
    private String sdt;

    @OneToMany(mappedBy = "nhaCungCap")
    private List<SanPham> dsSanPham;
}
