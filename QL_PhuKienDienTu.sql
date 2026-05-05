CREATE DATABASE QL_PHUKIEN_DIENTU
GO
USE QL_PHUKIEN_DIENTU;
GO
-- ==========================================
-- PHẦN 1: TÀI KHOẢN & PHÂN QUYỀN
-- ==========================================

-- 1. BẢNG TÀI KHOẢN (Hỗ trợ đăng nhập Local & Google OAuth2)
CREATE TABLE TaiKhoan (
    TenDangNhap VARCHAR(100) PRIMARY KEY, 
    MatKhau VARCHAR(255),                 
    Quyen NVARCHAR(20) NOT NULL,          -- ROLE_ADMIN, ROLE_STAFF, ROLE_CUSTOMER
    TrangThai BIT DEFAULT 1,              -- 1: Hoạt động, 0: Bị khóa
    LoaiTaiKhoan VARCHAR(50) DEFAULT 'LOCAL', -- 'LOCAL' hoặc 'GOOGLE'
    ProviderId VARCHAR(255) NULL          
)

-- 2. BẢNG TOKEN QUÊN MẬT KHẨU [MỚI BỔ SUNG]
CREATE TABLE TokenQuenMatKhau (
    MaToken INT PRIMARY KEY IDENTITY(1,1),
    TenDangNhap VARCHAR(100) FOREIGN KEY REFERENCES TaiKhoan(TenDangNhap),
    ChuoiToken VARCHAR(255) NOT NULL UNIQUE, -- Chuỗi UUID sinh ra ngẫu nhiên
    NgayTao DATETIME DEFAULT GETDATE(),
    NgayHetHan DATETIME NOT NULL             -- Thường set +15 phút từ lúc tạo
)

-- 3. BẢNG KHÁCH HÀNG 
CREATE TABLE KhachHang (
    MaKH CHAR(5) PRIMARY KEY,
    HoTen NVARCHAR(100) NOT NULL,
    GioiTinh NVARCHAR(10),
    DiaChi NVARCHAR(255),
    DienThoai VARCHAR(15),
    Email NVARCHAR(100) UNIQUE,
    TenDangNhap VARCHAR(100) FOREIGN KEY REFERENCES TaiKhoan(TenDangNhap)
)

-- 4. BẢNG NHÂN VIÊN 
CREATE TABLE NhanVien (
    MaNV CHAR(5) PRIMARY KEY,
    HoTen NVARCHAR(100) NOT NULL,
    ChucVu NVARCHAR(50),
    DienThoai VARCHAR(15),
    TenDangNhap VARCHAR(100) FOREIGN KEY REFERENCES TaiKhoan(TenDangNhap)
)

-- ==========================================
-- PHẦN 2: DANH MỤC & SẢN PHẨM
-- ==========================================

-- 5. BẢNG DANH MỤC
CREATE TABLE DanhMuc (
    MaDM CHAR(5) PRIMARY KEY,
    TenDM NVARCHAR(100) NOT NULL
)

-- 6. BẢNG NHÀ CUNG CẤP
CREATE TABLE NhaCungCap (
    MaNCC CHAR(5) PRIMARY KEY,
    TenNCC NVARCHAR(100) NOT NULL,
    DiaChi NVARCHAR(255),
    SoDienThoai VARCHAR(20),
    Email NVARCHAR(100)
)

-- 7. BẢNG SẢN PHẨM
CREATE TABLE SanPham (
    MaSP CHAR(5) PRIMARY KEY,
    TenSP NVARCHAR(150) NOT NULL,
    MaDM CHAR(5) FOREIGN KEY REFERENCES DanhMuc(MaDM),
    MaNCC CHAR(5) FOREIGN KEY REFERENCES NhaCungCap(MaNCC), 
    Gia DECIMAL(18,2) NOT NULL,
    SoLuong INT DEFAULT 0,
    MoTa NVARCHAR(255),
    HinhAnh NVARCHAR(255),
	TinhTrang NVARCHAR(30) DEFAULT N'Còn hàng'
)

-- 8. BẢNG BÌNH LUẬN (Thêm cột Kiểm duyệt)
CREATE TABLE BinhLuan (
    MaBL CHAR(5) PRIMARY KEY,
    MaSP CHAR(5) FOREIGN KEY REFERENCES SanPham(MaSP),
    MaKH CHAR(5) FOREIGN KEY REFERENCES KhachHang(MaKH),
    NoiDung NVARCHAR(500),
    SoSao INT CHECK(SoSao BETWEEN 1 AND 5),
    NgayDang DATETIME DEFAULT GETDATE(),
    TrangThai BIT DEFAULT 1 -- [MỚI] 1: Đang hiển thị, 0: Bị Admin ẩn/xóa
)

-- ==========================================
-- PHẦN 3: GIAO DỊCH (HÓA ĐƠN & KHUYẾN MÃI)
-- ==========================================

