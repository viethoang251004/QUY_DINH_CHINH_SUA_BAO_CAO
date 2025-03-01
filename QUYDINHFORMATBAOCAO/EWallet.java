public class EWallet implements Payment, Transfer {
	// code here
	private int soDienThoai;
	private double soDuTaiKhoan;

	public EWallet(int soDienThoai) {
		this.soDienThoai = soDienThoai;
		this.soDuTaiKhoan = 0;
	}

	public boolean pay(double amount) {
		double soTienCanThanhToan = amount;
		if (soTienCanThanhToan <= this.soDuTaiKhoan) {
			this.soDuTaiKhoan = this.soDuTaiKhoan - soTienCanThanhToan;
			return true;
		} else {
			return false;
		}
	}

	public boolean transfer(double amount, Transfer to) {
		double soTienChuyen = amount + Transfer.transferFee * amount;
		if (soTienChuyen <= this.soDuTaiKhoan) {
			this.soDuTaiKhoan = this.soDuTaiKhoan - soTienChuyen;
			if (to instanceof EWallet) {
				((EWallet) to).topUp(amount);
			} else if (to instanceof BankAccount) {
				((BankAccount) to).topUp(amount);
			}
			return true;
		}
		return false;
	}

	public double checkBalance() {
		return this.soDuTaiKhoan;
	}

	public void topUp(double amount) {
		this.soDuTaiKhoan = this.soDuTaiKhoan + amount;
	}

	public String toString() {
		return this.soDienThoai + "," + this.soDuTaiKhoan;
	}

	public int getSoDienThoai() {
		return this.soDienThoai;
	}

	public void setSoDienThoai(int soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public double getSoDuTaiKhoan() {
		return this.soDuTaiKhoan;
	}

	public void setSoDuTaiKhoan(double soDuTaiKhoan) {
		this.soDuTaiKhoan = soDuTaiKhoan;
	}

	public void accountBalanceAfterWithDraw(double amount) {
        this.soDuTaiKhoan = this.soDuTaiKhoan - amount;
    }
}
