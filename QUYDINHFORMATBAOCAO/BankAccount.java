public class BankAccount implements Payment, Transfer {
    // code here
    private int soTaiKhoan;
    private double tiLeLaiSuat;
    private double soDuTaiKhoan;

    public BankAccount(int soTaiKhoan, double tiLeLaiSuat) {
        this.soTaiKhoan = soTaiKhoan;
        this.tiLeLaiSuat = tiLeLaiSuat;
        this.soDuTaiKhoan = 50;
    }

    public boolean pay(double amount) {
        if (amount + 50 <= this.soDuTaiKhoan) {
            this.soDuTaiKhoan = this.soDuTaiKhoan - amount;
            return true;
        }
        return false;
    }

    public boolean transfer(double amount, Transfer to) {
        double soTienChuyen = amount + Transfer.transferFee * amount;
        if (soTienChuyen <= this.soDuTaiKhoan - 50) {
            this.soDuTaiKhoan = this.soDuTaiKhoan - soTienChuyen;
            if (to instanceof BankAccount) {
                ((BankAccount) to).topUp(amount);
            } else if (to instanceof EWallet) {
                ((EWallet) to).topUp(amount);
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
        return this.soTaiKhoan + "," + this.tiLeLaiSuat + "," + this.soDuTaiKhoan;
    }

    public int getSoTaiKhoan() {
        return this.soTaiKhoan;
    }

    public void setSoTaiKhoan(int soTaiKhoan) {
        this.soTaiKhoan = soTaiKhoan;
    }

    public double getTiLeLaiSuat() {
        return this.tiLeLaiSuat;
    }

    public void setTiLeLaiSuat(double tiLeLaiSuat) {
        this.tiLeLaiSuat = tiLeLaiSuat;
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
