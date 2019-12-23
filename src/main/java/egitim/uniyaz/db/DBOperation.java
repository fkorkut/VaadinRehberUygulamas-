package egitim.uniyaz.db;

import egitim.uniyaz.domain.Kisi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBOperation {

    final String CONNECTION_STR= "jdbc:mysql://localhost:3306/Rehber?serverTimezone=UTC";
    final String USERNAME="root";
    final String PASSWORD="12345";

    public int addKisi(Kisi kisi){
        String sqlSorgu="insert into kisi(adi,soyadi,telefon)"+" values(?,?,?)";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try{
            Connection conn= DriverManager.getConnection(CONNECTION_STR,USERNAME,PASSWORD);
            PreparedStatement preparedStatement=conn.prepareStatement(sqlSorgu);


            preparedStatement.setString(1,kisi.getAd());
            preparedStatement.setString(2,kisi.getSoyad());
            preparedStatement.setString(3,kisi.getTelefon());

            int affectedRows=preparedStatement.executeUpdate();
            System.out.println(affectedRows);

            return affectedRows;//Veritabanına ekleme yapıp yapıladığının kontrolu için değer döndürülmeli

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            System.out.println("hata sql bağlantısı");
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Kisi> listKisi(){
        List<Kisi> listKisi=new ArrayList<Kisi>();
        String sqlSorgu="select * from kisi";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection conn= DriverManager.getConnection(CONNECTION_STR,USERNAME,PASSWORD);

            PreparedStatement preparedStatement=conn.prepareStatement(sqlSorgu);

            ResultSet resultSet=preparedStatement.executeQuery();

            while(resultSet.next()){
                int id=resultSet.getInt("id");
                String name=resultSet.getString("adi");
                String surname=resultSet.getString("soyadi");
                String phone=resultSet.getString("telefon");

                Kisi kisi=new Kisi();
                kisi.setId(id);
                kisi.setAd(name);
                kisi.setSoyad(surname);
                kisi.setTelefon(phone);

                listKisi.add(kisi);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listKisi;
    }


    public void deleteKisi(int id){
        String sqlSorgu="delete from kisi where id=?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try{
            Connection conn= DriverManager.getConnection(CONNECTION_STR,USERNAME,PASSWORD);
            PreparedStatement preparedStatement=conn.prepareStatement(sqlSorgu);

            preparedStatement.setInt(1,id);
            int affectedRows=preparedStatement.executeUpdate();
            System.out.println("Silindi"+affectedRows);

        }
        catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            System.out.println("hata sql bağlantısı");

        }
        catch (Exception e) {
            e.printStackTrace();

        }
    }


    public int update(Kisi kisi){
        String sqlSorgu="update kisi set adi=?,soyadi=?,telefon=? where id=?";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try{
            Connection conn= DriverManager.getConnection(CONNECTION_STR,USERNAME,PASSWORD);
            PreparedStatement preparedStatement=conn.prepareStatement(sqlSorgu);

            preparedStatement.setString(1,kisi.getAd());
            preparedStatement.setString(2,kisi.getSoyad());
            preparedStatement.setString(3,kisi.getTelefon());
            preparedStatement.setInt(4,kisi.getId());

            int affectedRows=preparedStatement.executeUpdate();
            return  affectedRows;

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            System.out.println("hata sql bağlantısı");
           return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;

        }

    }
}
