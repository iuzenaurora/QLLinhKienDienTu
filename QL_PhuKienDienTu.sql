CREATE DATABASE QL_PHUKIEN_DIENTU
USE QL_PHUKIEN_DIENTU;

drop database ql_linhkien_dientu

-- 1. BẢNG TÀI KHOẢN (Đã cập nhật hỗ trợ đăng nhập Google/OAuth2)
CREATE TABLE TaiKhoan (
    TenDangNhap VARCHAR(100) PRIMARY KEY, -- Khách hàng dùng Email, Nhân viên dùng Username
    MatKhau VARCHAR(255),                 -- Bỏ 'NOT NULL' vì đăng nhập Google sẽ không có mật khẩu
    Quyen NVARCHAR(20) NOT NULL,          -- ROLE_ADMIN, ROLE_STAFF, ROLE_CUSTOMER
    TrangThai BIT DEFAULT 1,              -- 1: Hoạt động, 0: Bị khóa
    LoaiTaiKhoan VARCHAR(50) DEFAULT 'LOCAL', -- Phân biệt nguồn: 'LOCAL' (Bình thường), 'GOOGLE', 'FACEBOOK'
    ProviderId VARCHAR(255) NULL          -- Lưu mã ID duy nhất do Google trả về
)

-- 2. BẢNG DANH MỤC
CREATE TABLE DanhMuc (
    MaDM CHAR(5) PRIMARY KEY,
    TenDM NVARCHAR(100) NOT NULL
)

-- 3. BẢNG NHÀ CUNG CẤP
CREATE TABLE NhaCungCap (
    MaNCC CHAR(5) PRIMARY KEY,
    TenNCC NVARCHAR(100) NOT NULL,
    DiaChi NVARCHAR(255),
    SoDienThoai VARCHAR(20),
    Email NVARCHAR(100)
)

-- 4. BẢNG SẢN PHẨM
CREATE TABLE SanPham (
    MaSP CHAR(5) PRIMARY KEY,
    TenSP NVARCHAR(150) NOT NULL,
    MaDM CHAR(5) FOREIGN KEY REFERENCES DanhMuc(MaDM),
    MaNCC CHAR(5) FOREIGN KEY REFERENCES NhaCungCap(MaNCC), -- Nhà cung cấp mặc định
    Gia DECIMAL(18,2) NOT NULL,
    SoLuong INT DEFAULT 0,
    MoTa NVARCHAR(255),
    HinhAnh NVARCHAR(255),
	TinhTrang nvarchar(30) default N'Còn hàng'
)

-- 5. BẢNG KHÁCH HÀNG (Đã bỏ cột Mật khẩu, liên kết với bảng TaiKhoan)
CREATE TABLE KhachHang (
    MaKH CHAR(5) PRIMARY KEY,
    HoTen NVARCHAR(100) NOT NULL,
    GioiTinh NVARCHAR(10),
    DiaChi NVARCHAR(255),
    DienThoai VARCHAR(15),
    Email NVARCHAR(100) UNIQUE,
    TenDangNhap VARCHAR(100) FOREIGN KEY REFERENCES TaiKhoan(TenDangNhap)
)

-- 6. BẢNG NHÂN VIÊN (Đã bỏ cột Mật khẩu, liên kết với bảng TaiKhoan)
CREATE TABLE NhanVien (
    MaNV CHAR(5) PRIMARY KEY,
    HoTen NVARCHAR(100) NOT NULL,
    ChucVu NVARCHAR(50),
    DienThoai VARCHAR(15),
    TenDangNhap VARCHAR(100) FOREIGN KEY REFERENCES TaiKhoan(TenDangNhap)
)

-- 7. BẢNG HÓA ĐƠN
CREATE TABLE HoaDon (
    MaHD CHAR(5) PRIMARY KEY,
    MaKH CHAR(5) FOREIGN KEY REFERENCES KhachHang(MaKH),
    MaNV CHAR(5) FOREIGN KEY REFERENCES NhanVien(MaNV), -- Null nếu khách tự đặt online
    NgayLap DATE DEFAULT GETDATE(),
    TongTien DECIMAL(18,2),
    TrangThai NVARCHAR(50) DEFAULT N'Chưa giao',
	PhuongThucThanhToan NVARCHAR(50),
	DiaChiGiaoHang NVARCHAR(255),
	SoDienThoaiGiaoHang VARCHAR(15)
)

