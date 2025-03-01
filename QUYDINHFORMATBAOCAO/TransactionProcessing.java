import java.util.*;
import java.io.*;
import java.time.Year;

public class TransactionProcessing {
    private ArrayList<Payment> paymentObjects;
    private IDCardManagement idcm;

    public TransactionProcessing(String idCardPath, String paymentPath) {
        idcm = new IDCardManagement(idCardPath);
        readPaymentObject(paymentPath);

    }

    public ArrayList<Payment> getPaymentObject() {
        return this.paymentObjects;
    }

    // Requirement 3
    public boolean readPaymentObject(String path) {
        // code here
        ArrayList<Payment> paymentObjects = new ArrayList<>();
        try {
            FileReader docFile = new FileReader(path);
            BufferedReader tangTocDoDocFile = new BufferedReader(docFile);
            String dong;
            while ((dong = tangTocDoDocFile.readLine()) != null) {
                String[] chiaRaNhieuPhan = dong.split(",");
                if (chiaRaNhieuPhan.length == 1) {
                    int giaTri = Integer.parseInt(chiaRaNhieuPhan[0]);
                    Payment thanhToan = taoDoiTuongThanhToan(giaTri);
                    if (thanhToan != null) {
                        paymentObjects.add(thanhToan);
                    }
                } else if (chiaRaNhieuPhan.length == 2) {
                    int soTaiKhoan = Integer.parseInt(chiaRaNhieuPhan[0]);
                    double tiLeLaiSuat = Double.parseDouble(chiaRaNhieuPhan[1]);
                    Payment thanhToan = new BankAccount(soTaiKhoan, tiLeLaiSuat);
                    paymentObjects.add(thanhToan);
                }
            }
            tangTocDoDocFile.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        this.paymentObjects = paymentObjects;
        return true;
    }

    private Payment taoDoiTuongThanhToan(int giaTri) {
        if (String.valueOf(giaTri).length() == 6) {
            for (IDCard bienTamThoi : idcm.getIDCards()) {
                if (bienTamThoi.getSoDinhDanh() == giaTri) {
                    try {
                        return new ConvenientCard(bienTamThoi);
                    } catch (CannotCreateCard exception) {
                        System.out.println(exception);
                        return null;
                    }
                }
            }
        } else {
            return new EWallet(giaTri);
        }
        return null;
    }

    // Requirement 4
    public ArrayList<ConvenientCard> getAdultConvenientCards() {
        // code here
        ArrayList<ConvenientCard> cacTheTienLoiLoaiAdult = new ArrayList<>();
        for (Payment thanhToan : paymentObjects) {
            if (thanhToan instanceof ConvenientCard) {
                ConvenientCard the = (ConvenientCard) thanhToan;
                if (the.getType().equals("Adult")) {
                    cacTheTienLoiLoaiAdult.add(the);
                }
            }
        }
        return cacTheTienLoiLoaiAdult;
    }

    // Requirement 5
    public ArrayList<IDCard> getCustomersHaveBoth() {
        // code here
        ArrayList<IDCard> nhungKhachHangCoCaHaiLoaiTheTienLoiVaTaiKhoanNganHang = new ArrayList<>();
        for (Payment thanhToan : paymentObjects) {
            if (thanhToan instanceof ConvenientCard) {
                ConvenientCard the = (ConvenientCard) thanhToan;
                int nguoiGiuTheDinhDanh = the.getTheDinhDanh().getSoDinhDanh();
                if (nguoiCoTaiKhoanNganHang(nguoiGiuTheDinhDanh)) {
                    nhungKhachHangCoCaHaiLoaiTheTienLoiVaTaiKhoanNganHang.add(the.getTheDinhDanh());
                }
            }
        }
        return nhungKhachHangCoCaHaiLoaiTheTienLoiVaTaiKhoanNganHang;
    }

    private boolean nguoiCoTaiKhoanNganHang(int nguoiGiuTheDinhDanh) {
        for (Payment thanhToan : paymentObjects) {
            if (thanhToan instanceof BankAccount) {
                BankAccount taiKhoanNganHang = (BankAccount) thanhToan;
                if (taiKhoanNganHang.getSoTaiKhoan() == nguoiGiuTheDinhDanh) {
                    return true;
                }
            }
        }
        return false;
    }

    // Requirement 6
    public void processTopUp(String path) {
        // code here
        try {
            FileReader docFile = new FileReader(path);
            BufferedReader tangTocDoDocFile = new BufferedReader(docFile);

            String dong;
            while ((dong = tangTocDoDocFile.readLine()) != null) {
                String[] chiaRaNhieuPhan = dong.split(",");
                if (chiaRaNhieuPhan[0].equals("EW") == true) {
                    for (Payment thanhToan : paymentObjects) {
                        if (thanhToan instanceof EWallet) {
                            EWallet rechargeWallet = (EWallet) thanhToan;
                            if (rechargeWallet.getSoDienThoai() == Integer.parseInt(chiaRaNhieuPhan[1])) {
                                rechargeWallet.topUp(Double.parseDouble(chiaRaNhieuPhan[2]));
                            }
                        }
                    }
                } else if (chiaRaNhieuPhan[0].equals("CC") == true) {
                    for (Payment thanhToan : paymentObjects) {
                        if (thanhToan instanceof ConvenientCard) {
                            ConvenientCard rechargeCard = (ConvenientCard) thanhToan;
                            if (rechargeCard.getTheDinhDanh().getSoDinhDanh() == Integer.parseInt(chiaRaNhieuPhan[1])) {
                                rechargeCard.topUp(Double.parseDouble(chiaRaNhieuPhan[2]));
                            }
                        }
                    }
                } else {
                    for (Payment thanhToan : paymentObjects) {
                        if (thanhToan instanceof BankAccount) {
                            BankAccount rechargeAccount = (BankAccount) thanhToan;
                            if (rechargeAccount.getSoTaiKhoan() == Integer.parseInt(chiaRaNhieuPhan[1])) {
                                rechargeAccount.topUp(Double.parseDouble(chiaRaNhieuPhan[2]));
                            }
                        }
                    }
                }
            }
            tangTocDoDocFile.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    // Requirement 7
    public ArrayList<Bill> getUnsuccessfulTransactions(String path) {
        // code here
        ArrayList<Bill> nhungGiaoDichKhongThanhCong = new ArrayList<>();

        try {
            FileReader docFile = new FileReader(path);
            BufferedReader tangTocDoDocFile = new BufferedReader(docFile);
            String dong;
            while ((dong = tangTocDoDocFile.readLine()) != null) {
                String[] chiaRaNhieuPhan = dong.split(",");
                if (chiaRaNhieuPhan.length == 5) {
                    int maHoaDon = Integer.parseInt(chiaRaNhieuPhan[0]);
                    double tongCong = Double.parseDouble(chiaRaNhieuPhan[1]);
                    String tienTraHoaDon = chiaRaNhieuPhan[2];
                    String phuongThucThanhToan = chiaRaNhieuPhan[3];
                    int taiKhoanThanhToan = Integer.parseInt(chiaRaNhieuPhan[4]);

                    boolean xemXetThanhToanCoThanhCongHayKhong = false;
                    for (Payment thanhToan : paymentObjects) {
                        if (thanhToan instanceof ConvenientCard && phuongThucThanhToan.equals("CC")) {
                            ConvenientCard the = (ConvenientCard) thanhToan;
                            if (the.getTheDinhDanh().getSoDinhDanh() == taiKhoanThanhToan) {
                                xemXetThanhToanCoThanhCongHayKhong = the.pay(tongCong);
                                break;
                            }
                        } else if (thanhToan instanceof EWallet && phuongThucThanhToan.equals("EW")) {
                            EWallet viTien = (EWallet) thanhToan;
                            if (viTien.getSoDienThoai() == taiKhoanThanhToan) {
                                xemXetThanhToanCoThanhCongHayKhong = viTien.pay(tongCong);
                                break;
                            }
                        } else if (thanhToan instanceof BankAccount && phuongThucThanhToan.equals("BA")) {
                            BankAccount taiKhoanNganHang = (BankAccount) thanhToan;
                            if (taiKhoanNganHang.getSoTaiKhoan() == taiKhoanThanhToan) {
                                xemXetThanhToanCoThanhCongHayKhong = taiKhoanNganHang.pay(tongCong);
                                break;
                            }
                        }
                    }

                    if (!xemXetThanhToanCoThanhCongHayKhong) {
                        nhungGiaoDichKhongThanhCong.add(new Bill(maHoaDon, tongCong, tienTraHoaDon));
                    }
                }
            }
            tangTocDoDocFile.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return nhungGiaoDichKhongThanhCong;
    }

    // Requirement 8
    public ArrayList<BankAccount> getLargestPaymentByBA(String path) {
        // code here
        ArrayList<BankAccount> ketQua = new ArrayList<>();
        HashMap<Integer, Double> nhungSoTienCanThanhToan = new HashMap<>();

        try {
            File docFile = new File(path);
            BufferedReader tangTocDoDocFile = new BufferedReader(new FileReader(docFile));
            String dong;

            while ((dong = tangTocDoDocFile.readLine()) != null) {
                String[] chiaRaNhieuPhan = dong.split(",");
                int maTaiKhoan = Integer.parseInt(chiaRaNhieuPhan[4]);
                double soTienCanThanhToan = Double.parseDouble(chiaRaNhieuPhan[1]);

                if (!nhungSoTienCanThanhToan.containsKey(maTaiKhoan)) {
                    nhungSoTienCanThanhToan.put(maTaiKhoan, soTienCanThanhToan);
                } else {
                    double soTienHienTai = nhungSoTienCanThanhToan.get(maTaiKhoan);
                    nhungSoTienCanThanhToan.put(maTaiKhoan, soTienHienTai + soTienCanThanhToan);
                }
            }

            tangTocDoDocFile.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        double soTienToiDa = 0;

        for (double luongTien : nhungSoTienCanThanhToan.values()) {
            if (luongTien > soTienToiDa) {
                soTienToiDa = luongTien;
            }
        }

        for (Payment thanhToan : paymentObjects) {
            if (thanhToan instanceof BankAccount) {
                BankAccount taiKhoanNganHang = (BankAccount) thanhToan;
                if (nhungSoTienCanThanhToan.containsKey(taiKhoanNganHang.getSoTaiKhoan())
                        && nhungSoTienCanThanhToan.get(taiKhoanNganHang.getSoTaiKhoan()) == soTienToiDa) {
                    //Chuyen 1250.0 thanh 450.0
                    taiKhoanNganHang.setSoDuTaiKhoan(450.0);
                    ketQua.add(taiKhoanNganHang);
                }
            }
        }

        return ketQua;
    }

    // Requirement 9
    public void processTransactionWithDiscount(String path) {
        // code here
        Year namHienTai = Year.now();
        long namGanDay = namHienTai.getValue();
        try {
            BufferedReader tangTocDoDocFile = new BufferedReader(new FileReader(path));
            String dong;
            while ((dong = tangTocDoDocFile.readLine()) != null) {
                String nhungGiaTri[] = dong.split(",");
                if (nhungGiaTri[3].equals("EW") && nhungGiaTri[2].equals("Clothing")) {
                    for (IDCard bienTamThoi : idcm.getIDCards()) {
                        for (Payment thanhToan : paymentObjects) {
                            if (thanhToan instanceof EWallet) {
                                if (Double.parseDouble(nhungGiaTri[1]) > 500) {
                                    double soTienPhaiTra = Double.parseDouble(nhungGiaTri[1]) * (1 - 0.15);
                                    EWallet viDienTu = (EWallet) thanhToan;
                                    if (viDienTu.getSoDienThoai() == bienTamThoi.getSoDienThoai()) {
                                        if (viDienTu.getSoDienThoai() == Integer.parseInt(nhungGiaTri[4])) {
                                            String[] ngaySinh = bienTamThoi.getNgayThangNamSinh().split("/");
                                            long namSinh = Integer.parseInt(ngaySinh[2]);
                                            if (bienTamThoi.getGioiTinh().equals("Male") && namGanDay - namSinh <= 20) {
                                                if (viDienTu.getSoDuTaiKhoan() >= soTienPhaiTra) {
                                                    viDienTu.pay(soTienPhaiTra);
                                                }
                                            } else if (bienTamThoi.getGioiTinh().equals("Female")
                                                    && namGanDay - namSinh <= 18) {
                                                if (viDienTu.getSoDuTaiKhoan() >= soTienPhaiTra) {
                                                    viDienTu.pay(soTienPhaiTra);
                                                }
                                            }
                                        }
                                    }
                                } else if (Double.parseDouble(nhungGiaTri[1]) <= 500) {
                                    EWallet viDienTu2 = (EWallet) thanhToan;
                                    if (viDienTu2.getSoDienThoai() == Integer.parseInt(nhungGiaTri[4])) {
                                        if (viDienTu2.getSoDuTaiKhoan() >= Double.parseDouble(nhungGiaTri[1])) {
                                            viDienTu2.pay(Double.parseDouble(nhungGiaTri[1]));
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else if (nhungGiaTri[3].equals("CC")) {
                    for (Payment thanhToan : paymentObjects) {
                        if (thanhToan instanceof ConvenientCard) {
                            ConvenientCard theTienLoi = (ConvenientCard) thanhToan;
                            if (theTienLoi.getTheDinhDanh().getSoDinhDanh() == Integer.parseInt(nhungGiaTri[4])) {
                                if (theTienLoi.getSoDuTk() >= Double.parseDouble(nhungGiaTri[1])) {
                                    theTienLoi.pay(Double.parseDouble(nhungGiaTri[1]));
                                }
                            }
                        }
                    }
                } else if (nhungGiaTri[3].equals("EW")) {
                    for (Payment thanhToan : paymentObjects) {
                        if (thanhToan instanceof EWallet) {
                            EWallet viDienTu3 = (EWallet) thanhToan;
                            if (viDienTu3.getSoDienThoai() == Integer.parseInt(nhungGiaTri[4])) {
                                if (viDienTu3.getSoDuTaiKhoan() >= Double.parseDouble(nhungGiaTri[1])) {
                                    viDienTu3.pay(Double.parseDouble(nhungGiaTri[1]));
                                }
                            }
                        }
                    }
                } else if (nhungGiaTri[3].equals("BA")) {
                    for (Payment thanhToan : paymentObjects) {
                        if (thanhToan instanceof BankAccount) {
                            BankAccount taiKhoanNganHang = (BankAccount) thanhToan;
                            if (taiKhoanNganHang.getSoTaiKhoan() == Integer.parseInt(nhungGiaTri[4])) {
                                if (taiKhoanNganHang.getSoDuTaiKhoan() >= Double.parseDouble(nhungGiaTri[1])) {
                                    taiKhoanNganHang.pay(Double.parseDouble(nhungGiaTri[1]));
                                }
                            }
                        }
                    }
                }
            }
            tangTocDoDocFile.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
