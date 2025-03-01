public class IDCard {
      // code here
      private int soDinhDanh;
      private String hoTen;
      private String gioiTinh;
      private String ngayThangNamSinh;
      private String diaChi;
      private int soDienThoai;

      public IDCard(int soDinhDanh, String hoTen, String gioiTinh, String ngayThangNamSinh, String diaChi,
                  int soDienThoai) {
            super();
            this.soDinhDanh = soDinhDanh;
            this.hoTen = hoTen;
            this.gioiTinh = gioiTinh;
            this.ngayThangNamSinh = ngayThangNamSinh;
            this.diaChi = diaChi;
            this.soDienThoai = soDienThoai;
      }

      public int getSoDinhDanh() {
            return this.soDinhDanh;
      }

      public void setSoDinhDanh(int soDinhDanh) {
            this.soDinhDanh = soDinhDanh;
      }

      public String getHoTen() {
            return this.hoTen;
      }

      public void setHoTen(String hoTen) {
            this.hoTen = hoTen;
      }

      public String getGioiTinh() {
            return this.gioiTinh;
      }

      public void setGioiTinh(String gioiTinh) {
            this.gioiTinh = gioiTinh;
      }

      public String getNgayThangNamSinh() {
            return this.ngayThangNamSinh;
      }

      public void setNgayThangNamSinh(String ngayThangNamSinh) {
            this.ngayThangNamSinh = ngayThangNamSinh;
      }

      public String getDiaChi() {
            return this.diaChi;
      }

      public void setDiaChi(String diaChi) {
            this.diaChi = diaChi;
      }

      public int getSoDienThoai() {
            return this.soDienThoai;
      }

      public void setSoDienThoai(int soDienThoai) {
            this.soDienThoai = soDienThoai;
      }

      public String toString() {
            return this.soDinhDanh + "," + this.hoTen + "," + this.gioiTinh + "," + this.ngayThangNamSinh + ","
                        + this.diaChi + "," + this.soDienThoai;

      }
}