-- 8. BẢNG CHI TIẾT HÓA ĐƠN
CREATE TABLE ChiTietHoaDon (
    MaHD CHAR(5) FOREIGN KEY REFERENCES HoaDon(MaHD),
    MaSP CHAR(5) FOREIGN KEY REFERENCES SanPham(MaSP),
    SoLuong INT,
    DonGia DECIMAL(18,2), -- Chỉ lưu đơn giá của 1 SP tại thời điểm mua
    PRIMARY KEY (MaHD, MaSP)
)

-- 9. BẢNG BÌNH LUẬN
CREATE TABLE BinhLuan (
    MaBL CHAR(5) PRIMARY KEY,
    MaSP CHAR(5) FOREIGN KEY REFERENCES SanPham(MaSP),
    MaKH CHAR(5) FOREIGN KEY REFERENCES KhachHang(MaKH),
    NoiDung NVARCHAR(500),
    SoSao INT CHECK(SoSao BETWEEN 1 AND 5),
    NgayDang DATE DEFAULT GETDATE()
)

-- 10. BẢNG PHIẾU NHẬP KHO (Mới - Dùng để quản lý kho)
CREATE TABLE PhieuNhap (
    MaPN CHAR(5) PRIMARY KEY,
    MaNV CHAR(5) FOREIGN KEY REFERENCES NhanVien(MaNV), -- Nhân viên tạo phiếu
    MaNCC CHAR(5) FOREIGN KEY REFERENCES NhaCungCap(MaNCC),
    NgayNhap DATE DEFAULT GETDATE(),
    TongTienNhap DECIMAL(18,2) DEFAULT 0,
    GhiChu NVARCHAR(255)
)

-- 11. BẢNG CHI TIẾT PHIẾU NHẬP
CREATE TABLE ChiTietPhieuNhap (
    MaPN CHAR(5) FOREIGN KEY REFERENCES PhieuNhap(MaPN),
    MaSP CHAR(5) FOREIGN KEY REFERENCES SanPham(MaSP),
    SoLuongNhap INT,
    GiaNhap DECIMAL(18,2),
    PRIMARY KEY (MaPN, MaSP)
)
GO

----------------------------------------------------
-- THÊM DỮ LIỆU MẪU (MOCK DATA)
----------------------------------------------------

-- 1. Thêm Tài khoản
INSERT INTO TaiKhoan (TenDangNhap, MatKhau, Quyen, TrangThai, LoaiTaiKhoan, ProviderId) VALUES
('admin', 'admin123', 'ROLE_ADMIN', 1, 'LOCAL', NULL),
('nhanvien1', '123456', 'ROLE_STAFF', 1, 'LOCAL', NULL),
('nhanvien2', '123456', 'ROLE_STAFF', 1, 'LOCAL', NULL),
('a@gmail.com', '123456', 'ROLE_CUSTOMER', 1, 'LOCAL', NULL),
('b@gmail.com', '123456', 'ROLE_CUSTOMER', 1, 'LOCAL', NULL),
('c@gmail.com', '123456', 'ROLE_CUSTOMER', 1, 'LOCAL', NULL)
-- Dữ liệu mẫu cho tài khoản đăng nhập bằng Google (Không có mật khẩu)
--vd: ('khachhang_google@gmail.com', NULL, 'ROLE_CUSTOMER', 1, 'GOOGLE', '10293847561029384756')

-- 2. Thêm Danh mục
INSERT INTO DanhMuc (MaDM,TenDM) VALUES
('DM01',N'Ốp lưng'),
('DM02',N'Tai nghe'),
('DM03',N'Sạc dự phòng'),
('DM04',N'Cáp sạc'),
('DM05',N'Kính cường lực'),
('DM06',N'Chuột'),
('DM07',N'Bàn Phím')

-- 3. Thêm Nhà cung cấp
INSERT INTO NhaCungCap (MaNCC,TenNCC, DiaChi, SoDienThoai, Email) VALUES
('NCC01',N'Phụ kiện An Phát', N'Hà Nội', '0987654321', 'anphat@gmail.com'),
('NCC02',N'MobileKing', N'TP.HCM', '0912345678', 'mobileking@gmail.com'),
('NCC03',N'TechZone', N'Đà Nẵng', '0905123456', 'techzone@gmail.com')