-- 9. BẢNG KHUYẾN MÃI / VOUCHER [MỚI BỔ SUNG]
CREATE TABLE KhuyenMai (
    MaKM CHAR(10) PRIMARY KEY, -- VD: 'TET2026', 'GIAM10K'
    TenKM NVARCHAR(100),
    PhanTramGiam INT DEFAULT 0,        -- Giảm theo % (VD: 10%)
    TienGiamToiDa DECIMAL(18,2) DEFAULT 0, -- Số tiền giảm tối đa nếu dùng %
    DonToiThieu DECIMAL(18,2) DEFAULT 0,   -- Đơn hàng tối thiểu để áp dụng
    SoLuong INT DEFAULT 100,           -- Số lượng mã có thể nhập
    NgayBatDau DATETIME,
    NgayKetThuc DATETIME,
    TrangThai BIT DEFAULT 1            -- 1: Đang kích hoạt, 0: Đã tắt/Hết hạn
)

-- 10. BẢNG HÓA ĐƠN (Gộp NgayThanhToan và Cột Khuyến mãi)
CREATE TABLE HoaDon (
    MaHD CHAR(5) PRIMARY KEY,
    MaKH CHAR(5) FOREIGN KEY REFERENCES KhachHang(MaKH),
    MaNV CHAR(5) FOREIGN KEY REFERENCES NhanVien(MaNV), -- Null nếu khách tự mua online
    MaKM CHAR(10) FOREIGN KEY REFERENCES KhuyenMai(MaKM), -- [MỚI] Mã Voucher đã áp dụng
    TienGiamGia DECIMAL(18,2) DEFAULT 0,                  -- [MỚI] Số tiền được giảm
    NgayLap DATETIME DEFAULT GETDATE(),
    NgayThanhToan DATETIME NULL,                          -- Dùng khi quẹt thẻ VNPay xong
    TongTien DECIMAL(18,2),                               -- Tiền cuối cùng khách phải trả
    TrangThai NVARCHAR(50) DEFAULT N'Chờ thanh toán',
	PhuongThucThanhToan NVARCHAR(50),                     -- 'COD' hoặc 'VNPAY'
	DiaChiGiaoHang NVARCHAR(255),
	SoDienThoaiGiaoHang VARCHAR(15)
)

-- 11. BẢNG CHI TIẾT HÓA ĐƠN
CREATE TABLE ChiTietHoaDon (
    MaHD CHAR(5) FOREIGN KEY REFERENCES HoaDon(MaHD),
    MaSP CHAR(5) FOREIGN KEY REFERENCES SanPham(MaSP),
    SoLuong INT,
    DonGia DECIMAL(18,2), 
    PRIMARY KEY (MaHD, MaSP)
)

-- 12. BẢNG CHI TIẾT GIỎ HÀNG (Dùng khi muốn lưu giỏ hàng vào DB thay vì Session)
CREATE TABLE ChiTietGioHang (
	MaKH CHAR(5) FOREIGN KEY REFERENCES KhachHang(MaKH),
	MaSP CHAR(5) FOREIGN KEY REFERENCES SanPham(MaSP),
	SoLuong INT,
    NgayThem DATETIME DEFAULT GETDATE(), -- [MỚI] Để quản lý hàng tồn trong giỏ lâu ngày
	PRIMARY KEY (MaKH, MaSP)
)

-- ==========================================
-- PHẦN 4: QUẢN LÝ KHO & HỆ THỐNG
-- ==========================================

-- 13. BẢNG PHIẾU NHẬP KHO
CREATE TABLE PhieuNhap (
    MaPN CHAR(5) PRIMARY KEY,
    MaNV CHAR(5) FOREIGN KEY REFERENCES NhanVien(MaNV), 
    MaNCC CHAR(5) FOREIGN KEY REFERENCES NhaCungCap(MaNCC),
    NgayNhap DATETIME DEFAULT GETDATE(),
    TongTienNhap DECIMAL(18,2) DEFAULT 0,
    GhiChu NVARCHAR(255)
)

-- 14. BẢNG CHI TIẾT PHIẾU NHẬP
CREATE TABLE ChiTietPhieuNhap (
    MaPN CHAR(5) FOREIGN KEY REFERENCES PhieuNhap(MaPN),
    MaSP CHAR(5) FOREIGN KEY REFERENCES SanPham(MaSP),
    SoLuongNhap INT,
    GiaNhap DECIMAL(18,2),
    PRIMARY KEY (MaPN, MaSP)
)

-- 15. BẢNG TIN NHẮN (LIVE CHAT WEBSOCKETS)
CREATE TABLE TinNhan (
	MaTN INT PRIMARY KEY IDENTITY(1,1),
	NguoiGui VARCHAR(100), -- Dùng TenDangNhap (Email KH hoặc Username Admin)
	NguoiNhan VARCHAR(100),
	NoiDung NVARCHAR(MAX),
	ThoiGian DATETIME DEFAULT GETDATE(),
	DaDoc BIT DEFAULT 0
)

-- 16. BẢNG THÔNG BÁO HỆ THỐNG
CREATE TABLE ThongBao (
	MaTB INT PRIMARY KEY IDENTITY(1,1),
	TieuDe NVARCHAR(255),
	NoiDung NVARCHAR(MAX),
	NgayTao DATETIME DEFAULT GETDATE(),
	TrangThai BIT DEFAULT 0 -- 0: Chưa xem, 1: Đã xem
)
GO

