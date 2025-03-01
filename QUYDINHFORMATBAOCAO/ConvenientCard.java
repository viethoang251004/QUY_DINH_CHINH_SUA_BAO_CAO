import java.time.Year;

public class ConvenientCard implements Payment {
	// code here
	private String type;
	private IDCard theDinhDanh;
	private double soDuTaiKhoan;

	private long tinhTuoiHienTai(String ngayThangNamSinh) {
		long tuoiTac = 0;
		String[] chiaranhieuPhan = ngayThangNamSinh.split("/");
		long namSinh = Integer.parseInt(chiaranhieuPhan[2]);
		long namHienTai = Year.now().getValue();
		tuoiTac = namHienTai - namSinh;
		return tuoiTac;
	}

	public ConvenientCard(IDCard theDinhDanh) throws CannotCreateCard {
		super();
		this.theDinhDanh = theDinhDanh;
		this.soDuTaiKhoan = 100;

		long tuoitac = tinhTuoiHienTai(theDinhDanh.getNgayThangNamSinh());

		if (tuoitac < 12) {
			throw new CannotCreateCard("Not enough age");
		} else if (tuoitac <= 18) {
			this.type = "Student";
		} else {
			this.type = "Adult";
		}
	}

	public boolean pay(double amount) {
		double soTienCanThanhToan = amount;

		if (type.equals("Student")) {
			soTienCanThanhToan = amount;
		}

		if (type.equals("Adult")) {
			soTienCanThanhToan = soTienCanThanhToan + (0.01 * amount);
		}

		if (soTienCanThanhToan <= this.soDuTaiKhoan) {
			this.soDuTaiKhoan = this.soDuTaiKhoan - soTienCanThanhToan;
			return true;
		} else {
			return false;
		}
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public IDCard getTheDinhDanh() {
		return this.theDinhDanh;
	}

	public void setTheDinhDanh(IDCard theDinhDanh) {
		this.theDinhDanh = theDinhDanh;
	}

	public double getSoDuTk() {
		return this.soDuTaiKhoan;
	}

	public void setSoDuTK(double soDuTk) {
		this.soDuTaiKhoan = soDuTk;
	}

	public double checkBalance() {
		return this.soDuTaiKhoan;
	}

	public void topUp(double amount) {
		this.soDuTaiKhoan = this.soDuTaiKhoan + amount;
	}

	public String toString() {
		return this.theDinhDanh + "," + this.type + "," + this.soDuTaiKhoan;
	}

	public void accountBalanceAfterWithDraw(double amount) {
		this.soDuTaiKhoan = this.soDuTaiKhoan - amount;
	}
}