-- 4. Thêm Sản phẩm
INSERT INTO SanPham (MaSP,TenSP, MaDM, MaNCC, Gia, SoLuong, MoTa, HinhAnh) VALUES
('SP01',N'Ốp lưng iPhone 14 trong suốt', 'DM01','NCC01', 120000, 50, N'Ốp lưng nhựa dẻo chống sốc', N'OpLungiPhone14TrongSuot.jpg'),
('SP02',N'Ốp lưng Samsung S23 chống va đập','DM01','NCC01', 250000, 30, N'Ốp lưng silicon cao cấp', N'OpLungSamSungS23.jpg'),
('SP05',N'Tai nghe Bluetooth Baseus','DM02','NCC01', 350000, 60, N'Tai nghe không dây pin 8 giờ', N'TaiNgheBluetoothBaseus.jpg'),
('SP09',N'Sạc dự phòng Romoss 20000mAh','DM03','NCC01', 590000, 40, N'Hỗ trợ sạc nhanh PD 18W, 3 cổng ra', N'SacDuPhongRomoss.jpg')

-- 5. Thêm Khách hàng
INSERT INTO KhachHang (MaKH, HoTen, GioiTinh, DiaChi, DienThoai, Email, TenDangNhap) VALUES
('KH01',N'Nguyễn Văn A', N'Nam', N'Hà Nội', '0912345678', 'a@gmail.com', 'a@gmail.com'),
('KH02',N'Trần Thị B', N'Nữ', N'TP.HCM', '0987654321', 'b@gmail.com', 'b@gmail.com'),
('KH03',N'Lê Văn C', N'Nam', N'Đà Nẵng', '0909123456', 'c@gmail.com', 'c@gmail.com'),
('KH04',N'Khách Hàng Google', N'Nam', NULL, NULL, 'khachhang_google@gmail.com', 'khachhang_google@gmail.com')

-- 6. Thêm Nhân viên
INSERT INTO NhanVien (MaNV, HoTen, ChucVu, DienThoai, TenDangNhap) VALUES
('NV01',N'Lê Văn Quản', N'Quản lý', '0909123456', 'admin'),
('NV02',N'Phạm Thị Hương', N'Nhân viên bán hàng', '0977888999', 'nhanvien1'),
('NV03',N'Nguyễn Quốc Dũng', N'Nhân viên kho', '0911222333', 'nhanvien2')

-- 7. Thêm Hóa đơn
INSERT INTO HoaDon (MaHD, MaKH, MaNV, NgayLap, TongTien, TrangThai, PhuongThucThanhToan, DiaChiGiaoHang) VALUES
('HD001', 'KH01', 'NV01', '2025-10-10', 549000, N'Đã giao', N'Tiền mặt', N'Hà Nội'),
('HD002', 'KH02', 'NV02', '2025-10-11', 350000, N'Chưa giao', N'Chuyển khoản', N'TP.HCM')

-- 8. Thêm Chi tiết hóa đơn (Đã sửa giá trị DonGia cho chuẩn)
INSERT INTO ChiTietHoaDon (MaHD, MaSP, SoLuong, DonGia) VALUES
-- HD001 (Mua 2 SP01 giá 120k và 1 SP014 (nếu có) nhưng ở đây mock data SP01 thôi để làm mẫu)
('HD001', 'SP01', 2, 120000), 
('HD002', 'SP05', 1, 350000)

-- 9. Thêm Phiếu nhập (Dữ liệu mẫu cho Quản lý kho)
INSERT INTO PhieuNhap (MaPN, MaNV, MaNCC, NgayNhap, TongTienNhap, GhiChu) VALUES
('PN001', 'NV03', 'NCC01', '2025-09-01', 5000000, N'Nhập hàng tháng 9')

INSERT INTO ChiTietPhieuNhap (MaPN, MaSP, SoLuongNhap, GiaNhap) VALUES
('PN001', 'SP01', 50, 60000), -- Giá nhập thường rẻ hơn giá bán
('PN001', 'SP02', 30, 150000)