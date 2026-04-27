package com.doan.qllinhkiendientu.models;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "DanhMuc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DanhMuc {
    @Id
    @Column(name = "MaDM")
    private String maDm;

    @Column(name = "TenDM", nullable = false)
    private String tenDm;

    @OneToMany(mappedBy = "danhMuc")
    private List<SanPham> dsSanPham;
}
