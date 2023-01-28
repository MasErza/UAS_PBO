// proses import liblary
import java.sql.*;
import java.util.Scanner;

//membuat clas main utama untuk koneksi ke db
public class Uas {
    // dipergunakan untuk melakukan koneksi mysql dengan vscode serta program
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/db_reza";
    static final String USER = "root";
    static final String PASS = "";

    // membuat main untuk program run
    public static void main(String[] args) {
        // Kode ini digunakan untuk menghubungkan data ke sebuah database menggunakan
        // JDBC di Java
        // Kode Connection untuk koneksi dan Statement dipergunakan sebagai eksekutor
        Connection koneksi = null;
        Statement eksekusi = null;
        try {
            Class.forName(JDBC_DRIVER);
            koneksi = DriverManager.getConnection(DB_URL, USER, PASS);
            eksekusi = koneksi.createStatement();
            // membuat menu
            Scanner input = new Scanner(System.in);
            int pilihan;
            do {
                System.out.println("-----------------------------------------");
                System.out.println("Aplikasi CRUD sederhana data product:");
                System.out.println("-----------------------------------------");
                System.out.println("1. Tampilkan data category");
                System.out.println("2. Tampilkan data Product");
                System.out.println("3. Input data Product");
                System.out.println("4. Edit data Product");
                System.out.println("5. Hapus data");
                System.out.println("6. Keluar");
                System.out.print("Masukkan pilihan: ");
                pilihan = input.nextInt();
                System.out.println("-----------------------------------------");

                switch (pilihan) {
                    case 1:
                        showAllcategory(eksekusi);
                        break;
                    case 2:
                        showAll(eksekusi);
                        break;
                    case 3:
                        create(eksekusi, input);
                        break;
                    case 4:
                        edit(eksekusi, input);
                        break;
                    case 5:
                        delete(eksekusi, input);
                        break;
                    case 6:
                        System.out.println("Terima kasih telah menggunakan aplikasi ini.");
                        break;
                    default:
                        System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                        break;
                }
            } while (pilihan != 6);

            eksekusi.close();
            koneksi.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (eksekusi != null)
                    eksekusi.close();
            } catch (SQLException se2) {
            }
            try {
                if (koneksi != null)
                    koneksi.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }

        }

    }
    private static void showAllcategory(Statement eksekusi) throws SQLException {
        String sql = "SELECT * FROM category";
        ResultSet rs = eksekusi.executeQuery(sql);
        System.out.println("-----------------------------------------");
        System.out.println("Menampilkan Data category :");
        System.out.println("-----------------------------------------");
        while (rs.next()) {
            // ngambil data di database kumudian di tampilkan
            int id = rs.getInt("id");
            String name = rs.getString("name");
            System.out.println("Id : " + id + "| Name : " + name);
        }
        System.out.println();
    }

    private static void showAll(Statement eksekusi) throws SQLException {
        String sql = "SELECT * FROM product";
        ResultSet rs = eksekusi.executeQuery(sql);
        System.out.println("-----------------------------------------");
        System.out.println("Menampilkan Data Product:");
        System.out.println("-----------------------------------------");
        while (rs.next()) {
            // ngambil data di database kumudian di tampilkan
            int id = rs.getInt("id");
            int category_id = rs.getInt("category_id");
            String name = rs.getString("name");
            String des = rs.getString("description");
            Double price = rs.getDouble("price");
            String status = rs.getString("status");
            System.out.println("Id : " + id + " | Category : " + category_id + " | Name : " + name + " | Description : "
                    + des + " | Price : " + price + " | Status : " + status);
        }
        System.out.println();
    }

    private static void create(Statement eksekusi, Scanner input) throws SQLException {
        int category;
        System.out.println("\n=========================");
        System.out.println("Category Barang : ");
        System.out.println("1. Peralatan dapur ");
        System.out.println("2. Peralatan berkebun ");
        System.out.println("3. Pakaian ");
        
        System.out.println("=========================");

        System.out.print("Masukkan Category barang dengan angka: ");
        category = input.nextInt();

        if (category == 1 || category == 2 || category == 3) {
            System.out.print("Masukkan id barang: ");
            int id = input.nextInt();
            input.nextLine();

            System.out.print("Masukkan Nama Barang: ");
            String name = input.nextLine();

            System.out.print("Masukkan Descripsi barang: ");
            String des = input.nextLine();

            System.out.print("Masukkan Harga barang: ");
            Float price = input.nextFloat();

            System.out.print("Masukkan Status barang: ");
            String status = input.next();

            String sql = "INSERT INTO product (id, category_id, name, description, price, status) VALUES ('" + id
                    + "','" + category + "','" + name + "','" + des + "','" + price + "','" + status + "')";
            eksekusi.executeUpdate(sql);
            System.out.println("-----------------------------------------");
            System.out.println("Data Produk berhasil ditambahkan.");
            System.out.println("-----------------------------------------\n");
        } else {
            System.out.println("== PILIHAN TIDAK VALID ==");
        }
    }

    private static void edit(Statement eksekusi, Scanner input) throws SQLException {
        System.out.print("Masukkan id barang yang akan diubah: ");
        int id = input.nextInt();
        input.nextLine();

        System.out.print("Masukkan nama barang baru: ");
        String name = input.next();
        input.nextLine();

        int category;
        System.out.println("\n=========================");
        System.out.println("Category Barang : ");
        System.out.println("1. Peralatan dapur ");
        System.out.println("2. Peralatan berkebun ");
        System.out.println("3. Pakaian ");
        System.out.println("=========================");

        System.out.print("Masukkan Category barang yang baru dengan angka: ");
        category = input.nextInt();

        if (category == 1 || category == 2 || category == 3) {

            System.out.print("Masukkan Descripsi barang baru: ");
            String des = input.nextLine();

            System.out.print("Masukkan Harga barang baru: ");
            Float price = input.nextFloat();

            System.out.print("Masukkan Status barang baru: ");
            String status = input.next();

            String sql = "UPDATE product SET category_id = '" + category + "', description = '" + des + "', price = '"
                    + price + "', status = '" + status + "', name = '" + name + "'  WHERE id = " + id;
            eksekusi.executeUpdate(sql);
            System.out.println("-----------------------------------------");
            System.out.println("Data product berhasil diubah.");
            System.out.println("-----------------------------------------\n");
        } else {
            System.out.println("== PILIHAN TIDAK VALID ==");
        }
    }

    private static void delete(Statement eksekusi, Scanner input) throws SQLException {
        System.out.print("Masukkan id product yang akan dihapus: ");
        int id = input.nextInt();

        String sql = "DELETE FROM product WHERE id = " + id;
        eksekusi.executeUpdate(sql);
        System.out.println("-----------------------------------------");
        System.out.println("Data product berhasil dihapus.");
        System.out.println("-----------------------------------------\n");
    }
}