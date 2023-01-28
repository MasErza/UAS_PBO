import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ImportJSON {

    public static void main(String[] args) {
        // Koneksi ke database
        Connection conn = Database.getConnection();

        try {
            // Membaca file JSON
            FileReader reader = new FileReader("categories.json");
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);

            // Menyiapkan statement untuk menyimpan data ke tabel "Category"
            String sql = "INSERT INTO Category (id, name) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);

            // Iterasi setiap objek dalam array
            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;

                // Mengambil data dari file JSON
                int id = Integer.parseInt(jsonObject.get("id").toString());
                String name = jsonObject.get("name").toString();

                // Menyimpan data ke tabel "Category"
                statement.setInt(1, id);
                statement.setString(2, name);
                statement.executeUpdate();
            }

            System.out.println("Data berhasil diimpor ke tabel 'Category'");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
